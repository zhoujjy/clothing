package com.zj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Outbound {
    @TableId(type = IdType.ASSIGN_ID) //指定自增策略,雪花算法
    @JsonFormat(shape = JsonFormat.Shape.STRING) //类型转换，解决前台js精度丢失
    private Long id;
    private LocalDate date;
    private Integer operatorId;
    private String recipient;
    private String recipientPhone;
    private String remark;
    private Integer isDeleted;
}
