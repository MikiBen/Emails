package com.onwelo.emails.rest;

import com.onwelo.emails.db.User;
import com.onwelo.emails.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public User get(@RequestParam("id") Long id) {
        logger.info("get User id = " + id);
        User user = userService.findById(id);
        logger.info("response User = " + user);
        return user;
    }

    @PostMapping("/createOrUpdate")
    public void createOrUpdate(@RequestBody User user) {
        logger.info("createOrUpdate " + user);
        userService.createOrUpdate(user);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Long id) {
        logger.info("delete user with id = " + id);
        User user = userService.findById(id);
        userService.delete(id);
        logger.info("deleted user  = " + user);
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        logger.info("getAll users");
        return userService.findAll();
    }
}
