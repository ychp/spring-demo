package com.ychp.demo.user.api.service;


import com.ychp.demo.common.model.paging.Paging;
import com.ychp.demo.user.api.bean.query.UserCriteria;
import com.ychp.demo.user.model.User;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
public interface UserReadService {

    /**
     * 根据id查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    User findById(Long id);

    /**
     * 根据id查询用户基础信息
     *
     * @param id 主键id
     * @return 查询结果
     */
    User findDetailById(Long id);

    /**
     * 登录
     * @param name 用户名
     * @param password 密码
     * @return 用户
     */
    User login(String name, String password);

    /**
     * 根据条件获取分页
     *
     * @param criteria 条件
     * @return 分页结果
     */
    Paging<User> paging(UserCriteria criteria);

    /**
     * 根据用户名查询
     *
     * @param name 用户名
     * @return 查询结果
     */
    User findByName(String name);

}