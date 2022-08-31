package com.zj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Commodity {
    @TableId
    private Integer id;
    private String commName;
    private String commColor;
    private String commSize;
    private Integer commNumber;
    private Integer isDeleted;
}
