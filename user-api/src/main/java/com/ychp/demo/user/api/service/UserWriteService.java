package com.ychp.demo.user.api.service;


import com.ychp.demo.user.model.User;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
public interface UserWriteService {

    /**
     * 创建用户
     * @param user 需要创建的数据
     * @return 主键
     */
    Long create(User user);

    /**
     * 更新用户
     * @param user 需要更新的数据
     * @return 操作结果
     */
    Boolean update(User user);

    /**
     * 根据主键id删除用户
     * @param id 主键
     * @return 操作结果
     */
    Boolean delete(Long id);
}