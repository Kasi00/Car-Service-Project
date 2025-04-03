package org.example.springbootdemo.cars.user;

import lombok.RequiredArgsConstructor;
import org.example.springbootdemo.cars.error.NotFoundException;
import org.example.springbootdemo.cars.user.persistance.Role;
import org.example.springbootdemo.cars.user.persistance.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Role with id " + id + " not found")
        );
    }
}
