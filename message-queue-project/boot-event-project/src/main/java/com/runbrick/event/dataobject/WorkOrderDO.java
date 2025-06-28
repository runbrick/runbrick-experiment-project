package com.runbrick.event.dataobject;

import lombok.Data;

@Data
public class WorkOrderDO {

    private String code;

    private String submitter;

    private String description;

    /**
     * 是否为停机维修
     */
    private Boolean isStop;
}
