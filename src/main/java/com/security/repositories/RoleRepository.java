package com.security.repositories;

import org.springframework.data.repository.CrudRepository;

import com.security.models.RoleEntity;

public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
    
}
