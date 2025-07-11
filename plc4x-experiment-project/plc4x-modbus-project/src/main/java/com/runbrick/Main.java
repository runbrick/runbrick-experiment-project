package com.runbrick;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.PlcDriverManager;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        String connectionString = "modbus-tcp:tcp://127.0.0.1:502";

        try (PlcConnection plcConnection = PlcDriverManager.getDefault().getConnectionManager().getConnection(connectionString)) {
            // 这里实现各种逻辑
//            PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
//            builder.addTagAddress("value-1", "holding-register:1:REAL");
//            builder.addTagAddress("value-2", "holding-register:3:REAL");
//            builder.addTagAddress("value-3", "holding-register:5:INT");
//            builder.addTagAddress("value-4", "holding-register:6:INT");
//            PlcReadRequest readRequest = builder.build();
//
//            CompletableFuture<? extends PlcReadResponse> response = readRequest.execute();
//            PlcReadResponse plcReadResponse = response.get();
//            Collection<String> tagNames = plcReadResponse.getTagNames();
//            tagNames.forEach(tagName -> {
//                if ("value-3".equals(tagName)) {
////                    这个是二级制的所以要转为二进制
//                    System.out.println(Integer.toBinaryString(plcReadResponse.getInteger(tagName)));
//                } else {
//                    System.out.println(plcReadResponse.getObject(tagName));
//                }
//            });

            if (!plcConnection.getMetadata().isWriteSupported()) {
                System.out.println("不支持写操作");
            } else {
                PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
                builder.addTagAddress("value-1", "holding-register:1:REAL", 12.45f);
                PlcWriteRequest writeRequest = builder.build();

                CompletableFuture<? extends PlcWriteResponse> asyncResponse = writeRequest.execute();
                PlcWriteResponse plcWriteResponse = asyncResponse.get();
                System.out.println(plcWriteResponse.getTagNames());
            }


        } catch (PlcConnectionException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}