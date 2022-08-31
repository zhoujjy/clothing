package com.zj.controller;

import com.zj.annotation.UserAuthentication;
import com.zj.common.R;
import com.zj.entity.OutboundDetail;
import com.zj.service.OutboundDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outboundDetail")
public class OutboundDetailController {

    @Autowired
    private OutboundDetailService outboundDetailService;

    /**
     * 根据id查明细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getOutboundDetailInfoById(@PathVariable Long id){
        return outboundDetailService.getOutboundDetailInfoById(id);
    }

    /**
     * 新增明细
     * @param outboundDetail
     * @return
     */
    @UserAuthentication
    @PostMapping
    public R addOutboundDetail(@RequestBody OutboundDetail outboundDetail){
        return outboundDetailService.addOutboundDetail(outboundDetail);
    }

    /**
     * 更新明细
     * @param outboundDetail
     * @return
     */
    @UserAuthentication
    @PutMapping
    public R updateOutboundDetail(@RequestBody OutboundDetail outboundDetail){
        return outboundDetailService.updateOutboundDetail(outboundDetail);
    }


    @UserAuthentication
    @DeleteMapping
    public R deleteOutboundDetail(@RequestBody OutboundDetail outboundDetail){
        return outboundDetailService.deleteOutboundDetail(outboundDetail);
    }
}
