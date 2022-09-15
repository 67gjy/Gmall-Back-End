package com.gmall.backend.controller.dto;

import com.gmall.backend.entity.Menu;
import lombok.Data;

import java.util.List;

//接收前端
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
