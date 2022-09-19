package com.gmall.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author GJY
 * @since 2022-09-06
 */
@Getter
@Setter
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;
        @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

        private String name;

        private String path;

        private String icon;

        private String description;

        @TableField(exist = false)
        private List<Menu> children;

        private Integer pid;

        private String pagePath;

}
