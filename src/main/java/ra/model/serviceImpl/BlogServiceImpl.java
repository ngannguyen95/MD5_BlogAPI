package ra.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Blog;
import ra.model.entity.Users;
import ra.model.repository.BlogRepository;
import ra.model.service.IBlogService;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements IBlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Iterable<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public Optional<Blog> save(Blog blog) {
        return Optional.of(blogRepository.save(blog));
    }

    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Iterable<Blog> findBlogByUsers(Users users) {
        return blogRepository.findBlogByUsers(users);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 5);
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> findBlogByUsersUserNameOrUsersUserId(String userName, Long userId) {
        return blogRepository.findBlogByUsersUserNameOrUsersUserId(userName, userId);
    }

    @Override
    public Iterable<Blog> findBlogByTitle(String title) {
        return blogRepository.findBlogByTitle(title);
    }


}
