package com.sy.mapper;

import com.sy.model.Invitation;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface InvitationMapper {

    List<Invitation> selectInvitationAll(@Param("pid") int pid, @Param("title") String title, @Param("uid") Integer uid) throws Exception;

    List<Invitation> selectAllBYTile(Invitation invitation) throws Exception;

    List<Invitation> selectAllBYUid(int uid) throws Exception;

    int updateInvitation(@Param("title") String title, @Param("summary") String summary, @Param("id") int id) throws Exception;

    int updateClicks(@Param("clicks") int clicks, @Param("id") int id) throws Exception;

    @Select("select * from invitation where id=#{id} order by createDate desc")
    Invitation selectInvitationById(int id) throws Exception;

    int updateAnswer(@Param("answer") int answer, @Param("id") int id) throws Exception;

    @Insert("insert into invitation(title,summary,uid,pid) values(#{title},#{summary},#{uid},#{pid})")
    int insertUserAll(Invitation invitation) throws Exception;

    @Delete("delete from invitation where id=#{id}")
    int deleteInvitationById(Invitation invitation) throws Exception;

    int updatePid(Invitation invitation) throws Exception;

    @Update("update invitation set status=#{status} where id=#{id}")
    int updateStatus(@Param("id") int id, @Param("status") int status) throws Exception;
}
