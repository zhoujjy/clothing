package com.zj.controller;

import com.zj.annotation.UserAuthentication;
import com.zj.common.R;
import com.zj.entity.InboundDetail;
import com.zj.service.InboundDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inboundDetail")
public class InboundDetailController {

    @Autowired
    InboundDetailService inboundDetailService;

    /**
     * 根据id查明细
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R getInboundDetailInfoById(@PathVariable Long id){
        return inboundDetailService.getInboundDetailInfoById(id);
    }

    /**
     * 新增明细
     * @param inboundDetail
     * @return
     */
    @UserAuthentication
    @PostMapping()
    public R addInboundDetail(@RequestBody InboundDetail inboundDetail){
        return inboundDetailService.addInboundDetail(inboundDetail);
    }

    /**
     * 删除明细
     * @param inboundDetail
     * @return
     */
    @UserAuthentication
    @DeleteMapping
    public R deleteInboundDetail(@RequestBody InboundDetail inboundDetail){
        return inboundDetailService.deleteInboundDetail(inboundDetail);
    }

    /**
     * 更改明细
     * @param inboundDetail
     * @return
     */
    @UserAuthentication
    @PutMapping
    public R updateInboundDetailInfo(@RequestBody InboundDetail inboundDetail){
        return inboundDetailService.updateInboundDetailInfo(inboundDetail);
    }


}
