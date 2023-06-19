package ra.model.service;


import ra.model.entity.Blog;
import ra.model.entity.Comment;
import ra.model.entity.Users;


public interface ICommentService extends IGenericService<Comment,Long>{
    Iterable<Comment> findCommentsByUsers(Users user);
    Iterable<Comment> findCommentsByBlog_BlogId(Long id);
}
