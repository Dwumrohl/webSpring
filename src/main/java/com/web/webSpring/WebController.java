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

@Controller
public class WebController {

    @Autowired
    UserRepository userRepository;

    ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    MongoDBOperations ops = new MongoDBOperations();

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        List<user> users = ops.getAllUsers(mongoOperation);
        model.addAttribute("name", users.get(0).getUsername());
        model.addAttribute("user", new user());
        return "greeting";
    }
    @PostMapping("/greeting")
    public String greetingForm(@ModelAttribute user usr, Model model) {
        ops.addUser(mongoOperation,usr);
        return "greeting";
    }


}
