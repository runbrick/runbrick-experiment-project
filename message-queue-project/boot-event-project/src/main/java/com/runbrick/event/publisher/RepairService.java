package com.runbrick.event.publisher;

import com.runbrick.event.dataobject.WorkOrderDO;
import com.runbrick.event.event.RepairOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RepairService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void submit(WorkOrderDO workOrderDO) {
        log.info("[repair][执行用户({}) 的提报逻辑]", workOrderDO);
        applicationEventPublisher.publishEvent(new RepairOrderEvent(this, workOrderDO));
    }


}
