package com.ychp.demo.web.controller.common;

import com.ychp.demo.common.exception.ResponseException;
import com.ychp.demo.common.model.SkyUser;
import com.ychp.demo.common.util.Encryption;
import com.ychp.demo.common.util.SessionContextUtils;
import com.ychp.demo.user.api.service.UserReadService;
import com.ychp.demo.user.api.service.UserWriteService;
import com.ychp.demo.user.model.User;
import com.ychp.demo.web.component.user.LoginChecker;
import com.ychp.demo.web.util.SkyUserMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yingchengpeng
 * @date 2018-08-27
 */
@RestController
@RequestMapping("/api/user")
public class CoreUsers {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private UserWriteService userWriteService;

    @Autowired
    private LoginChecker loginChecker;

    @GetMapping("{id}")
    public SkyUser detail(@PathVariable Long id) {
        return SkyUserMaker.make(userReadService.findById(id));
    }

    @PostMapping("login")
    public SkyUser login(@RequestParam String name, @RequestParam String password,
                      @RequestParam(required = false) String captcha,
                      HttpServletRequest request) {
        if(loginChecker.needCheckCaptcha(name)
                && loginChecker.checkCaptcha(request.getSession(), captcha)) {
            throw new ResponseException("captcha.mismatch");
        }

        User user = userReadService.login(name, password);

        if(user == null) {
            loginChecker.incrErrorTimes(name);
            throw new ResponseException("user.login.fail");
        }

        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());

        return SkyUserMaker.make(user);
    }

    @PostMapping("logout")
    public Boolean logout(HttpServletRequest request) {
        SkyUser skyUser = SessionContextUtils.currentUser();

        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        return true;
    }

    @PutMapping("change-password")
    public Boolean changePassword(String oldPassword, String newPassword) {
        Long userId = SessionContextUtils.getUserId();
        User user = userReadService.findById(userId);
        if(!Encryption.checkPassword(oldPassword, user.getSalt(), user.getPassword())) {
            throw new ResponseException("user.password.mismatch");
        }

        User toUpdate = new User();
        toUpdate.setId(userId);
        toUpdate.setPassword(Encryption.encrypt3DES(newPassword, user.getSalt()));

        return userWriteService.update(toUpdate);
    }

}
