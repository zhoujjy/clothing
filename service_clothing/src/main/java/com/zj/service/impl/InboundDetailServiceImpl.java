package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.entity.Commodity;
import com.zj.entity.Inbound;
import com.zj.entity.InboundDetail;
import com.zj.entity.Record;
import com.zj.mapper.InboundDetailMapper;
import com.zj.service.CommodityService;
import com.zj.service.InboundDetailService;
import com.zj.service.RecordService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Service
@Transactional
public class InboundDetailServiceImpl extends ServiceImpl<InboundDetailMapper, InboundDetail> implements InboundDetailService {

    @Autowired
    InboundDetailMapper inboundDetailMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private CommodityService commodityService;

    /**
     * 根据id查询入库详细
     * @param id
     * @return
     */
    @Override
    public R getInboundDetailInfoById(Long id) {
        LambdaQueryWrapper<InboundDetail> queryWrapper = new LambdaQueryWrapper<InboundDetail>();
        queryWrapper.eq(InboundDetail::getId,id);

        return new R(Code.SUCCESS,inboundDetailMapper.selectList(queryWrapper),"查询成功");
    }

    /**
     * 更新入库明细
     * @param inboundDetail
     * @return
     */
    @Override
    public R updateInboundDetailInfo(InboundDetail inboundDetail) {
        //获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();

        //条件拼接
        LambdaQueryWrapper<InboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InboundDetail::getId,inboundDetail.getId());
        queryWrapper.eq(InboundDetail::getCommId,inboundDetail.getCommId());

        //查询已经保存的信息
        InboundDetail oldInboundDetail = inboundDetailMapper.selectOne(queryWrapper);

        int newCommNumber = inboundDetail.getCommNumber();
        int oldCommNumber = oldInboundDetail.getCommNumber();

        //查询货品信息
        Commodity commodity = commodityService.getById(inboundDetail.getCommId());

        //创建记录对象并设置信息
        Record record = new Record();
        record.setCommId(inboundDetail.getCommId());
        record.setModelType(2);
        record.setOperator(userId);
        record.setDate(LocalDate.now());

        if(newCommNumber>oldCommNumber){
            //记录信息
            int num = newCommNumber-oldCommNumber;
            record.setOperationType(1);
            record.setNumber(num);
            recordService.save(record);
            //修改货品数量
            commodity.setCommNumber(commodity.getCommNumber()+num);
            commodityService.updateById(commodity);
        }else if (newCommNumber<oldCommNumber){
            int num = oldCommNumber-newCommNumber;
            record.setOperationType(2);
            record.setNumber(num);
            recordService.save(record);

            //修改货品数量
            commodity.setCommNumber(commodity.getCommNumber()-num);
            commodityService.updateById(commodity);
        }

        if(inboundDetailMapper.update(inboundDetail,queryWrapper)>0){
            return new R(Code.SUCCESS,"修改成功");
        }
        return new R(Code.ERROR,"修改失败，请稍后再试！");

    }

    /**
     * 添加入库明细
     * @param inboundDetail
     * @return
     */
    @Override
    public R addInboundDetail(InboundDetail inboundDetail) {
        //获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        //条件拼接，查询是否存在相同明细
        LambdaQueryWrapper<InboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InboundDetail::getId,inboundDetail.getId());
        queryWrapper.eq(InboundDetail::getCommId,inboundDetail.getCommId());
        //查询已经保存的信息
        InboundDetail oldInboundDetail = inboundDetailMapper.selectOne(queryWrapper);
        if(!ObjectUtils.isEmpty(oldInboundDetail)){
            //如果明细中已经存在相同的记录，则提示用户
            return new R(Code.ERROR,"该货品已经存在，请勿重复添加！");
        }

        //查询货品信息
        Commodity commodity = commodityService.getById(inboundDetail.getCommId());

        //保存明细信息
        if(inboundDetailMapper.insert(inboundDetail)>0) {
            //创建记录对象并设置信息
            Record record = new Record();
            record.setCommId(inboundDetail.getCommId());
            record.setModelType(2);
            record.setOperator(userId);
            record.setDate(LocalDate.now());
            //record.setOperationType(1);
            record.setNumber(inboundDetail.getCommNumber());

            if(ObjectUtils.isEmpty(commodity)) {
                //当库中没有该货品，则新增货品
                Commodity c = new Commodity();
                c.setId(inboundDetail.getCommId());
                c.setCommNumber(inboundDetail.getCommNumber());
                c.setCommColor(inboundDetail.getCommColor());
                c.setCommSize(inboundDetail.getCommSize());
                c.setCommName(inboundDetail.getCommName());
                commodityService.save(c);

                //添加记录
                record.setOperationType(4);
                record.setNumber(inboundDetail.getCommNumber());
                recordService.save(record);
                return new R(Code.SUCCESS,"新增成功");
            }

            //当库中有货品更新货品数量
            commodity.setCommNumber(commodity.getCommNumber()+inboundDetail.getCommNumber());
            commodityService.updateById(commodity);
            //添加记录
            record.setOperationType(1);
            recordService.save(record);
            return new R(Code.SUCCESS,"新增成功");
        }
        return new R(Code.ERROR,"新增失败，请稍后再试！");
    }

    /**
     * 删除入库明细
     * @param inboundDetail
     * @return
     */
    @Override
    public R deleteInboundDetail(InboundDetail inboundDetail) {
        //获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        //条件拼接
        LambdaQueryWrapper<InboundDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InboundDetail::getId,inboundDetail.getId());
        queryWrapper.eq(InboundDetail::getCommId,inboundDetail.getCommId());
        //删除明细信息
        if(inboundDetailMapper.delete(queryWrapper)>0) {
            //更改货品数量
            Commodity commodity = commodityService.getById(inboundDetail.getCommId());
            commodity.setCommNumber(commodity.getCommNumber()-inboundDetail.getCommNumber());
            commodityService.updateById(commodity);
            //创建记录对象并设置信息
            Record record = new Record();
            record.setCommId(inboundDetail.getCommId());
            record.setModelType(2);
            record.setOperator(userId);
            record.setDate(LocalDate.now());
            record.setOperationType(2);
            record.setNumber(inboundDetail.getCommNumber());
            recordService.save(record);
            return new R(Code.SUCCESS,"删除成功");
        }
        return new R(Code.ERROR,"删除失败，请稍后再试！");
    }


}
