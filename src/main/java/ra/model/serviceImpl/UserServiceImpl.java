package ra.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Users;
import ra.model.repository.UserRepository;
import ra.model.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findAllByUserName(String userName) {
        return userRepository.findAllByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        pageable= PageRequest.of(pageable.getPageNumber(), 5);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<Users> sortUserByUserName(String direction) {
        if (direction.equals("asc")){
            return userRepository.findAll(Sort.by("userName").ascending());
        }else {
            return userRepository.findAll(Sort.by("userName").descending());
        }
    }


    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<Users> save(Users users) {
        return Optional.of(userRepository.save(users));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
