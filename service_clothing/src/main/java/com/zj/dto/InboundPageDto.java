package com.zj.dto;

import com.zj.entity.Inbound;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class InboundPageDto extends Inbound {
    private Integer currentPage;
    private Integer pageSize;
    private List<LocalDate> dateRange = new ArrayList<>();
}
