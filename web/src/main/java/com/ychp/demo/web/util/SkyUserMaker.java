package com.ychp.demo.web.util;


import com.ychp.demo.common.model.SkyUser;
import com.ychp.demo.user.model.User;
import org.springframework.cglib.beans.BeanCopier;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class SkyUserMaker {

    private static BeanCopier copier = BeanCopier.create(User.class, SkyUser.class, false);

    public static SkyUser make(User user) {
        SkyUser skyUser = new SkyUser();
        copier.copy(user, skyUser, null);
        return skyUser;
    }
}
