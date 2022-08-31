package com.zj.dto;

import com.zj.entity.Outbound;
import com.zj.entity.OutboundDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OutboundDto extends Outbound {
    private List<OutboundDetail> detail = new ArrayList<>();
}
