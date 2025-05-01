package org.kps.appjwt.repository;

import org.apache.ibatis.annotations.*;
import org.kps.appjwt.model.AppUser;
import org.kps.appjwt.model.AppUserDto;
import org.kps.appjwt.model.AppUserRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper
public interface AppUserRepository {

    @Select("""
        insert into users(email, password)
        values (#{user.email}, #{user.password})
        returning *
    """)
    AppUser insertUser(@Param("user") AppUserRequest appUserRequest);

    @Select("""
        insert into user_role(user_id, role_id)
        values (#{userId}, #{roleId})
    """)
    void insertIntoUserRole(Integer userId, Integer roleId);

    @Select("select * from users where id = #{userId}")
    @Result(property = "roles", column = "id",
        many = @Many(select = "getAllRolesByUserId"))
    @Result(property = "id", column = "id")
    AppUser findUserByUserId(Integer userId);

    @Select("""
        select role_name from roles r
        inner join user_role ur
        on r.id = ur.role_id
        where ur.user_id = #{userId}
    """)
    List<String> getAllRolesByUserId(Integer userId);

    @Select("select * from users where email = #{email}")
    @Result(property = "roles", column = "id",
            many = @Many(select = "getAllRolesByUserId"))
    @Result(property = "id", column = "id")
    AppUser findUserByEmail(String email);
}
