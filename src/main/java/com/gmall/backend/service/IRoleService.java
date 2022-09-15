package com.gmall.backend.service;

import com.gmall.backend.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gmall.backend.entity.RoleMenu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GJY
 * @since 2022-09-06
 */
public interface IRoleService extends IService<Role> {
   //void setRoleMenu(Integer roleId, String[] menuIds);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
