package com.zj.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class StatisticsDto {
    private Integer allOutboundNum;
    private Integer todayOutboundNum;
    private ArrayList<Integer> inboundChange;
    private Integer stockNum;
    private ArrayList<HashMap<String,Object>> stockNumByType;
}
