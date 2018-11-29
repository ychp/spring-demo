package com.ychp.demo.web.controller.admin;

import com.ychp.demo.common.model.paging.Paging;
import com.ychp.demo.user.api.bean.query.UserCriteria;
import com.ychp.demo.user.api.service.UserReadService;
import com.ychp.demo.user.api.service.UserWriteService;
import com.ychp.demo.user.enums.UserStatusEnum;
import com.ychp.demo.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@RestController
@RequestMapping("/api/admin/user")
public class AdminUsers {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserWriteService userWriteService;

    @GetMapping("{id}/findById")
    public User detail(@PathVariable Long id) {
        return userReadService.findDetailById(id);
    }

    @GetMapping("paging")
    public Paging<User> paging(UserCriteria criteria) {
        return userReadService.paging(criteria);
    }

    @PutMapping("{id}/frozen")
    public Boolean frozen(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(UserStatusEnum.FROZEN.getValue());
        return userWriteService.update(user);
    }

    @PutMapping("{id}/unfrozen")
    public Boolean unfrozen(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        return userWriteService.update(user);
    }

}
