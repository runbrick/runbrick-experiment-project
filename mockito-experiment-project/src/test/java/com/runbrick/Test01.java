package com.runbrick;

import com.runbrick.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test01 {

    @Mock
    private IUserService userService;

    @Test
    public void test01() {
        // 1. Mockito.mock(xxxxx)
        // 2. when
//        when().thenReturn(); // 返回值
//        when().thenThrow(); // 抛异常
//        when().thenAnswer() // 回调


    }


}
