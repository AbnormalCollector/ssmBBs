package com.sy.mapper;

import com.sy.model.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ReplyMapper {

    @Select("select count(invid) from reply where invid=#{invid}")
    Integer selectCountInvId(int invid) throws Exception;

    @Select("select * from reply where invid=#{invid} order by createDate desc")
    List<Reply> selectAll(int invid) throws Exception;

    @Insert("insert into reply(invid,content,uid) values(#{invid},#{content},#{uid})")
    int insertAll(Reply reply) throws Exception;

    @Select("select * from reply where id=#{id}")
    Reply selectById(int id) throws Exception;

    @Update("update reply set content=#{content} where id=#{id}")
    int updateAll(Reply reply) throws Exception;


    List<Reply> selectContent(Reply reply) throws Exception;

    @Delete("delete from reply where id=#{id}")
    int deleteById(int id) throws Exception;
}
