package com.sy.service;

import com.sy.model.User;
import com.sy.util.Result;

public interface UserService {

    Result findUser(User user) throws Exception;

    Result addUser(User user) throws Exception;

    Result findAll(String username, int pageNow, int pageSize) throws Exception;

    Result removeUser(User user) throws Exception;

    Result modifyUser(User user) throws Exception;

    Result modifyEnable(User user) throws Exception;

    Result modifyPost(User user) throws Exception;

    Result modifyReplyStatus(User user) throws Exception;

    Result findPost(int id) throws Exception;

    Result findStatus(int id) throws Exception;

    Result findReplyStatus(int id) throws Exception;
}
