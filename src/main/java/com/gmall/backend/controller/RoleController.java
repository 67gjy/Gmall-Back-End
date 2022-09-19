package com.gmall.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.backend.common.Result;
import com.gmall.backend.entity.Role;
import com.gmall.backend.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  角色管理接口
 * </p>
 *
 * @author GJY
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(roleService.removeByIds(ids));
    }
    @GetMapping
    public Result findAll() {
        return Result.success(roleService.list());
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

//    @PostMapping("/roleMenu/{roleId}")
//    public Result roleMenu(@PathVariable Integer roleId,@RequestParam("array") String menuIdsArr) {
//        String[] menuIds =  menuIdsArr.split(",");
//        roleService.setRoleMenu(roleId,menuIds);
//        return Result.success();
//    }
    @PostMapping("/roleMenu/{roleId}")
    @ResponseBody
    public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId,menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return Result.success(roleService.getRoleMenu(roleId));
    }

}
