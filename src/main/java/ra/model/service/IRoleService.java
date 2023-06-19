package ra.model.service;

import ra.model.entity.ERole;
import ra.model.entity.Roles;

import java.util.Optional;

public interface IRoleService extends IGenericService<Roles,Long> {
    Optional<Roles> findAllByRoleName(ERole roleName);

}
