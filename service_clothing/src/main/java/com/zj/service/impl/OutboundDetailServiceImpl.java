package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.entity.Commodity;
import com.zj.entity.OutboundDetail;
import com.zj.entity.Record;
import com.zj.mapper.OutboundDetailMapper;
import com.zj.service.CommodityService;
import com.zj.service.OutboundDetailService;
import com.zj.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

@Service
@Transactional
public class OutboundDetailServiceImpl extends ServiceImpl<OutboundDetailMapper, OutboundDetail> implements OutboundDetailService {

    @Autowired
    OutboundDetailMapper outboundDetailMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private CommodityService commodityService;

    /**
     * 新增明细
     * @param outboundDetail
     * @return
     */
    @Override
    public R addOutboundDetail(OutboundDetail outboundDetail) {
        //获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        //条件拼接，查询是否存在相同明细
        LambdaQueryWrapper<OutboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OutboundDetail::getId,outboundDetail.getId());
        queryWrapper.eq(OutboundDetail::getCommId,outboundDetail.getCommId());
        //查询已经保存的信息
        OutboundDetail oldOutboundDetail = outboundDetailMapper.selectOne(queryWrapper);
        if(!ObjectUtils.isEmpty(oldOutboundDetail)) {
            //如果明细中已经存在相同的记录，则提示用户
            return new R(Code.ERROR,"该出库明细已经存在，请勿重复添加！");
        }

        //查询货品信息
        Commodity commodity = commodityService.getById(outboundDetail.getCommId());
        if(ObjectUtils.isEmpty(commodity)||commodity.getCommNumber()<outboundDetail.getCommNumber()) {
            //当库中没有该货品，或者库存小于出库的数量，则提示用户
            return new R(Code.ERROR,"该货品不存在,或者库存库存不足，请检查！");
        }

        //保存明细信息
        if(outboundDetailMapper.insert(outboundDetail)>0) {
            //创建记录对象并设置信息
            Record record = new Record();
            record.setCommId(outboundDetail.getCommId());
            record.setModelType(2);
            record.setOperator(userId);
            record.setDate(LocalDate.now());
            record.setNumber(outboundDetail.getCommNumber());

            //当库中有货品更新货品数量
            commodity.setCommNumber(commodity.getCommNumber()-outboundDetail.getCommNumber());
            commodityService.updateById(commodity);
            //添加记录
            record.setOperationType(2);
            recordService.save(record);
            return new R(Code.SUCCESS,"新增成功");
        }
        return new R(Code.ERROR,"新增失败，请稍后再试！");
    }

    /**
     * 根据id查询明细信息
     * @param id
     * @return
     */
    @Override
    public R getOutboundDetailInfoById(Long id) {
        LambdaQueryWrapper<OutboundDetail> queryWrapper = new LambdaQueryWrapper<OutboundDetail>();
        queryWrapper.eq(OutboundDetail::getId,id);

        return new R(Code.SUCCESS,outboundDetailMapper.selectList(queryWrapper),"查询成功");
    }

    /**
     * 更新明细信息
     * @param outboundDetail
     * @return
     */
    @Override
    public R updateOutboundDetail(OutboundDetail outboundDetail) {
        //获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        //拼接条件
        LambdaQueryWrapper<OutboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OutboundDetail::getId,outboundDetail.getId());
        queryWrapper.eq(OutboundDetail::getCommId,outboundDetail.getCommId());

        //查询已经保存的信息
        OutboundDetail oldOutboundDetail = outboundDetailMapper.selectOne(queryWrapper);
        int newNumber = outboundDetail.getCommNumber();
        int oldNumber = oldOutboundDetail.getCommNumber();
        //查询货品信息
        Commodity commodity = commodityService.getById(outboundDetail.getCommId());

        //创建记录对象并设置信息
        Record record = new Record();
        record.setCommId(outboundDetail.getCommId());
        record.setModelType(2);
        record.setOperator(userId);
        record.setDate(LocalDate.now());

        if(newNumber>oldNumber){
            //记录信息
            int num = newNumber-oldNumber;
            record.setOperationType(2);
            record.setNumber(num);
            recordService.save(record);
            //修改货品数量
            commodity.setCommNumber(commodity.getCommNumber()-num);
            commodityService.updateById(commodity);
        }else if (newNumber<oldNumber){
            int num = oldNumber-newNumber;
            record.setOperationType(1);
            record.setNumber(num);
            recordService.save(record);

            //修改货品数量
            commodity.setCommNumber(commodity.getCommNumber()+num);
            commodityService.updateById(commodity);
        }
        if(outboundDetailMapper.updateById(outboundDetail)>0) {
            return new R(Code.SUCCESS,"修改成功");
        }
        return new R(Code.ERROR,"修改失败，请稍后再试！");
    }

    /**
     * 删除明细信息
     * @param outboundDetail
     * @return
     */
    @Override
    public R deleteOutboundDetail(OutboundDetail outboundDetail) {
        //获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        //拼接条件
        LambdaQueryWrapper<OutboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OutboundDetail::getId,outboundDetail.getId());
        queryWrapper.eq(OutboundDetail::getCommId,outboundDetail.getCommId());
        //删除明细信息
        if(outboundDetailMapper.delete(queryWrapper)>0) {
            //更改货品数量
            Commodity commodity = commodityService.getById(outboundDetail.getCommId());
            commodity.setCommNumber(commodity.getCommNumber()+outboundDetail.getCommNumber());
            commodityService.updateById(commodity);
            //创建记录对象并设置信息
            Record record = new Record();
            record.setCommId(outboundDetail.getCommId());
            record.setModelType(2);
            record.setOperator(userId);
            record.setDate(LocalDate.now());
            record.setOperationType(1);
            record.setNumber(outboundDetail.getCommNumber());
            recordService.save(record);
            return new R(Code.SUCCESS,"删除成功");
        }
        return new R(Code.ERROR,"删除失败，请稍后再试！");
    }

}
