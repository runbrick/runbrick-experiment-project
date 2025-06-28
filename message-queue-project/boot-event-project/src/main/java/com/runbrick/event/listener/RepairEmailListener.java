package com.runbrick.event.listener;

import com.runbrick.event.event.RepairOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 发送邮件
 */
@Slf4j
@Component
public class RepairEmailListener implements ApplicationListener<RepairOrderEvent> {
    @Override
    @Async
    public void onApplicationEvent(RepairOrderEvent event) {
        log.info("[onApplicationEvent][给用户({}) 发送邮件：{}]", event.getWorkOrderDO().getSubmitter(), event.getWorkOrderDO().getDescription());
    }
}
