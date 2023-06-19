package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.model.entity.Blog;
import ra.model.entity.Likes;
import ra.model.entity.Users;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    boolean existsByBlogAndUsers(Blog blog, Users users);

    Optional<Likes> findByBlogAndUsers(Blog blog, Users users);
    Iterable<Likes> findLikesByUsers(Users user);
    @Query(value = "select count(*) from likes\n" +
            "where status=true and blogId=?1",nativeQuery = true)
    Long countLikesByBlogId(Long id);


}
