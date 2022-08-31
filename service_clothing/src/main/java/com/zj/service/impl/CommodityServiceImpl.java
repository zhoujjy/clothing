package com.zj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.common.R;
import com.zj.dto.CommodityPageDto;
import com.zj.entity.Commodity;
import com.zj.entity.Record;
import com.zj.mapper.CommodityMapper;
import com.zj.service.CommodityService;
import com.zj.service.RecordService;
import com.zj.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Transactional //事务注解
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RedisUtils redisUtils;



    /**
     * 获得所有货品信息
     * @param commodityPageDto
     * @return
     */
    @Override
    public R getAllCommodity(CommodityPageDto commodityPageDto) {
        //创建分页构造器
        Page<Commodity> page = new Page<>(commodityPageDto.getCurrentPage(), commodityPageDto.getPageSize());
        //条件拼接
        LambdaQueryWrapper<Commodity> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(commodityPageDto.getId() != null, Commodity::getId, commodityPageDto.getId());
        queryWrapper.like(commodityPageDto.getCommColor() != null, Commodity::getCommColor, commodityPageDto.getCommColor());
        // queryWrapper.like(commodityPageDto.getCommSize() != null, Commodity::getCommSize, commodityPageDto.getCommSize());
        //相等查询
        queryWrapper.eq(!ObjectUtils.isEmpty(commodityPageDto.getCommSize()), Commodity::getCommSize, commodityPageDto.getCommSize());
        //查询
        commodityMapper.selectPage(page, queryWrapper);
        return new R(Code.SUCCESS,page);
    }

    /**
     * 添加货品
     * @param commodity
     * @return
     */
    @Override
    public R addCommodity(Commodity commodity) {
        //1. 查询数据库中的货品
        Commodity oldCommodity =  commodityMapper.selectById(commodity.getId());
        //2. 判断是否为空
        if(!ObjectUtils.isEmpty(oldCommodity)){
            return new R(Code.ERROR,"货品已经存在");
        }
        //3. 添加
         if(commodityMapper.insert(commodity)>0){
             //获取当前登录用户
             int userId = (int) BaseContext.getCurrentId();
             //添加记录
             Record record = new Record();
             record.setCommId(commodity.getId());
             record.setModelType(1);
             record.setOperator(userId);
             record.setOperationType(4);
             record.setNumber(commodity.getCommNumber());
             record.setDate(LocalDate.now());
             recordService.save(record);
             return new R(Code.SUCCESS,"添加成功");
         }
        return new R(Code.ERROR,"添加失败，请稍后重试");
    }

    /**
     * 删除货品
     * @param id
     * @return
     */
    @Override
    public R deleteCommodity(Integer id) {
        if(commodityMapper.deleteById(id)>0){
            //获取当前登录用户
            int userId = (int) BaseContext.getCurrentId();
            //添加记录
            Record record = new Record();
            record.setCommId(id);
            record.setModelType(1);
            record.setOperator(userId);
            record.setOperationType(3);
            record.setDate(LocalDate.now());
            recordService.save(record);
            return new R(Code.SUCCESS,"删除成功");
        }
        return new R(Code.ERROR,"删除失败，请稍后重试");
    }

    /**
     * 修改货品
     * @param commodity
     * @return
     */
    @Override
    public R updateCommodity(Commodity commodity) {
        //1. 查询数据库中的货品
        Commodity oldCommodity =  commodityMapper.selectById(commodity.getId());
        int oldNumber = oldCommodity.getCommNumber();
        int newNumber = commodity.getCommNumber();
        //2. 判断数量是否修改
        if(oldNumber != newNumber){
            //获取当前登录用户
            int userId = (int) BaseContext.getCurrentId();
            //修改了数量，添加记录
            Record record = new Record();
            record.setCommId(commodity.getId());
            record.setModelType(1);
            record.setOperator(userId);
            if (newNumber > oldNumber) {
                record.setOperationType(1);
                record.setNumber(newNumber - oldNumber);
            } else {
                record.setOperationType(2);
                record.setNumber(oldNumber - newNumber);
            }
            record.setDate(LocalDate.now());
            recordService.save(record);
        }
        //3. 修改
        if(commodityMapper.updateById(commodity)>0){
            return new R(Code.SUCCESS,"修改成功");
        }
        return new R(Code.ERROR,"修改失败，请稍后重试");
    }

    /**
     * 根据id查询货品
     * @param id
     * @return
     */
    @Override
    public R getCommodityInfoById(Integer id) {
        return new R(Code.SUCCESS,commodityMapper.selectById(id));
    }

    /**
     * 库存余量
     * @return
     */
    public Integer getStockNum(){
        return commodityMapper.getStockNum();
    }

    /**
     * 货品和数量
     */
    public ArrayList<HashMap<String,Object>> getStockNumByType(){
        return commodityMapper.getStockNumByType();
    }

}
