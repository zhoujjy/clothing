package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.dto.InboundDto;
import com.zj.dto.InboundPageDto;
import com.zj.entity.Commodity;
import com.zj.entity.Inbound;
import com.zj.entity.InboundDetail;
import com.zj.entity.Record;
import com.zj.mapper.InboundMapper;
import com.zj.service.CommodityService;
import com.zj.service.InboundDetailService;
import com.zj.service.InboundService;
import com.zj.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional //事务注解
@Service
public class InboundServiceImpl extends ServiceImpl<InboundMapper, Inbound> implements InboundService {

    @Autowired
    private InboundMapper inboundMapper;
    @Autowired
    private InboundDetailService inboundDetailService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private RecordService recordService;


    /**
     * 新增入库单
     * @param inboundDto
     * @return
     */
    @Override
    public R addInbound(InboundDto inboundDto) {
        //1. 获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        inboundDto.setOperatorId(userId);

        // 2. 获取入库单详细信息
        List<InboundDetail> inboundDetailList = inboundDto.getDetail();

        //3. 保存入库单信息
        if(inboundMapper.insert(inboundDto)<=0){
            return new R(Code.ERROR,"新增入库单失败,请稍后重试");
        }
        //4.查询入库单号
        Long inboundId = inboundDto.getId();

        //5.保存入库单详细信息、货品信息和记录
        for (InboundDetail inboundDetail : inboundDetailList) {
            inboundDetail.setId(inboundId);
            if (!inboundDetailService.save(inboundDetail)) {
                return new R(Code.ERROR, "新增入库单详细信息失败,请稍后重试");
            }
            //保存记录
            //判断货品是否已经存在
            Commodity commodity = commodityService.getById(inboundDetail.getCommId());

            Record record = new Record();
            record.setCommId(inboundDetail.getCommId());
            record.setModelType(2);
            record.setOperator(userId);
            record.setNumber(inboundDetail.getCommNumber());
            record.setDate(LocalDate.now());

            if(ObjectUtils.isEmpty(commodity)){
                //不存在就新增
                Commodity c = new Commodity();
                c.setId(inboundDetail.getCommId());
                c.setCommNumber(inboundDetail.getCommNumber());
                c.setCommColor(inboundDetail.getCommColor());
                c.setCommSize(inboundDetail.getCommSize());
                c.setCommName(inboundDetail.getCommName());
                c.setCommNumber(inboundDetail.getCommNumber());
                commodityService.save(c);

                //添加记录
                record.setOperationType(4);
            }else{
                //存在就添加数量
                int num = commodity.getCommNumber()+inboundDetail.getCommNumber();
                commodity.setCommNumber(num);
                commodityService.updateCommodity(commodity);
                //记录
                record.setOperationType(1);
            }
            //保存记录
            recordService.save(record);
        }
        Map<String,String> map = new HashMap<>();
        map.put("inboundId",inboundId.toString());
        return new R(Code.SUCCESS,map,"入库单新增成功！");
    }

    /**
     * 查询数据及分页
     * @param inboundPageDto
     * @return
     */
    @Override
    public R getInboundPageInfo(InboundPageDto inboundPageDto) {
        //创建分页构造器
        Page<Inbound> page = new Page<>(inboundPageDto.getCurrentPage(), inboundPageDto.getPageSize());
        //拼接条件
        LambdaQueryWrapper<Inbound> queryWrapper = new LambdaQueryWrapper<Inbound>();
        //模糊查询单据号
        queryWrapper.like(!ObjectUtils.isEmpty(inboundPageDto.getId()),Inbound::getId,inboundPageDto.getId());
        //模糊查询所入仓库
        queryWrapper.like(!ObjectUtils.isEmpty(inboundPageDto.getStorage()),Inbound::getStorage,inboundPageDto.getStorage());
        //日期范围查询
        if(!ObjectUtils.isEmpty(inboundPageDto.getDateRange())){
            queryWrapper.ge(Inbound::getDate,inboundPageDto.getDateRange().get(0));
            queryWrapper.le(Inbound::getDate,inboundPageDto.getDateRange().get(1));
        }
        //查询
        inboundMapper.selectPage(page,queryWrapper);

        return new R(Code.SUCCESS,page);
    }

    /**
     * 修改入库单
     * @param inbound
     * @return
     */
    @Override
    public R updateInbound(Inbound inbound) {
        //1. 获得当前登录用户的id
        int userId = (int) BaseContext.getCurrentId();
        inbound.setOperatorId(userId);
        if(inboundMapper.updateById(inbound)<=0){
            return new R(Code.ERROR,"修改入库单失败,请稍后重试");
        }
        return new R(Code.SUCCESS,"修改入库单成功！");
    }

    /**
     * 删除入库单
     * @param id
     * @return
     */
    @Override
    public R deleteInbound(Long id) {
        //获取入库单明细列表
        LambdaQueryWrapper<InboundDetail> queryWrapper = new LambdaQueryWrapper<InboundDetail>();
        queryWrapper.eq(InboundDetail::getId,id);
        List<InboundDetail> inboundDetailList = inboundDetailService.list(queryWrapper);
        boolean flag = true;
        if(inboundDetailList.size()>0){
            //遍历明细删除
           for (InboundDetail inboundDetail : inboundDetailList) {
               R r = inboundDetailService.deleteInboundDetail(inboundDetail);
               if(!r.getCode().equals(Code.SUCCESS)){
                   flag = false;
                   break;
               }
           }
        }
        if(inboundMapper.deleteById(id)<=0) flag = false;
        if(flag) return new R(Code.SUCCESS, "删除入库单成功！");
        return new R(Code.ERROR, "删除入库单失败！");
    }


}
