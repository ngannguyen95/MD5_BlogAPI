package ra.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.dto.response.MessageResponse;
import ra.model.entity.Blog;
import ra.model.entity.Comment;
import ra.model.entity.Users;
import ra.model.service.ICommentService;
import ra.model.service.IUserService;
import ra.security.CustomUserDetailService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = (List<Comment>) commentService.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(comments, HttpStatus.CREATED);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> create(@RequestBody Comment comment) {
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            comment.setUsers(users);
            comment.setCreated(new Date());
            comment.setStatus(true);
            Comment comment1 = commentService.save(comment).get();
            return new ResponseEntity<>(comment1, HttpStatus.CREATED);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Comment comment) {
        Optional<Comment> comment1 = commentService.findById(id);
        Blog blog = comment.getBlog();
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            if (blog != null) {
                if (comment1.isPresent()) {
                    comment.setCommentId(comment1.get().getCommentId());
                    comment.setUsers(users);
                    commentService.save(comment);
                    return new ResponseEntity<>(comment, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));
        }
    }

    @GetMapping("/changeStatusComment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Comment> comment = commentService.findById(id);
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            if (comment.isPresent() && comment.get().getUsers().getUserId() == users.getUserId()) {
                comment.get().setStatus(!comment.get().isStatus());
                commentService.save(comment.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));

        }

    }

    @GetMapping("/findCommentByUser/{id}")
    public ResponseEntity<Iterable<Comment>> findCommentByUser(@PathVariable Long id) {
        Optional<Users> user = userService.findById(id);
        if (user.isPresent()) {
            List<Comment> comments = (List<Comment>) commentService.findCommentsByUsers(user.get());
            if (comments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findCommentByBlog/{id}")
    public ResponseEntity<?> findCommentByBlog(@PathVariable Long id) {
        List<Comment> comments = (List<Comment>) commentService.findCommentsByBlog_BlogId(id);
        List<Comment> commentList = new ArrayList<>();
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (Comment c : comments) {
                if (c.isStatus()) {
                    commentList.add(c);
                }
            }
            return new ResponseEntity<>(commentList, HttpStatus.OK);
        }
    }

}
