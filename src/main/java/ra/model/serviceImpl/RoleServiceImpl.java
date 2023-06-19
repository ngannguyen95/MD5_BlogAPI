package ra.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.ERole;
import ra.model.entity.Roles;
import ra.model.repository.RoleRepository;
import ra.model.service.IRoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public Optional<Roles> findAllByRoleName(ERole roleName) {
        return repository.findAllByRoleName(roleName);
    }

    @Override
    public Iterable<Roles> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Roles> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Roles> save(Roles roles) {
        return Optional.of(repository.save(roles));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
