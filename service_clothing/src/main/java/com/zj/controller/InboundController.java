package com.zj.controller;

import com.zj.annotation.UserAuthentication;
import com.zj.common.R;
import com.zj.dto.InboundDto;
import com.zj.dto.InboundPageDto;
import com.zj.entity.Inbound;
import com.zj.service.InboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inbound")
public class InboundController {

    @Autowired
    private InboundService inboundService;

    /**
     * 查询入库单
     * @param inboundPageDto
     * @return
     */
    @PostMapping("/inboundPage")
    public R getInboundPageInfo(@RequestBody InboundPageDto inboundPageDto){
        return inboundService.getInboundPageInfo(inboundPageDto);
    }

    /**
     * 新增入库单
     * @param inboundDto
     * @return
     */
    @UserAuthentication
    @PostMapping
    public R addInbound(@RequestBody InboundDto inboundDto){
        return inboundService.addInbound(inboundDto);
    }

    /**
     * 修改入库单
     * @param inbound
     * @return
     */
    @UserAuthentication
    @PutMapping
    public R updateInbound(@RequestBody Inbound inbound){
        return inboundService.updateInbound(inbound);
    }

    /**
     * 删除入库单
     * @param id
     * @return
     */
    @UserAuthentication
    @DeleteMapping("/{id}")
    public R deleteInbound(@PathVariable("id") Long id){
        return inboundService.deleteInbound(id);
    }
}
