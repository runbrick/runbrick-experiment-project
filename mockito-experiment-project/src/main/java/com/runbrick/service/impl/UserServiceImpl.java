package com.runbrick.service.impl;

import com.runbrick.entity.UserDO;
import com.runbrick.service.IUserService;

public class UserServiceImpl implements IUserService {
    @Override
    public UserDO getUserById(Long id) {
        return new UserDO(1L, "RunBrick", "runbrick@163.com");
    }

    @Override
    public UserDO getUserByName(String name) {
        return null;
    }
}
