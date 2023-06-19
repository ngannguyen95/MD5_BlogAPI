package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Blog;
import ra.model.entity.Comment;
import ra.model.entity.Users;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Iterable<Comment> findCommentsByUsers(Users user);
    Iterable<Comment> findCommentsByBlog_BlogId(Long id);
}
