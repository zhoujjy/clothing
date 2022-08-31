package com.zj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Record {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer commId;
    private Integer modelType;//改变渠道，1为货品模块，2为库存模块
    private Integer operator;//操作人
    private Integer operationType;//操作类型，1为加，2为减，3删除，4新增
    private Integer number;
    private LocalDate date;
}
