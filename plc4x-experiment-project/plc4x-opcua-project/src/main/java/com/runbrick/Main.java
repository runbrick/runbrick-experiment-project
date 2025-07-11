package com.runbrick;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.PlcDriverManager;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        String connectionString = "opcua:tcp://DESKTOP-SF0ESP4:53530/OPCUA/SimulationServer";
        try (PlcConnection plcConnection = PlcDriverManager.getDefault().getConnectionManager().getConnection(connectionString)) {
//             这里实现各种逻辑
//            PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
//            builder.addTagAddress("value-1", "ns=3;i=1003;LREAL");
//            PlcReadRequest readRequest = builder.build();
//
//            CompletableFuture<? extends PlcReadResponse> response = readRequest.execute();
//            PlcReadResponse plcReadResponse = response.get();
//            Collection<String> tagNames = plcReadResponse.getTagNames();
//            tagNames.forEach(tagName -> {
//                System.out.println(plcReadResponse.getObject(tagName));
//            });

            if (!plcConnection.getMetadata().isWriteSupported()) {
                System.out.println("不支持写操作");
            } else {
                PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
                builder.addTagAddress("value-1", "ns=3;i=1003;LREAL", Double.valueOf("12.45"));
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