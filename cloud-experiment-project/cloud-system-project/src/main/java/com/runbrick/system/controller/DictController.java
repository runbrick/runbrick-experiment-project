package com.runbrick.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dict")
public class DictController {
    /**
     * 获取字典数据
     * @return 字典数据
     */
    @GetMapping("info")
    public String info() {
        return "这是字典服务";
    }
}
