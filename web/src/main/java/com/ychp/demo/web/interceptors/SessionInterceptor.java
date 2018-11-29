package com.ychp.demo.web.interceptors;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.ychp.demo.common.exception.ResponseException;
import com.ychp.demo.common.model.SkyUser;
import com.ychp.demo.common.util.SessionContextUtils;
import com.ychp.demo.user.api.service.UserReadService;
import com.ychp.demo.user.model.User;
import com.ychp.demo.web.util.SkyUserMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yingchengpeng
 * @date 2018-08-08
 */
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserReadService userReadService;

    @Value("${cache.expire.time:60}")
    private Long expiredTime;
    private LoadingCache<String, List<String>> white;

    @PostConstruct
    public void init() {

        white = CacheBuilder.newBuilder()
                .expireAfterWrite(expiredTime, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(1000)
                .build(new CacheLoader<String, List<String>>() {
                    @Override
                    public List<String> load(String s) {
                        return Lists.newArrayList(
                                "/api/address/.*",
                                "/api/article/.*",
                                "/api/comment/.*",
                                "/api/category/.*",
                                "/api/label/.*",
                                "/api/friend-link/visible",
                                "/api/see-log",
                                "/api/file/.*",
                                "/api/user/captcha",
                                "/api/user/register",
                                "/api/user/login",
                                "/api/search/.*",
                                "/api/v2/api-docs",
                                "/v2/api-docs",
                                "/swagger.*",
                                "/webjars.*",
                                "/error",
                                "/csrf",
                                "/index",
                                "/");
                    }
                });

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        String uri = request.getRequestURI();

        if(userId != null) {
            User user = userReadService.findById(Long.valueOf(userId.toString()));
            SkyUser skyUser = SkyUserMaker.make(user);
            SessionContextUtils.put(skyUser);
            return true;
        }

        if(contains(white.get("all"), uri)) {
            return true;
        }

        throw new ResponseException(401, "user.not.login");
    }

    private boolean contains(List<String> uris, String uri) {
        for(String uriReg : uris) {
            if(uri.matches(uriReg)) {
                return true;
            }
        }
        return false;
    }

}
