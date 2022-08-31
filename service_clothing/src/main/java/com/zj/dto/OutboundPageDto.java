package com.zj.dto;

import com.zj.entity.Outbound;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OutboundPageDto extends Outbound {
    private Integer currentPage;
    private Integer pageSize;
    private List<LocalDate> dateRange = new ArrayList<>();
}
