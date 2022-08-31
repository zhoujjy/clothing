package com.zj.dto;

import com.zj.entity.User;
import lombok.Data;

@Data
public class UserPageDto extends User {
    private Integer currentPage;
    private Integer pageSize;
}
