package com.zj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zj.common.R;
import com.zj.dto.RecordPageDto;
import com.zj.entity.Record;

public interface RecordService extends IService<Record> {
    R getAllRecords(RecordPageDto recordPageDto);

     R getStatistics();
}
