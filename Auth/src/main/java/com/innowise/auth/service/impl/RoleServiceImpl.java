package com.innowise.auth.service.impl;

import com.innowise.auth.entity.Role;
import com.innowise.auth.entity.RoleName;
import com.innowise.auth.repository.RoleRepository;
import com.innowise.auth.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleName roleName) {

        return roleRepository.findByName(roleName)
            .orElseThrow(EntityNotFoundException::new);
    }
}
