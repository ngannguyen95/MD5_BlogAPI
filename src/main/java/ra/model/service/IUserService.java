package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Users;

import java.util.List;

public interface IUserService extends IGenericService<Users,Long> {
    Users findAllByUserName (String userName);
    boolean existsByUserName (String userName);
    boolean existsByEmail (String email);

    Page<Users> findAll(Pageable pageable);
    List<Users> sortUserByUserName(String direction);
}
