package com.sy.controller;

import com.sy.model.User;
import com.sy.service.UserService;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Result findUser(@RequestBody User user) throws Exception {
        System.out.println(user);
        Result result = userService.findUser(user);
        return result;
    }

    @RequestMapping("/add")
    public Result addUser(@RequestBody User user) throws Exception {
        Result result = userService.addUser(user);
        return result;
    }

    @RequestMapping("/list")
    public Result getList(@RequestParam(defaultValue = "") String username, @RequestParam int pageNow, @RequestParam int pageSize) throws Exception {
//        System.out.println("===>"+username);
        Result result = userService.findAll(username, pageNow, pageSize);
        return result;
    }

    @RequestMapping("/remove")
    public Result removeUser(@RequestBody User user) throws Exception {

        Result result = userService.removeUser(user);
        return result;
    }

    @RequestMapping("/modify")
    public Result modifyUser(@RequestBody User user) throws Exception {
        System.out.println(user);
        Result result = userService.modifyUser(user);
        return result;
    }

    @RequestMapping("/modifyEnable")
    public Result modifyEnable(@RequestBody User user) throws Exception {

        Result result = userService.modifyEnable(user);
        return result;
    }

    @RequestMapping("/modifyreplyStatus")
    public Result modifyReplyStatus(@RequestBody User user) throws Exception {
        Result result = userService.modifyReplyStatus(user);
        return result;
    }

    @RequestMapping("/modifyPost")
    public Result modifyPost(@RequestBody User user) throws Exception {
        Result result = userService.modifyPost(user);
        return result;
    }

    @RequestMapping("/findPost")
    public Result findPost(@RequestParam int id) throws Exception {
        Result result = userService.findPost(id);
        return result;
    }

    @RequestMapping("/findReplyStatus")
    public Result findReplyStatus(@RequestParam int id) throws Exception {
        Result result = userService.findReplyStatus(id);
        return result;
    }

    @RequestMapping("/findStatus")
    public Result findStatus(@RequestParam int id) throws Exception {
        Result result = userService.findStatus(id);
        return result;
    }

}
