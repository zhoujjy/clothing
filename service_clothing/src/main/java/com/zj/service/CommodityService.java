package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.dto.CommodityPageDto;
import com.zj.entity.Commodity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CommodityService extends IService<Commodity> {

    R getAllCommodity(CommodityPageDto commodityPageDto);

    R addCommodity(Commodity commodity);

    R deleteCommodity(Integer id);

    R updateCommodity(Commodity commodity);

    R getCommodityInfoById(Integer id);

    Integer getStockNum();

    ArrayList<HashMap<String,Object>> getStockNumByType();
}
