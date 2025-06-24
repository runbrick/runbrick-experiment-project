package com.runbrick.service;

import com.runbrick.entity.UserDO;

public interface IUserService {

    UserDO getUserById(Long id);

    UserDO getUserByName(String name);

}
