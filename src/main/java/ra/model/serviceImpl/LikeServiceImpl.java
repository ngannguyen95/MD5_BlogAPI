package ra.model.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ra.model.entity.Blog;
import ra.model.entity.Likes;
import ra.model.entity.Users;
import ra.model.repository.LikeRepository;
import ra.model.service.ILikeService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements ILikeService {
    private final LikeRepository likeRepository;


    @Override
    public Iterable<Likes> findAll() {
        return null;
    }

    @Override
    public Optional<Likes> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Likes> save(Likes likes) {
        return Optional.of(likeRepository.save(likes));
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsByBlogAndUsers(Blog blog, Users users) {
        return likeRepository.existsByBlogAndUsers(blog,users);
    }

    @Override
    public Optional<Likes> findByBlogAndUsers(Blog blog, Users users) {
        return likeRepository.findByBlogAndUsers(blog,users);
    }

    @Override
    public Long countLikesByBlogId(Long id) {
        return likeRepository.countLikesByBlogId(id);
    }

    @Override
    public Iterable<Likes> findLikesByUsers(Users user) {
        return likeRepository.findLikesByUsers(user);
    }

}
