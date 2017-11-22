package com.mybatis.example.mapper;

import com.mybatis.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangzhiguo  2017/11/3.
 */
@Mapper
@Repository
public interface Test {

    /**
     * 用户查询
     *
     * @return 所有用户
     */
    @NotEmpty
    List<User> findUser();
}
