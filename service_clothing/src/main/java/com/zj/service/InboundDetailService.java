package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.entity.InboundDetail;

public interface InboundDetailService extends IService<InboundDetail> {
    R getInboundDetailInfoById(Long id);

    R updateInboundDetailInfo(InboundDetail inboundDetail);

    R addInboundDetail(InboundDetail inboundDetail);

    R deleteInboundDetail(InboundDetail inboundDetail);
}
