package com.sy.controller;

import com.sy.mapper.UserMapper;
import com.sy.model.Invitation;
import com.sy.model.User;
import com.sy.service.InvitationService;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("invitation")
@CrossOrigin
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @RequestMapping(value = "list")
    public Result findAll(@RequestParam int pid, @RequestParam(defaultValue = "") String title, @RequestParam String username, @RequestParam int pageNow, @RequestParam int pageSize) throws Exception {
        System.out.println(pageNow + "===" + pageSize);
        Result result = invitationService.findAll(pid, title, username, pageNow, pageSize);
        return result;
    }

    @RequestMapping(value = "listUid")
    public Result findByUId(@RequestParam() int uid, @RequestParam int pageNow, @RequestParam int pageSize) throws Exception {
        Result result = invitationService.findBYUid(uid, pageNow, pageSize);
        return result;
    }

    @RequestMapping(value = "listTitle")
    public Result findByTitle(@RequestParam(defaultValue = "") String title, @RequestParam String username, @RequestParam int pageNow, @RequestParam int pageSize) throws Exception {
        System.out.println(title + username);
        Result result = invitationService.findByTitle(title, username, pageNow, pageSize);
        return result;
    }

    @RequestMapping("/modifyClicks")
    public Result modifyClicks(Invitation invitation) throws Exception {
        Result result = invitationService.modifyClicks(invitation.getId());
        return result;
    }

    @RequestMapping("/content")
    public Result findBYId(@RequestBody Invitation invitation) throws Exception {
        System.out.println(invitation.getId());
        Result byId = invitationService.findById(invitation.getId());
        return byId;
    }

    @RequestMapping("/add")
    public Result addAll(@RequestBody Invitation invitation) throws Exception {
        Result result = invitationService.addAll(invitation);
        return result;
    }

    @RequestMapping("/modifyAll")
    public Result modifyAll(@RequestBody Invitation invitation) throws Exception {
//        System.out.println(invitation);
        Result result = invitationService.modifyAll(invitation.getTitle(), invitation.getSummary(), invitation.getId());
        return result;
    }

    @RequestMapping("/removeById")
    public Result removeById(@RequestBody Invitation invitation) throws Exception {
        System.out.println(invitation.getId());
//        System.out.println(invitation);
        Result result = invitationService.removeById(invitation);
        return result;
    }

    @RequestMapping("/modifyPid")
    public Result modifyPid(@RequestBody Invitation invitation) throws Exception {
//        System.out.println(invitation);
        Result result = invitationService.modifyPid(invitation);
        return result;
    }

    @RequestMapping("/modifyStatus")
    public Result modifyStatus(@RequestBody Invitation invitation) throws Exception {
        System.out.println(invitation);
        Result result = invitationService.modifyStatus(invitation);
        return result;
    }
}
