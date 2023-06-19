package ra.model.service;

import ra.model.entity.Blog;
import ra.model.entity.Likes;
import ra.model.entity.Users;

import java.util.Optional;

public interface ILikeService extends IGenericService<Likes,Long>{
    boolean existsByBlogAndUsers(Blog blog, Users users);
    Optional<Likes> findByBlogAndUsers(Blog blog, Users users);
    Long countLikesByBlogId(Long id);
    Iterable<Likes> findLikesByUsers(Users user);

}
