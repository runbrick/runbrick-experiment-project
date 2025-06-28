package com.runbrick.event.event;

import com.runbrick.event.dataobject.WorkOrderDO;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class RepairOrderEvent extends ApplicationEvent {

    private WorkOrderDO workOrderDO;

    public RepairOrderEvent(Object source) {
        super(source);
    }

    public RepairOrderEvent(Object source, WorkOrderDO workOrderDO) {
        super(source);
        this.workOrderDO = workOrderDO;
    }

}
