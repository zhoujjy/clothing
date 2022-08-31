package com.zj.controller;

import com.zj.annotation.UserAuthentication;
import com.zj.common.R;
import com.zj.dto.CommodityPageDto;
import com.zj.dto.UserPageDto;
import com.zj.entity.Commodity;
import com.zj.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    /**
     * 获得所有货品信息
     * @return
     */
    @PostMapping("/commodityPage")
    public R getAllCommodity(@RequestBody CommodityPageDto commodityPageDto){
        return commodityService.getAllCommodity(commodityPageDto);
    }

    /**
     * 新增货品
     * @param commodity
     * @return
     */
    @UserAuthentication
    @PostMapping
    public R addCommodity(@RequestBody Commodity commodity){
        return commodityService.addCommodity(commodity);
    }

    /**
     * 删除货品
     * @param id
     * @return
     */
    @UserAuthentication
    @DeleteMapping("/{id}")
    public R deleteCommodity(@PathVariable("id") Integer id){
        return commodityService.deleteCommodity(id);
    }

    /**
     * 修改货品
     * @param commodity
     * @return
     */
    @UserAuthentication
    @PutMapping()
    public R updateCommodity(@RequestBody Commodity commodity){
        return commodityService.updateCommodity(commodity);
    }

    /**
     * 根据id查询货品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getCommodityInfoById(@PathVariable Integer id){
        return commodityService.getCommodityInfoById(id);
    }

}
