package com.gmall.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.backend.common.Constants;
import com.gmall.backend.common.Result;
import com.gmall.backend.entity.Dict;
import com.gmall.backend.entity.Menu;
import com.gmall.backend.mapper.DictMapper;
import com.gmall.backend.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  菜单管理接口
 * </p>
 *
 * @author GJY
 * @since 2022-09-06
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @Resource
    private DictMapper dictMapper;

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(menuService.removeByIds(ids));
    }
//    @GetMapping("/ids")
//    public Result findAllIds(@RequestParam(defaultValue = "") String name) {
//        return Result.success(menuService.list().stream().map(Menu::getId));
//    }
    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return Result.success(menuService.findMenus(name));
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String name){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }

}
