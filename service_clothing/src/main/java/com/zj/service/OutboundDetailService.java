package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.entity.OutboundDetail;

public interface OutboundDetailService extends IService<OutboundDetail> {


    R addOutboundDetail(OutboundDetail outboundDetail);

    R getOutboundDetailInfoById(Long id);

    R updateOutboundDetail(OutboundDetail outboundDetail);

    R deleteOutboundDetail(OutboundDetail outboundDetail);
}
