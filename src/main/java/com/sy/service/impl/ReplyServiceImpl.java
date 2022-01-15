package com.sy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.mapper.InvitationMapper;
import com.sy.mapper.ReplyMapper;
import com.sy.mapper.UserMapper;
import com.sy.model.Invitation;
import com.sy.model.Reply;
import com.sy.model.User;
import com.sy.service.ReplyService;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InvitationMapper invitationMapper;

    @Override
    public Result findAll(int invid, int pageNow, int pageSize) throws Exception {
        Result result = new Result();
        PageHelper.startPage(pageNow, pageSize);
        List<Reply> replies = replyMapper.selectAll(invid);
        for (Reply reply : replies) {
            User user = userMapper.selectById(reply.getUid());
            reply.setUser(user);
        }
        PageInfo pageInfo = PageInfo.of(replies);
        if (pageInfo.getList() != null) {
            result.setData(replies);
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
    public Result addAll(Reply reply) throws Exception {
        Result result = new Result();
        int i = replyMapper.insertAll(reply);
        int i2 = invitationMapper.updateAnswer(Result.ANSWER_ADD, reply.getInvid());
        if (i + i2 >= 1) {
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setData(i);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result
    findById(Reply reply) throws Exception {
        Result result = new Result();
        Reply findReply = replyMapper.selectById(reply.getId());
        if (findReply != null) {
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setData(findReply);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyAll(Reply reply) throws Exception {
        Result result = new Result();
        int i = replyMapper.updateAll(reply);
        if (i != 0) {
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setData(i);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result findContent(String content, String username, int pageNow, int pageSize) throws Exception {
        Result result = new Result();
        User uid = new User();
        if (username != null && !"".equals(username)) {
            uid = userMapper.selectByName(username);
        }
        Reply replys = new Reply();
        replys.setContent(content);
        if (uid.getId() != null) {
            replys.setUid(uid.getId());
        }
        PageHelper.startPage(pageNow, pageSize);
        List<Reply> replies = replyMapper.selectContent(replys);
        for (Reply reply : replies) {
            User user = userMapper.selectById(reply.getUid());
            reply.setUser(user);
        }
        PageInfo pageInfo = PageInfo.of(replies);
        if (pageInfo.getList() != null) {
            result.setData(replies);
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
    public Result removeById(int id) throws Exception {
        Result result = new Result();
        int i = replyMapper.deleteById(id);
        if (i != 0) {
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            result.setData(i);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }
}
