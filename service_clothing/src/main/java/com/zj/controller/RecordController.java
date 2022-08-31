package com.zj.controller;

import com.zj.common.R;
import com.zj.dto.RecordPageDto;
import com.zj.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 分页获取记录
     * @return
     */
    @PostMapping
    public R getAllRecords(@RequestBody RecordPageDto recordPageDto){
        return recordService.getAllRecords(recordPageDto);
    }

    /**
     * 统计信息
     * @return
     */
    @GetMapping("/statistics")
    public R getStatistics(){
        return recordService.getStatistics();
    }
}
