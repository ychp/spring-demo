package com.ychp.demo.user.server.service;

import com.ychp.demo.common.exception.ResponseException;
import com.ychp.demo.user.api.service.UserWriteService;
import com.ychp.demo.user.enums.UserStatusEnum;
import com.ychp.demo.user.model.User;
import com.ychp.demo.user.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yingchengpeng
 * @date 2018/8/9
 */
@Service
public class UserWriteServiceImpl implements UserWriteService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Long create(User user) {
		try {
			user.setStatus(UserStatusEnum.NORMAL.getValue());
			userRepository.create(user);
			return user.getId();
		} catch (Exception e) {
			throw new ResponseException("user.create.fail", e.getMessage(), e.getCause());
		}
	}

	@Override
	public Boolean update(User user) {
		try {
			return userRepository.update(user);
		} catch (Exception e) {
			throw new ResponseException("user.update.fail", e.getMessage(), e.getCause());
		}
	}

	@Override
	public Boolean delete(Long id) {
		try {
			return userRepository.delete(id);
		} catch (Exception e) {
			throw new ResponseException("user.delete.fail", e.getMessage(), e.getCause());
		}
	}
}
