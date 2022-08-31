package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.dto.UserPageDto;
import com.zj.entity.User;
import com.zj.mapper.UserMapper;
import com.zj.service.UserService;
import com.zj.utils.JwtUtil;
import com.zj.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户登录
     * @param user
     * @return R
     */
    @Override
    public R login(User user) {

        //1. 获取密码
        String password = user.getUserPassword();
        //加密密码
        password = DigestUtils.md5DigestAsHex(password.getBytes());


        //2. 根据用户名查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserLogin, user.getUserLogin());
        User queryUser = userMapper.selectOne(wrapper);

        //3. 判断用户是否存在
        if(ObjectUtils.isEmpty(queryUser)){
            return new R(Code.ERROR, "用户不存在");
        }

        //4.查询到了，判断密码是否正确
        if(!queryUser.getUserPassword().equals(password)) {
            return new R(Code.ERROR, "密码错误");
        }

        //5. 登录成功
        //5.1 生成token
        String token = JwtUtil.createToken(queryUser.getId().toString());
        //5.2 将用户数据存入redis 并设置20分钟过期时间
        redisUtils.setObject("user:" + queryUser.getId(), queryUser, 20);
        //5.3 返回token
        Map<String,String> map = new HashMap<String,String>();
        map.put("token", token);
        return new R(Code.SUCCESS,map ,"登录成功" );
    }


    /**
     * 用户退出
     * @return
     */
    @Override
    public R logout() {
        //1. 获取userId
        int userId = (int) BaseContext.getCurrentId();
        String redisUserKey = "user:" + userId;
        //2. 删除redis中的userKey
        if(!ObjectUtils.isEmpty(redisUserKey)){
            redisUtils.removeValue(redisUserKey);
        }
        return new R(Code.SUCCESS, "退出成功");
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Override
    public R getUserInfo() {
        //1. 获取redis userKey
        int userId = (int) BaseContext.getCurrentId();
        String redisUserKey = "user:" + userId;
        //2. 获取redis中的信息
        User user =  redisUtils.getObject(redisUserKey, User.class);
        return new R(Code.SUCCESS, user, "获取用户角色成功");
    }

    /**
     * 获得所有用户信息
     * @return
     */
    @Override
    public R getAllUser(UserPageDto userPageDto) {
        // 1.分页构造器
        Page<User> pageInfo = new Page<User>(userPageDto.getCurrentPage(), userPageDto.getPageSize());
        // 2. 拼接条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(userPageDto.getUserLogin()!=null,User::getUserLogin,userPageDto.getUserLogin());
        queryWrapper.like(userPageDto.getUserName()!=null,User::getUserName,userPageDto.getUserName());
        //判断是否是管理员,如果是管理员则不限制,不是管理员就只能显示自己的信息
        int userId = (int) BaseContext.getCurrentId();
        String redisUserKey = "user:" + userId;
        User userRedis = redisUtils.getObject(redisUserKey, User.class);
        String userRole = userRedis.getUserRole();
        String userLogin = userRedis.getUserLogin();
        queryWrapper.eq(!userRole.equals("ADMIN"),User::getUserLogin,userLogin);
        // 3.查询
        userMapper.selectPage(pageInfo, queryWrapper);

        // 4.返回
        return new R(Code.SUCCESS,pageInfo);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Override
    public R addUser(User user) {
        //1. 判断用户是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserLogin, user.getUserLogin());
        User queryUser = userMapper.selectOne(wrapper);
        if(!ObjectUtils.isEmpty(queryUser)){
            return new R(Code.ERROR, "用户已存在");
        }
        //2. 添加用户
        user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        int i = userMapper.insert(user);
        if(i>0) return new R(Code.SUCCESS, "添加用户成功");
        return new R(Code.ERROR, "添加用户失败,请稍后再试");
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @Override
    public R deleteUser(User user) {
        int i = userMapper.deleteById(user.getId());
        if(i>0) return new R(Code.SUCCESS, "删除用户成功");
        return new R(Code.ERROR, "删除用户失败,请稍后再试");
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Override
    public R updateUser(User user) {
        //1. 获取用户角色
        int userId = (int) BaseContext.getCurrentId();
        String redisUserKey = "user:" + userId;
        User userRedis = redisUtils.getObject(redisUserKey, User.class);
        String userRole = userRedis.getUserRole();
        //2. 判断是否是管理员,如果是管理员则不限制,不是管理员就只能修改自己的信息
        if(!userRole.equals("ADMIN")){
            //判断是否是自己的信息
            if(!user.getUserLogin().equals(userRedis.getUserLogin())){
                return new R(Code.ERROR, "您没有权限修改其他人的信息");
            }
        }
        //查询用户信息
        User queryUser = userMapper.selectById(user.getId());
        //如果密码相同说明不需要修改密码
        if(queryUser.getUserPassword().equals(user.getUserPassword())) {
            user.setUserPassword(null);
        }
        //不相同说明密码需要修改，md5加密
        else{
            user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes()));
        }
        if (userMapper.updateById(user) > 0) {
            return new R(Code.SUCCESS, "修改用户信息成功");
        }
        return new R(Code.ERROR, "修改用户信息失败,请稍后再试");
    }

    /**
     * 查询用户姓名
     * @param id
     * @return
     */
    @Override
    public R getUserName(int id) {

        Map<String,String> map = new HashMap<>();
        map.put("userName",userMapper.selectById(id).getUserName());

        return new R(Code.SUCCESS,map);
    }

    /**
     * 获得在线用户信息
     * @return
     */
    @Override
    public R getOnlineUser() {
        List<User> users = redisUtils.getAllData(User.class);
        List<User> returnUsers = new ArrayList<>();
        for (User user : users) {
            User u = new User();
            u.setId(user.getId());
            u.setUserName(user.getUserName());
            u.setUserLogin(user.getUserLogin());
            u.setUserRole(user.getUserRole());
            returnUsers.add(u);
        }
        return new R(Code.SUCCESS,returnUsers);
    }

    /**
     * 踢出用户
     * @param id
     * @return
     */
    @Override
    public R kickOut(int id) {
        String redisUserKey = "user:" + id;
        // 删除redis中的userKey
        if(!ObjectUtils.isEmpty(redisUserKey)){
            redisUtils.removeValue(redisUserKey);
        }
        return new R(Code.SUCCESS, "操作成功");
    }
}
