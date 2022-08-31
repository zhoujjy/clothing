package com.zj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.entity.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 库存余量
     * @return
     */
    @Select("SELECT SUM(comm_number) FROM commodity WHERE is_deleted=0")
    Integer getStockNum();

    /**
     * 货品和数量
     */
    @Select("SELECT comm_name,comm_number FROM commodity WHERE is_deleted=0 GROUP BY id")
    ArrayList<HashMap<String,Object>> getStockNumByType();
}
