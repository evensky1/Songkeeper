package com.innowise.auth.service;

import com.innowise.auth.entity.Role;
import com.innowise.auth.entity.RoleName;

public interface RoleService {

    Role findByName(RoleName roleName);
}
