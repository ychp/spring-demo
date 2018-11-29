package com.ychp.demo.web.controller.bean.request.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@ToString
public class UserRegisterRequest implements Serializable {

	private static final long serialVersionUID = -3132782891889934402L;

	/**
	 * 用户名
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * 密码
	 */
	@Getter
	@Setter
	private String password;

	/**
	 * 校验码
	 */
	@Getter
	@Setter
	private String captcha;

}
