package com.zj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 出库总量
     * @return
     */
    @Select("SELECT SUM(number) FROM record WHERE operation_type=1 or operation_type =4")
    Integer getAllOutboundNum();

    /**
     * 今日出库总量
     */
    @Select("SELECT SUM(number) FROM record WHERE (operation_type=1 or operation_type =4) AND date=CURDATE()")
    Integer getTodayOutboundNum();

    /**
     * 入库变化
     */
    @Select("SELECT SUM(number) FROM record GROUP BY date ORDER BY date")
    ArrayList<Integer> getInboundChange();
}
