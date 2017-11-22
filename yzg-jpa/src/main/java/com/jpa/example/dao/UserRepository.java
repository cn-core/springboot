package com.jpa.example.dao;

import com.jpa.example.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangzhiguo  2017/11/6.
 */
public interface UserRepository extends JpaRepository<User,Serializable> {

    // 注:通过解析方法名创建查询

    /**
     * 查询指定地址的用户
     * @param address   地址
     * @return          用户信息
     */
    List<User> findByAddress(String address);

    /**
     * 通过一些关键字查询  And/Or
     * @param address      地址
     * @param username     用户名称
     * @return             用户信息
     */
    User findByAddressAndUserName(String address,String username);

    /**
     * 基本上SQL体系中的关键词都可以使用，例如：LIKE、 IgnoreCase、 OrderBy
     * @param address   地址
     * @return          用户信息
     */
    User findByAddressLike(String address);

    /**
     * IgnoreCase
     * @return      用户信息
     */
    User findByUserNameIgnoreCase(String username);
}
