package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.dto.RecordPageDto;
import com.zj.dto.StatisticsDto;
import com.zj.entity.Inbound;
import com.zj.entity.Record;
import com.zj.mapper.CommodityMapper;
import com.zj.mapper.RecordMapper;
import com.zj.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 分页获取记录
     *
     */
    @Override
    public R getAllRecords(RecordPageDto recordPageDto) {
        Page<Record> page = new Page<>(recordPageDto.getCurrentPage(),recordPageDto.getPageSize());
        //条件
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!ObjectUtils.isEmpty(recordPageDto.getCommId()),Record::getCommId,recordPageDto.getCommId());
        queryWrapper.orderByDesc(Record::getDate);
        //日期范围查询
        if(!ObjectUtils.isEmpty(recordPageDto.getDateRange())){
            queryWrapper.ge(Record::getDate,recordPageDto.getDateRange().get(0));
            queryWrapper.le(Record::getDate,recordPageDto.getDateRange().get(1));
        }
        recordMapper.selectPage(page,queryWrapper);
        return new R(Code.SUCCESS,page);
    }

    /**
     * 统计信息
     */
    @Override
    public R getStatistics() {
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setAllOutboundNum(recordMapper.getAllOutboundNum());
        statisticsDto.setTodayOutboundNum(recordMapper.getTodayOutboundNum());
        statisticsDto.setInboundChange(recordMapper.getInboundChange());
        statisticsDto.setStockNum(commodityMapper.getStockNum());
        statisticsDto.setStockNumByType(commodityMapper.getStockNumByType());
        return new R(Code.SUCCESS,statisticsDto);
    }
}
