package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.model.entity.Blog;
import ra.model.entity.Users;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
    Iterable<Blog> findBlogByUsers(Users users);
    List<Blog> findBlogByUsersUserNameOrUsersUserId(String userName, Long userId);
    Iterable<Blog> findBlogByTitle(String title);

}
