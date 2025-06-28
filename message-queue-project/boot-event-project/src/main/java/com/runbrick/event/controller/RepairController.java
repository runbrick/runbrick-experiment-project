package com.runbrick.event.controller;

import com.runbrick.event.dataobject.WorkOrderDO;
import com.runbrick.event.publisher.RepairService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repair")
@AllArgsConstructor
@Slf4j
public class RepairController {
    private RepairService repairService;

    /**
     * 提交维修工单
     */
    @PostMapping("/submit")
    public void submit(@RequestBody WorkOrderDO workOrderDO) {
        repairService.submit(workOrderDO); // 提交工单
        log.info("[repair][提交工单成功]");
    }
}
