package com.ychp.demo.web.controller.blog;

import com.ychp.demo.common.exception.ResponseException;
import com.ychp.demo.common.model.SkyUser;
import com.ychp.demo.common.util.Encryption;
import com.ychp.demo.user.api.service.UserReadService;
import com.ychp.demo.user.api.service.UserWriteService;
import com.ychp.demo.user.model.User;
import com.ychp.demo.web.constant.SessionConstants;
import com.ychp.demo.web.controller.bean.request.user.UserRegisterRequest;
import com.ychp.demo.web.util.SkyUserMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
@RestController
@RequestMapping("/api/user")
public class Users {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserWriteService userWriteService;

    @PostMapping("register")
    public SkyUser register(@RequestBody UserRegisterRequest registerRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();

        String token = (String) session.getAttribute(SessionConstants.CAPTCHA_TOKEN);
        if(!Objects.equals(registerRequest.getCaptcha(), token)) {
            throw new ResponseException("register.captcha.mismatch");
        }

        session.removeAttribute(SessionConstants.CAPTCHA_TOKEN);

        if(StringUtils.isEmpty(registerRequest.getName()) || StringUtils.isEmpty(registerRequest.getPassword())) {
            throw new ResponseException("user.register.info.empty");
        }

        User user = userReadService.findByName(registerRequest.getName());
        if(user != null) {
            throw new ResponseException("user.name.exist");
        }

        user = new User();
        user.setName(registerRequest.getName());
        user.setNickName(registerRequest.getName());
        user.setSalt(Encryption.getSalt());
        user.setPassword(Encryption.encrypt3DES(registerRequest.getPassword(), user.getSalt()));
        userWriteService.create(user);
        session.setAttribute("userId", user.getId());

        return SkyUserMaker.make(user);
    }

}
