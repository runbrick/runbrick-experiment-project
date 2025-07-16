package com.runbrick.boot;

import org.junit.jupiter.api.Test;
import org.springframework.lang.Contract;

public class ContractTest {

    /**
     * runbrick
     */
    @Test
    public void assertIsEmptyTest() {
        getMsg(null);
    }

    @Contract("null -> fail; _ -> !null")
    private String getMsg(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("参数不能为空");
        }

        return s;
    }
}
