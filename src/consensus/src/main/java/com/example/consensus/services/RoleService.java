package com.example.consensus.services;

import com.example.consensus.RoleRequest;
import com.example.consensus.entities.Role;
import com.example.consensus.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Collection;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Collection<Role> getRoleByUserId(Long id) {
        return roleRepository.findAllRolesByUserId(id);
    }

    @Transactional
    public void addRoleToUser(RoleRequest roleRequest) {
        roleRepository.addRoleToUser(roleRequest.getUser_id(), roleRequest.getRole_id());
    }

    @Transactional
    public void deleteRoleFromUser(RoleRequest roleRequest) {
        roleRepository.deleteRoleByUserId(roleRequest.getUser_id(), roleRequest.getRole_id());
    }
}
