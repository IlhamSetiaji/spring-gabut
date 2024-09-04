package com.gabut.mantap.mantap.seeders;

import org.springframework.stereotype.Component;

import com.gabut.mantap.mantap.entities.Role;
import com.gabut.mantap.mantap.entities.enums.RoleEnum;
import com.gabut.mantap.mantap.repositories.RoleRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    this.loadRoles();
  }

  private void loadRoles() {
    RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN };
    Map<RoleEnum, String> roleDescriptionMap = Map.of(
        RoleEnum.USER, "Default user role",
        RoleEnum.ADMIN, "Administrator role",
        RoleEnum.SUPER_ADMIN, "Super Administrator role");

    Arrays.stream(roleNames).forEach((roleName) -> {
      Optional<Role> optionalRole = roleRepository.findByName(roleName);

      optionalRole.ifPresentOrElse(System.out::println, () -> {
        Role roleToCreate = new Role();

        roleToCreate.setName(roleName)
            .setDescription(roleDescriptionMap.get(roleName));

        roleRepository.save(roleToCreate);
      });
    });
  }
}
