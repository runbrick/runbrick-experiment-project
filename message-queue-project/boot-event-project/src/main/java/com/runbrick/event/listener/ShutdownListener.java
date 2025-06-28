package com.runbrick.event.listener;

import com.runbrick.event.event.RepairOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 设备停机处理监听器
 */
@Slf4j
@Component
public class ShutdownListener {

    /**
     * 通过条件判断是否执行停机处理
     * @param event
     */
    @EventListener(condition = "#event.workOrderDO.isStop == true ")
    @Async
    public void equipmentShutdown(RepairOrderEvent event) {
        log.info("[repair][设备停机处理]");
    }
}
