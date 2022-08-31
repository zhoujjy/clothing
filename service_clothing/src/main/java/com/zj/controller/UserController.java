package com.zj.controller;

import com.zj.annotation.UserAuthentication;
import com.zj.common.R;
import com.zj.dto.UserPageDto;
import com.zj.entity.User;
import com.zj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @return R
     */
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 用户退出
     * @return R
     */
    @PostMapping("/logout")
    public R logout(){
        return userService.logout();
    }

    /**
     * 获得当前登录用户信息
     * @return
     */
    @GetMapping
    public R getUserInfo(){
        return userService.getUserInfo();
    }

    /**
     * 获得所有用户信息
     * @return
     */
    @PostMapping("/userPage")
    public R getAllUser(@RequestBody UserPageDto userPageDto){
        return userService.getAllUser(userPageDto);
    }

    @GetMapping("/getUserName/{id}")
    public R getUserName(@PathVariable int id){
        return userService.getUserName(id);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/addUser")
    public R addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 删除用户
     */
    @UserAuthentication
    @DeleteMapping
    public R deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    /**
     * 修改用户
     */
    @PutMapping
    @UserAuthentication
    public R updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 获得在线的用户信息
     * @return
     */
    @GetMapping("/onlineUser")
    public R getOnlineUser(){
        return userService.getOnlineUser();
    }

    /**
     * 踢出用户
     */
    @DeleteMapping("/kickOut/{id}")
    @UserAuthentication
    public R kickOut(@PathVariable int id){
        return userService.kickOut(id);
    }
}
