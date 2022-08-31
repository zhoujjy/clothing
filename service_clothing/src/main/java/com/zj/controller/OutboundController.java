package com.zj.controller;

import com.zj.annotation.UserAuthentication;
import com.zj.common.R;
import com.zj.dto.OutboundDto;
import com.zj.dto.OutboundPageDto;
import com.zj.entity.Outbound;
import com.zj.service.OutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outbound")
public class OutboundController {

    @Autowired
    private OutboundService outboundService;

    /**
     * 分页查询出库单
     * @return
     */
    @PostMapping("/page")
    public R getAllOutbound(@RequestBody OutboundPageDto outboundPageDto){
        return outboundService.getAllOutbound(outboundPageDto);
    }

    /**
     * 新增出库单
     * @param outboundDto
     * @return
     */
    @UserAuthentication
    @PostMapping
    public R addOutbound(@RequestBody OutboundDto outboundDto){
        return outboundService.addOutbound(outboundDto);
    }

    /**
     * 修改出库单
     * @param outbound
     * @return
     */
    @UserAuthentication
    @PutMapping
    public R updateOutbound(@RequestBody Outbound outbound){
        return outboundService.updateOutbound(outbound);
    }

    /**
     * 删除出库单
     * @param id
     * @return
     */
    @UserAuthentication
    @DeleteMapping("/{id}")
    public R deleteOutbound(@PathVariable("id") Long id){
        return outboundService.deleteOutbound(id);
    }
}
