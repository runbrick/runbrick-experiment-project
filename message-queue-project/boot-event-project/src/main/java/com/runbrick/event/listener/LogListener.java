package com.runbrick.event.listener;

import com.runbrick.event.event.RepairOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogListener implements ApplicationListener<RepairOrderEvent> {
    @Override
    @Async
    @Order(10)
    public void onApplicationEvent(RepairOrderEvent event) {
        log.info("[log][记录用户({}) 的提报逻辑]", event.getWorkOrderDO());
    }
}
