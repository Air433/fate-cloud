package com.fate.domain.bizs;

import com.fate.entity.User;
import lombok.Data;

import java.util.List;

/**
 * Created by ouyanggang on 2018/6/28.
 */
@Data
public class UserBiz extends User {

    private List<RoleBiz> roleBizList;

}
