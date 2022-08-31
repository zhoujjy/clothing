package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.dto.OutboundDto;
import com.zj.dto.OutboundPageDto;
import com.zj.entity.Inbound;
import com.zj.entity.Outbound;
import com.zj.entity.OutboundDetail;
import com.zj.exception.BusinessException;
import com.zj.mapper.InboundMapper;
import com.zj.mapper.OutboundMapper;
import com.zj.service.CommodityService;
import com.zj.service.OutboundDetailService;
import com.zj.service.OutboundService;
import com.zj.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional //事务注解
@Service
public class OutboundServiceImpl extends ServiceImpl<InboundMapper, Inbound> implements OutboundService {

    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private OutboundDetailService outboundDetailService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private RecordService recordService;

    /**
     * 新增出库单
     * @param outboundDto
     * @return
     */
    @Override
    public R addOutbound(OutboundDto outboundDto) {
        //1. 获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        outboundDto.setOperatorId(userId);
        //获取出库单详细信息
        List<OutboundDetail > outboundDetailList = outboundDto.getDetail();
        //保存出库单信息
        if(outboundMapper.insert(outboundDto)<=0) {
            return new R(Code.ERROR,"新增出库单失败,请稍后重试");
        }
        //获得单据号
        Long outboundId = outboundDto.getId();
        //保存出库单详细信息
        for(OutboundDetail outboundDetail : outboundDetailList) {
            outboundDetail.setId(outboundId);
            R r = outboundDetailService.addOutboundDetail(outboundDetail);
            if(!Objects.equals(r.getCode(), Code.SUCCESS)) {
                throw new BusinessException(r.getCode(),r.getMsg());
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("outboundId",outboundId.toString());

        return new R(Code.SUCCESS,map,"新增出库单成功");
    }

    /**
     * 分页查询出库单
     * @param outboundPageDto
     * @return
     */
    @Override
    public R getAllOutbound(OutboundPageDto outboundPageDto) {
        //创建分页对象
        Page<Outbound> page = new Page<Outbound>(outboundPageDto.getCurrentPage(),outboundPageDto.getPageSize());
        //拼接查询条件
        LambdaQueryWrapper<Outbound> queryWrapper = new LambdaQueryWrapper<Outbound>();
        //模糊查询单据号
        queryWrapper.like(!ObjectUtils.isEmpty(outboundPageDto.getId()),Outbound::getId,outboundPageDto.getId());
        //模糊查询接收人
        queryWrapper.like(!ObjectUtils.isEmpty(outboundPageDto.getRecipient()),Outbound::getRecipient,outboundPageDto.getRecipient());
        //日期范围查询
        if(!ObjectUtils.isEmpty(outboundPageDto.getDateRange())){
            queryWrapper.ge(Outbound::getDate,outboundPageDto.getDateRange().get(0));
            queryWrapper.le(Outbound::getDate,outboundPageDto.getDateRange().get(1));
        }
        //查询出库单信息
        outboundMapper.selectPage(page,queryWrapper);
        return new R(Code.SUCCESS,page);
    }

    /**
     * 修改出库单
     * @param outbound
     * @return
     */
    @Override
    public R updateOutbound(Outbound outbound) {
        //1. 获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        outbound.setOperatorId(userId);
        if(outboundMapper.updateById(outbound)<=0){
            return new R(Code.ERROR,"修改出库单失败,请稍后重试");
        }
        return new R(Code.SUCCESS,"修改出库单成功！");
    }

    /**
     * 删除出库单
     * @param id
     * @return
     */
    @Override
    public R deleteOutbound(Long id) {
        //获取出库单明细信息
        LambdaQueryWrapper<OutboundDetail> queryWrapper = new LambdaQueryWrapper<OutboundDetail>();
        queryWrapper.eq(OutboundDetail::getId,id);
        List<OutboundDetail> outboundDetailList = outboundDetailService.list(queryWrapper);
        boolean flag = true;
        //删除出库单明细信息
        if(outboundDetailList.size()>0){
            for(OutboundDetail outboundDetail : outboundDetailList){
                R r = outboundDetailService.deleteOutboundDetail(outboundDetail);
                if(!Objects.equals(r.getCode(),Code.SUCCESS)){
                    flag = false;
                    break;
                }
            }
        }
        //删除出库单信息
        if(outboundMapper.deleteById(id)<=0){
            flag = false;
        }
        if(flag) return new R(Code.SUCCESS, "删除出库单成功！");
        return new R(Code.ERROR, "删除出库单失败！");
    }
}
