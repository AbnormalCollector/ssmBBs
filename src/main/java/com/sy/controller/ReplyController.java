package com.sy.controller;

import com.sy.model.Reply;
import com.sy.service.ReplyService;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reply")
@RestController
@CrossOrigin
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @RequestMapping("/list")
    public Result findAll(@RequestParam int invid, @RequestParam int pageNow, @RequestParam int pageSize) throws Exception {
//        System.out.println("=====>"+invid+"=====>"+pageNow+"=====>"+pageSize);
        Result all = replyService.findAll(invid, pageNow, pageSize);
        return all;
    }

    @RequestMapping("/listContent")
    public Result findContent(@RequestParam(defaultValue = "") String content, String username, @RequestParam int pageNow, @RequestParam int pageSize) throws Exception {
        System.out.println("=====>" + content + "---" + username + "=====>" + pageNow + "=====>" + pageSize);
        Result result = replyService.findContent(content, username, pageNow, pageSize);
        return result;
    }

    @RequestMapping("/add")
    public Result addAll(@RequestBody Reply reply) throws Exception {
        Result result = replyService.addAll(reply);
        return result;
    }

    @RequestMapping("/findId")
    public Result findById(@RequestBody Reply reply) throws Exception {
//        System.out.println();
        Result byId = replyService.findById(reply);
        return byId;
    }

    @RequestMapping("/modify")
    public Result modifyAll(@RequestBody Reply reply) throws Exception {
        Result result = replyService.modifyAll(reply);
        return result;
    }

    @RequestMapping("/removeById")
    public Result removeById(@RequestBody Reply reply) throws Exception {
        Result result = replyService.removeById(reply.getId());
        return result;
    }
}
