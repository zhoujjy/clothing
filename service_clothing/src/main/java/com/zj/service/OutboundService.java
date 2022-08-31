package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.dto.InboundDto;
import com.zj.dto.InboundPageDto;
import com.zj.dto.OutboundDto;
import com.zj.dto.OutboundPageDto;
import com.zj.entity.Inbound;
import com.zj.entity.Outbound;

public interface OutboundService extends IService<Inbound> {

    R addOutbound(OutboundDto outboundDto);

    R getAllOutbound(OutboundPageDto outboundPageDto);

    R updateOutbound(Outbound outbound);

    R deleteOutbound(Long id);
}
