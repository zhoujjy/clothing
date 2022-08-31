package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.dto.InboundDto;
import com.zj.dto.InboundPageDto;
import com.zj.entity.Inbound;

public interface InboundService extends IService<Inbound> {
    R addInbound(InboundDto inboundDto);

    R getInboundPageInfo(InboundPageDto inboundPageDto);

    R updateInbound(Inbound inbound);

    R deleteInbound(Long id);
}
