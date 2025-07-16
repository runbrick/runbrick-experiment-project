package com.runbrick.boot;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class AssertTest {

    @Test
    public void allTest() {
        Assert.noNullElements(new String[]{"", null}, "结果为NULL");
    }

    @Test
    public void assertIsTrueTest() {
        // java.lang.IllegalArgumentException: 结果不为true
        Assert.isTrue(false, "结果不为true");
    }

    @Test
    public void assertState() {
        // 断言为true java.lang.IllegalArgumentException: 结果不为true
        Assert.state(false, "结果不为true");
    }


    @Test
    public void assertIsNullTest() {
        // 断言为NULL java.lang.IllegalArgumentException: 结果不为NULL
        Assert.isNull("", "结果不为NULL");
    }

    @Test
    public void assertNotNullTest() {
        // 断言不为NULL java.lang.IllegalArgumentException: 结果为NULL
        Assert.notNull(null, "结果为NULL");
    }

    @Test
    public void assertHasLengthTest() {
        // 断言给定的 String 不为空;也就是说，它不能是 null 空 String
        // java.lang.IllegalArgumentException: 结果为空
        Assert.hasLength(null, "结果为空");
    }

    @Test
    public void assertHasTextTest() {
        // 断言给定的 String 不为空;也就是说，它不能是 null 空 String，并且不能是纯空白
        // java.lang.IllegalArgumentException: 结果为空
        Assert.hasText("", "结果为空");
    }

    @Test
    public void assertDoesNotContainTest() {
        // 断言给定 String 不包含指定的子字符串
        // java.lang.IllegalArgumentException: 结果包含
        Assert.doesNotContain("1234", "4", "结果包含");
    }

    @Test
    public void assertNotEmptyTest() {
        // 断言给定数组不为空
        // java.lang.IllegalArgumentException: 结果为空
        Assert.notEmpty(new String[]{}, "结果为空");
    }

    @Test
    public void assertNoNullElementsTest() {
        // 断言数组不包含 null 任何元素。
        // java.lang.IllegalArgumentException: 数组的元素有null
        Assert.noNullElements(new String[]{"1", "2", null}, "数组的元素有null");
    }

    @Test
    public void assertIsInstanceOf() {
        // 断言给定对象是给定类的实例
        // java.lang.IllegalArgumentException: 结果不是String: java.lang.Integer
        Assert.isInstanceOf(String.class, 1, "结果不是String");
    }

    @Test
    public void assertIsAssignable() {
        // 断言一个类型（Class）是否可以被赋值给另一个类型（Class）
        // java.lang.IllegalArgumentException: 父类不是子类: class java.lang.Integer
        Assert.isAssignable(String.class, Integer.class, "父类不是子类");
    }

}
