package com.sy.mapper;

import com.sy.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    User selectUser(User user) throws Exception;

    @Insert("insert into user(username,password) values(#{username},#{password})")
    int insertUser(User user) throws Exception;

    @Delete("delete from user where id=#{id}")
    int deleteUser(User user) throws Exception;

    @Select("select user.id,user.username from user where id=#{id}")
    User selectById(int id) throws Exception;

    @Select("select user.id from user where username=#{username}")
    User selectByName(String username) throws Exception;

    @Select("select * from user where username like concat('%', #{username}, '%')")
    List<User> selectAll(String username) throws Exception;

    //    @Update("<script>" +
//            "update user " +
//            "<set>" +
//            "<if test='username!=null and username!=\"\"'>username=#{username},</if>" +
//            "<if test='password!=null and password!=\"\"'>password=#{password},</if>" +
//            "<if test='status!=null and status!=\"\"'>status=#{status},</if>" +
//            "<if test='enable!=null and enable!=\"\"'>enable=#{enable},</if>" +
//            "<if test='post!=null and post!=\"\"'>post=#{post},</if>" +
//            "</set>" +
//            "where id=#{id}" +
//            "</script>")
    @Update("update user set username=#{username},password=#{password},status=#{status} where id=#{id}")
    int alterUser(User user) throws Exception;

    @Update("update user set enable=#{enable} where id=#{id}")
    int alterEnable(User user) throws Exception;

    @Update("update user set post=#{post} where id=#{id}")
    int alterPost(User user) throws Exception;

    @Update("update user set replyStatus=#{replyStatus} where id=#{id}")
    int alterReplyStatus(User user) throws Exception;

    @Select("select user.post from user where id=#{id}")
    User selectByPost(int id) throws Exception;

    @Select("select user.status from user where id=#{id}")
    User selectByStatus(int id) throws Exception;

    @Select("select user.replyStatus from user where id=#{id}")
    User selectByReply(int id) throws Exception;
}
