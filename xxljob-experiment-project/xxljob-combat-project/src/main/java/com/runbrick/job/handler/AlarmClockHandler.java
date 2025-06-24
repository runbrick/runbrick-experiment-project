package com.runbrick.job.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AlarmClockHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @XxlJob("wakeUpTheAlarmClock")
    public void wakeUpTheAlarmClock() throws Exception {
        XxlJobHelper.log("要起床了哦"); // 在调度中心可以看到这个日志
    }


    @XxlJob("wakeUpTheAlarmClockParam")
    public void wakeUpTheAlarmClockParam() throws Exception {
        String param = XxlJobHelper.getJobParam();
        // 提取 param 中的 name
        Map<String, Object> paramMap = objectMapper.readValue(param, new TypeReference<Map<String, Object>>() {});
        XxlJobHelper.log("{}该起床了哦", paramMap.get("name")); // 在调度中心可以看到这个日志
    }

}
