package com.fate.domain.bizs;

import com.fate.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleBiz extends Role {

    private List<AuthorityBiz> authorityBizList;
}
