package com.gmall.backend.service;

import com.gmall.backend.controller.dto.UserDTO;
import com.gmall.backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GJY
 * @since 2022-08-23
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
