package com.zj.dto;

import com.zj.entity.Commodity;
import lombok.Data;

@Data
public class CommodityPageDto extends Commodity {
    private Integer currentPage;
    private Integer pageSize;
}
