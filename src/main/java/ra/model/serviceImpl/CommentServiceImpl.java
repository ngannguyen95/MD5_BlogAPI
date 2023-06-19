package ra.model.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Blog;
import ra.model.entity.Comment;
import ra.model.entity.Users;
import ra.model.repository.CommentRepository;
import ra.model.service.ICommentService;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Optional<Comment> save(Comment comment) {
        return Optional.of(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Iterable<Comment> findCommentsByUsers(Users user) {
        return commentRepository.findCommentsByUsers(user);
    }

    @Override
    public Iterable<Comment> findCommentsByBlog_BlogId(Long id) {
        return commentRepository.findCommentsByBlog_BlogId(id);
    }


}
