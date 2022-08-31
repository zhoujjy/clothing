package com.zj.dto;

import com.zj.entity.Inbound;
import com.zj.entity.InboundDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InboundDto extends Inbound {
    private List<InboundDetail> detail = new ArrayList<>();
}
