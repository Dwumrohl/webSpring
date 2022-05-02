package com.web.webSpring;

import com.web.webSpring.dbEntities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.web.webSpring.MongoConfig;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class WebController {

    @Autowired
    UserRepository userRepository;

    ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    MongoDBOperations ops = new MongoDBOperations();

    @GetMapping("/greeting")
    public String greeting(Model model, HttpSession session) {
        model.addAttribute("logged",session.getAttribute("logged"));
        model.addAttribute("userN",session.getAttribute("username"));
        return "greeting";
    }

    @GetMapping("/announcements")
    public String ann(Model model, HttpSession session) {
        model.addAttribute("logged",session.getAttribute("logged"));
        model.addAttribute("userN",session.getAttribute("username"));
        List<announcement> anns = ops.getAllAnnLimit(mongoOperation);
        model.addAttribute("anns",anns);
        return "announcements";
    }

    @PostMapping("/login")
    public ModelAndView logForm(@ModelAttribute user usr, Model model, HttpSession session) {
        List<user> users = ops.getAllUsers(mongoOperation);
        session.setAttribute("username",usr.getUsername());
        session.setAttribute("logged",true);
        for (user x:users) {
            String name = "";
            String pass = "";
            name = x.getUsername();
            pass = x.getPassword();
            if(Objects.equals(name, usr.getUsername()) && Objects.equals(pass, usr.getPassword())) {
                model.addAttribute("msgFlag", true);
                model.addAttribute("msg", "Successful!");
                return new ModelAndView("redirect:/greeting");
            }
        }
        model.addAttribute("msg", "Wrong login or password!");
        model.addAttribute("msgFlag", true);
        return new ModelAndView("/login");
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new user());
        return "login";
    }

    @PostMapping("/reg")
    public String regForm(@ModelAttribute user usr, Model model) {
        List<user> users = ops.getAllUsers(mongoOperation);
        for (user x:users) {
            String temp = "";
            temp = x.getUsername();
            if(Objects.equals(temp, usr.getUsername())) {
                model.addAttribute("msgReg", "User already exists!");
                model.addAttribute("regFlag", true);
                return "login";
            }
        }
        ops.addUser(mongoOperation, usr);
        model.addAttribute("msgReg", "Successful registration!");
        model.addAttribute("regFlag", true);
        return "login";
    }

    @GetMapping("/logout")
    public ModelAndView logout(Model model, HttpSession session){
        session.setAttribute("username",null);
        session.setAttribute("logged",false);
        return new ModelAndView("forward:/greeting");
    }

    @GetMapping("/announcementPage")
    public String annPage(@RequestParam String id, Model model, HttpSession session) {
        model.addAttribute("logged",session.getAttribute("logged"));
        model.addAttribute("userN",session.getAttribute("username"));
        announcement ann = ops.searchAnn(mongoOperation,"_id",id);
        model.addAttribute("ann", ann);
        List<comment> comments = ops.searchComments(mongoOperation,"annId",ann.getId());
        for (comment x:
             comments) {
            user user = ops.searchUser(mongoOperation,"_id",x.getUserId());
            x.setUserName(user.getUsername());
            x.setAdmin(user.isAdmin());
        }
        model.addAttribute("comments", comments);
        return "announcementPage";
    }



}
