package com.runbrick;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.PlcDriverManager;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.junit.jupiter.api.Test;

public class ClientTest {

    @Test
    public void test() {
        String connectionString = "modbus-tcp:tcp://38.147.172.24:39012";
        try (PlcConnection plcConnection = PlcDriverManager.getDefault().getConnectionManager().getConnection(connectionString)) {
            PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
            builder.addTagAddress("first-holding-register", "holding-register:1:REAL"); // 读取地址为1的保持寄存器
            builder.addTagAddress("first-holding-register1", "holding-register:3:REAL"); // 读取地址为1的保持寄存器
            builder.addTagAddress("first-holding-register2", "holding-register:5:REAL"); // 读取地址为1的保持寄存器

            PlcReadRequest readRequest = builder.build();

            // 执行读取请求
            PlcReadResponse response = readRequest.execute().get(); // .get() 会阻塞直到收到响应
            // 遍历并打印结果
            for (String tagName : response.getTagNames()) {
                if (response.getResponseCode(tagName) == PlcResponseCode.OK) {
                    Object value = response.getObject(tagName);
                    System.out.printf("标签 '%s': %s%n", tagName, value.toString());
                } else {
                    System.err.printf("读取标签 '%s' 出错: %s%n", tagName, response.getResponseCode(tagName).name());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
