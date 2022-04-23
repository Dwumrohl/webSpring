package com.web.webSpring;

import com.web.webSpring.dbEntities.MongoDBOperations;
import com.web.webSpring.dbEntities.UserRepository;
import com.web.webSpring.dbEntities.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.web.webSpring.MongoConfig;

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
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        return "greeting";
    }
    @PostMapping("/login")
    public String greetingForm(@ModelAttribute user usr, Model model) {
        List<user> users = ops.getAllUsers(mongoOperation);
        String temp = "";
        for (user x:users) {
            temp = x.getUsername();
        }
        if(Objects.equals(temp, usr.getUsername())) {
            model.addAttribute("regFlag", false);
        }
        else
        ops.addUser(mongoOperation,usr);
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new user());
        return "login";
    }


}
