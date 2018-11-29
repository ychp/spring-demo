package com.ychp.demo.user.server.repository;

import com.ychp.demo.mybatis.repository.BaseRepository;
import com.ychp.demo.user.model.User;
import org.springframework.stereotype.Repository;

/**
* @author yingchengpeng
* @date 2018/08/08
*/
@Repository
public class UserRepository extends BaseRepository<User, Long> {

    public User findByName(String name) {
        return getSqlSession().selectOne(sqlId("findByName"), name);
    }
}