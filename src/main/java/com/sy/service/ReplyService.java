package com.sy.service;

import com.sy.model.Reply;
import com.sy.util.Result;

public interface ReplyService {

    Result findAll(int invid, int pageNow, int pageSize) throws Exception;

    Result addAll(Reply reply) throws Exception;

    Result findById(Reply reply) throws Exception;

    Result modifyAll(Reply reply) throws Exception;

    Result findContent(String content, String username, int pageNow, int pageSize) throws Exception;

    Result removeById(int id) throws Exception;
}
