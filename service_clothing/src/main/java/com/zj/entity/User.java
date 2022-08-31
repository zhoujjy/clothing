package com.zj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO) //设置主键自增策略
    private Integer id;
    private String userLogin;
    private String userName;
    private String userPassword;
    private String userRole;
    private Integer isDeleted;
}
