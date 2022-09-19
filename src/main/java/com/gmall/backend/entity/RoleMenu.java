package com.gmall.backend.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
//父子级
@TableName("sys_role_menu")
@Data
public class RoleMenu {
//    @TableId(value = "id", type = IdType.AUTO)
    private Integer roleId;
    private Integer menuId;
}
