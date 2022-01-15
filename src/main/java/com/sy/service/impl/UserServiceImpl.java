package com.sy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.mapper.UserMapper;
import com.sy.model.User;
import com.sy.service.UserService;
import com.sy.util.JwtTokenUtil;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result findUser(User user) throws Exception {
        Result result = new Result();
        User newuser = userMapper.selectUser(user);
        if (newuser != null) {
            result.setData(newuser);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(newuser));
            Map<String, Object> claim = jsonObject.getInnerMap();
            String jwt = jwtTokenUtil.getAccessToken(newuser.getId().toString(), claim);
            result.setToken(jwt);
        } else {
            result.setCode(Result.CODE_ERROR);
            result.setMsg(Result.MSG_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result addUser(User user) throws Exception {
        Result result = new Result();
        int i = userMapper.insertUser(user);
        if (i == 1) {
            result.setData(i);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result findAll(String username, int pageNow, int pageSize) throws Exception {
        Result result = new Result();
        PageHelper.startPage(pageNow, pageSize);
        List<User> users = userMapper.selectAll(username);
        PageInfo pageInfo = PageInfo.of(users);
        if (users != null) {
            result.setData(pageInfo);
            result.setMsg(Result.MSG_SUCCESS);
            result.setCode(Result.CODE_SUCCESS);
            result.setCount(pageInfo.getTotal());
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result removeUser(User user) throws Exception {
        Result result = new Result();
        int i = userMapper.deleteUser(user);
        if (i == 1) {
            result.setData(i);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyUser(User user) throws Exception {
        Result result = new Result();
        int i = userMapper.alterUser(user);
        if (i == 1) {
            result.setData(i);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyEnable(User user) throws Exception {
        Result result = new Result();
        int i = userMapper.alterEnable(user);
        if (i == 1) {
            result.setData(i);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyPost(User user) throws Exception {
        Result result = new Result();
        int i = userMapper.alterPost(user);
        if (i == 1) {
            result.setData(i);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Transactional
    @Override
    public Result modifyReplyStatus(User user) throws Exception {
        Result result = new Result();
        int i = userMapper.alterReplyStatus(user);
        if (i == 1) {
            result.setData(i);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result findPost(int id) throws Exception {
        Result result = new Result();
        User user = userMapper.selectByPost(id);
        if (user != null) {
            result.setData(user);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result findStatus(int id) throws Exception {
        Result result = new Result();
        User user = userMapper.selectByStatus(id);
        if (user != null) {
            result.setData(user);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }

    @Override
    public Result findReplyStatus(int id) throws Exception {
        Result result = new Result();
        User user = userMapper.selectByReply(id);
        if (user != null) {
            result.setData(user);
            result.setCode(Result.CODE_SUCCESS);
            result.setMsg(Result.MSG_SUCCESS);
        } else {
            result.setMsg(Result.MSG_ERROR);
            result.setCode(Result.CODE_ERROR);
        }
        return result;
    }


}
