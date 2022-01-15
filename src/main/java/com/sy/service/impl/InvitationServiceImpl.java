package com.sy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.mapper.InvitationMapper;
import com.sy.mapper.PlateMapper;
import com.sy.mapper.ReplyMapper;
import com.sy.mapper.UserMapper;
import com.sy.model.Invitation;
import com.sy.model.Plate;
import com.sy.model.User;
import com.sy.service.InvitationService;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationMapper invitationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private PlateMapper plateMapper;

    @Override
    public Result findAll(int pid, String title, String username, int pageNow, int pageSize) throws Exception {
        Result result = new Result();
        User uid = new User();
        if (username != null && !"".equals(username)) {
            uid = userMapper.selectByName(username);
        }
        PageHelper.startPage(pageNow, pageSize);
        Integer id = null;
        if (uid != null) {
            id = uid.getId();
        }
        List<Invitation> invitations = invitationMapper.selectInvitationAll(pid, title, id);
        for (Invitation inv : invitations) {
            User user = userMapper.selectById(inv.getUid());
            inv.setUser(user);
        }
        PageInfo pageInfo = PageInfo.of(invitations);
        if (pageInfo.getList() != null) {
            result.setData(invitations);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCount(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Override
    public Result findByTitle(String title, String username, int pageNow, int pageSize) throws Exception {
        Result result = new Result();
        User uid = new User();
        Invitation invitation = new Invitation();
        if (username != null && !"".equals(username)) {
            uid = userMapper.selectByName(username);
        }
        PageHelper.startPage(pageNow, pageSize);
        invitation.setTitle(title);
        invitation.setUid(uid.getId());
        List<Invitation> invitations = invitationMapper.selectAllBYTile(invitation);
        for (Invitation inv : invitations) {
            User user = userMapper.selectById(inv.getUid());
            Plate plate = plateMapper.selectById(inv.getPid());
            inv.setPlate(plate);
            inv.setUser(user);
        }
        PageInfo pageInfo = PageInfo.of(invitations);
        if (pageInfo.getList() != null) {
            result.setData(invitations);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCount(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Override
    public Result findBYUid(int uid, int pageNow, int pageSize) throws Exception {
        Result result = new Result();
        PageHelper.startPage(pageNow, pageSize);
        List<Invitation> invitations = invitationMapper.selectAllBYUid(uid);
        for (Invitation inv : invitations) {
            User user = userMapper.selectById(inv.getUid());
            inv.setUser(user);
        }
        PageInfo pageInfo = PageInfo.of(invitations);
        if (pageInfo.getList() != null) {
            result.setData(invitations);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCount(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyAll(String title, String summary, int id) throws Exception {
        Result result = new Result();
        int i = invitationMapper.updateInvitation(title, summary, id);
        if (i != 0) {
            result.setData(i);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyClicks(int id) throws Exception {
        Result result = new Result();
        int i = invitationMapper.updateClicks(Result.CLICK_ADD, id);
        if (i != 0) {
            result.setData(i);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Override
    public Result findById(int id) throws Exception {
        Result result = new Result();
        Invitation invitation = invitationMapper.selectInvitationById(id);
        User user = userMapper.selectById(invitation.getUid());
        Integer integer = replyMapper.selectCountInvId(invitation.getId());
        invitation.setAnswer(integer);
        invitation.setUser(user);
        if (invitation != null) {
            result.setData(invitation);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result addAll(Invitation invitation) throws Exception {
        Result result = new Result();
        int i = invitationMapper.insertUserAll(invitation);
        if (i != 0) {
            result.setData(i);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result removeById(Invitation invitation) throws Exception {
        Result result = new Result();
        int i = invitationMapper.deleteInvitationById(invitation);
        if (i != 0) {
            result.setData(i);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyPid(Invitation invitation) throws Exception {
        Result result = new Result();
        int i = invitationMapper.updatePid(invitation);
        if (i != 0) {
            result.setData(i);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyStatus(Invitation invitation) throws Exception {
        Result result = new Result();
        int i = invitationMapper.updateStatus(invitation.getId(), invitation.getStatus());
        if (i != 0) {
            result.setData(i);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }


}
