package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Blog;
import ra.model.entity.Users;

import java.util.List;

public interface IBlogService extends IGenericService<Blog,Long>{
    Iterable<Blog> findBlogByUsers(Users users);
    Page<Blog> findAll(Pageable pageable);
    List<Blog> findBlogByUsersUserNameOrUsersUserId(String userName, Long userId);
    Iterable<Blog> findBlogByTitle(String title);

}
