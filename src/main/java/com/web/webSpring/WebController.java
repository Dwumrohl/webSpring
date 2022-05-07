package com.web.webSpring;

import com.web.webSpring.dbEntities.*;
import org.bson.BsonTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Controller
public class WebController {

    @Autowired
    UserRepository userRepository;

    ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    MongoDBOperations ops = new MongoDBOperations();

    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/announcements")
    public String ann(Model model) {
        List<announcement> realAnns = ops.getAllAnnLimit(mongoOperation);
        model.addAttribute("anns",realAnns);
        return "announcements";
    }


    @PostMapping("/login")
    public ModelAndView logForm(@ModelAttribute user usr, Model model, HttpSession session) {
        List<user> users = ops.getAllUsers(mongoOperation);
        for (user x:users) {
            String name = "";
            String pass = "";
            name = x.getUsername();
            pass = x.getPassword();
            if(Objects.equals(name, usr.getUsername()) && Objects.equals(pass, usr.getPassword())) {
                if(x.isBlocked()){
                    model.addAttribute("msg","Пользователь заблокирован!");
                    model.addAttribute("msgFlag", true);
                    return new ModelAndView("/login");
                }
                ops.updateUser(mongoOperation,"_id",x.getId(),"login_date", new BsonTimestamp((int) (Instant.now()).getEpochSecond(), 0));
                session.setAttribute("username",x.getUsername());
                session.setAttribute("logged",true);
                session.setAttribute("userId",x.getId());
                session.setAttribute("admin",x.isAdmin());
                session.setAttribute("register_date",x.getRegister_date());
                session.setAttribute("login_date",x.getLogin_date());
                model.addAttribute("msgFlag", true);
                model.addAttribute("msg", "Успешно!");
                return new ModelAndView("redirect:/greeting");
            }
        }
        model.addAttribute("msg", "Неверный логин или пароль!");
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
        usr.setLogin_date(new BsonTimestamp((int) (Instant.now()).getEpochSecond(), 0));
        usr.setRegister_date(new BsonTimestamp((int) (Instant.now()).getEpochSecond(), 0));
        ops.addUser(mongoOperation, usr);
        model.addAttribute("msgReg", "Successful registration!");
        model.addAttribute("regFlag", true);
        return "login";
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.setAttribute("username",null);
        session.setAttribute("logged",false);
        session.setAttribute("admin",false);
        session.setAttribute("userId",null);
        session.setAttribute("register_date",null);
        session.setAttribute("login_date",null);
        return new ModelAndView("forward:/greeting");
    }

    @GetMapping("/announcementPage")
    public String annPage(@RequestParam String id, Model model, HttpSession session) {
        announcement ann = ops.searchAnn(mongoOperation,"_id",id);
        model.addAttribute("ann", ann);
        List<comment> comments = ops.searchComments(mongoOperation,"annId",ann.getId());
        int commSize=comments.size();
        for (comment x:
             comments) {
            user user = ops.searchUser(mongoOperation,"_id",x.getUserId());
            x.setUserName(user.getUsername());
            x.setAdmin(user.isAdmin());
        }
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new comment());
        model.addAttribute("commSize",commSize);
        session.setAttribute("annId",id);
        return "announcementPage";
    }

    @GetMapping("/leaveComment")
    public ModelAndView leaveComment(@ModelAttribute comment comment, HttpSession session){
        String id = session.getAttribute("annId").toString();
        comment.setAnnId(id);
        comment.setDate(new BsonTimestamp((int) (Instant.now()).getEpochSecond(), 0));
        comment.setUserId(session.getAttribute("userId").toString());
        ops.addComment(mongoOperation,comment);
        return new ModelAndView("redirect:/announcementPage?id="+id);
    }

    @GetMapping("/deleteComment")
    public ModelAndView deleteComment(@RequestParam String id, HttpSession session){
        ops.removeComment(mongoOperation,"_id",id);
        return new ModelAndView("redirect:/announcementPage?id="+session.getAttribute("annId").toString());
    }

    @GetMapping("/deleteAnn")
    public ModelAndView deleteAnn(@RequestParam String id){
        ops.removeAnn(mongoOperation,"_id",id);
        return new ModelAndView("redirect:/announcements");
    }

    @GetMapping("/themes")
    public String getThemes(Model model){
        List<annType> types = ops.getAllAnnTypes(mongoOperation);
        model.addAttribute("themes",types);
        return "themes";
    }

    @GetMapping("/annByType")
    public String getAnnByType(@RequestParam String id, Model model){
        List<announcement> anns = ops.searchAnnS(mongoOperation,"annTypeId",id);
        model.addAttribute("anns",anns);
        return "announcements";
    }

    @GetMapping("/profile")
    public String getProfile(@RequestParam String id, Model model){
        user usr = ops.searchUser(mongoOperation,"_id",id);
        long count = ops.countUserComments(mongoOperation,"userId",id);
        model.addAttribute("user",usr);
        model.addAttribute("count",count);
        return "profile";
    }

    @GetMapping("/newAnn")
    public String newAnn(Model model){
        model.addAttribute("announcement",new announcement());
        List<annType> types = ops.getAllAnnTypes(mongoOperation);
        model.addAttribute("types", types);
        return "newAnn";
    }

    @GetMapping("/alterAnn")
    public String alterAnn(@RequestParam String id, Model model){
        model.addAttribute("announcement",ops.searchAnn(mongoOperation,"_id",id));
        List<annType> types = ops.getAllAnnTypes(mongoOperation);
        model.addAttribute("types", types);
        return "alterAnn";
    }

   //@GetMapping(path="/createAnn",consumes = {MULTIPART_FORM_DATA_VALUE})
    @PostMapping(path="/createAnn")
    public ModelAndView newAnn(@ModelAttribute announcement ann,
                               @RequestParam("file") MultipartFile file, Model model, HttpSession session){
        ann.setDate(new BsonTimestamp((int) (Instant.now()).getEpochSecond(), 0));
        annType annType = ops.searchAnnType(mongoOperation,"_id",ann.getAnnTypeId());
       ann.setType(annType.getType());
        if(!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream("src/main/resources/static/images/"+file.getOriginalFilename()));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
            }
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream("target/classes/static/images/"+file.getOriginalFilename()));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
            }
            ann.setImage("/images/"+file.getOriginalFilename());
        }
        else ann.setImage("");
       ops.addAnnouncement(mongoOperation,ann);
        return new ModelAndView("redirect:/announcements");
   }

   @PostMapping("alterAnn")
   public ModelAndView alterAnn(@ModelAttribute announcement ann,
                              @RequestParam("file") MultipartFile file,@RequestParam("_id") String id, @RequestParam("image") String image){
       ann.setDate(new BsonTimestamp((int) (Instant.now()).getEpochSecond(), 0));
       annType annType = ops.searchAnnType(mongoOperation,"_id",ann.getAnnTypeId());
       ann.setType(annType.getType());
       if(!file.isEmpty()){
       try {
           byte[] bytes = file.getBytes();
           BufferedOutputStream stream =
                   new BufferedOutputStream(new FileOutputStream("src/main/resources/static/images/"+file.getOriginalFilename()));
           stream.write(bytes);
           stream.close();
       } catch (Exception e) {
       }
           try {
               byte[] bytes = file.getBytes();
               BufferedOutputStream stream =
                       new BufferedOutputStream(new FileOutputStream("target/classes/static/images/"+file.getOriginalFilename()));
               stream.write(bytes);
               stream.close();
           } catch (Exception e) {
           }
           ann.setImage("/images/"+file.getOriginalFilename());
       }else ann.setImage(image);
       ann.setId(id);
       System.out.println(ann.getImage());
       ops.saveAnnouncement(mongoOperation,ann);
       return new ModelAndView("redirect:/announcements");
   }

   @PostMapping("searchAnn")
    public String searchAnn(@RequestParam String searchInf, Model model){
        List<announcement> anns = ops.searchForAnn(mongoOperation,searchInf);
        model.addAttribute("anns",anns);
        return "announcements";
   }

   @GetMapping("banUser")
    public ModelAndView banUser(@RequestParam String id, @RequestParam boolean banned){
        ops.banUser(mongoOperation,"_id",id,"blocked",!banned);
        return new ModelAndView("redirect:/profile?id="+id);
   }











}
