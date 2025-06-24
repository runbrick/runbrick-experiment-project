package com.runbrick.job.controller;

import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.client.AdminBizClient;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.JobInfoParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Value("${xxl.job.admin.addresses:http://127.0.0.1:8080/xxl-job-admin}")
    private        String addressUrl;
    @Value("${xxl.job.accessToken}")
    private        String accessToken;
    // admin-client
    private static int    timeoutSecond = 3;

    /**
     * 创建一个特定的job任务
     */
    @RequestMapping("/createJob")
    public ReturnT<String> createJob() {
        JobInfoParam jobInfoParam = new JobInfoParam();
        jobInfoParam.setJobGroup(4);
        jobInfoParam.setJobDesc("测试任务" + new Date());
        jobInfoParam.setAuthor("xuxueli");
        jobInfoParam.setScheduleType("CRON");
        jobInfoParam.setScheduleConf("0/10 * * * * ?");
        jobInfoParam.setMisfireStrategy("DO_NOTHING");
        jobInfoParam.setExecutorRouteStrategy("FIRST");
        jobInfoParam.setExecutorHandler("demoJobHandler");
        jobInfoParam.setExecutorParam("");
        jobInfoParam.setExecutorBlockStrategy("SERIAL_EXECUTION");
        jobInfoParam.setExecutorTimeout(0);
        jobInfoParam.setExecutorFailRetryCount(0);
        jobInfoParam.setChildJobId("");
        jobInfoParam.setTriggerNextTime(0);
        jobInfoParam.setUpdateTime(new Date());
        jobInfoParam.setAddTime(new Date());
        jobInfoParam.setGlueRemark("GLUE代码初始化");
        jobInfoParam.setGlueUpdatetime(new Date());
        jobInfoParam.setGlueSource("");
        jobInfoParam.setGlueType("BEAN");
        jobInfoParam.setTriggerStatus(1); // 启动模式
        jobInfoParam.setTriggerLastTime(0);
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken, timeoutSecond);
        return adminBiz.createJobInfo(jobInfoParam);
    }
}
