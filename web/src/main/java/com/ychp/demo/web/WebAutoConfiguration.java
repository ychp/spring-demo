package com.ychp.demo.web;

import com.ychp.demo.redis.RedisAutoConfiguration;
import com.ychp.demo.user.UserApiAutoConfig;
import com.ychp.demo.user.UserAutoConfiguration;
import com.ychp.demo.web.interceptors.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@ComponentScan
@EnableScheduling
@Configuration
@Import({
        UserApiAutoConfig.class,
        UserAutoConfiguration.class,
        RedisAutoConfiguration.class})
public class WebAutoConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(sessionInterceptor);
    }

}
