package com.zj.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class OutboundDetail {
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING) //类型转换，解决前台js精度丢失
    private Long id;
    private Integer commId;
    private String commName;
    private String commColor;
    private String commSize;
    private Integer commNumber;
    private Integer isDeleted;
}
