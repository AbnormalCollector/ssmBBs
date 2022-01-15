package com.sy.service;


import com.sy.model.Invitation;
import com.sy.util.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvitationService {
    Result findAll(int pid, String title, String username, int pageNow, int pageSize) throws Exception;

    Result findByTitle(String title, String username, int pageNow, int pageSize) throws Exception;

    Result findBYUid(int uid, int pageNow, int pageSize) throws Exception;

    Result modifyAll(String title, String summary, int id) throws Exception;

    Result modifyClicks(int id) throws Exception;

    Result findById(int id) throws Exception;

    Result addAll(Invitation invitation) throws Exception;

    Result removeById(Invitation invitation) throws Exception;

    Result modifyPid(Invitation invitation) throws Exception;

    Result modifyStatus(Invitation invitation) throws Exception;
}
