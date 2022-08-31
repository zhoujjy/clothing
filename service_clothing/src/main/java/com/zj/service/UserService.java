package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.dto.UserPageDto;
import com.zj.entity.User;

public interface UserService extends IService<User> {
    R login(User user);

    R logout();

    R getUserInfo();

    R getAllUser(UserPageDto userPageDto);

    R addUser(User user);

    R deleteUser(User user);

    R updateUser(User user);

    R getUserName(int id);

    R getOnlineUser();

    R kickOut(int id);
}
