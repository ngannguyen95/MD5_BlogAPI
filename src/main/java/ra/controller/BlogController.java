package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.BlogDTO;
import ra.dto.response.MessageResponse;
import ra.model.entity.Blog;
import ra.model.entity.Catalog;
import ra.model.entity.Image;
import ra.model.entity.Users;
import ra.model.service.IBlogService;
import ra.model.service.ICatalogService;
import ra.model.service.IUserService;
import ra.model.serviceImpl.ImageServiceImpl;
import ra.security.CustomUserDetailService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional(rollbackFor = SQLException.class)
@RequestMapping("/api/blogs")

public class BlogController {
    private final IBlogService blogService;
    private final IUserService userService;
    private final CustomUserDetailService customUserDetailService;

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<Blog>> findAllBlog(Pageable pageable) {
        Page<Blog> blogs = blogService.findAll(pageable);
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
    }

    @GetMapping("/findBlogByUsers")
//    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<?> findBlogByUsers() {
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            List<Blog> blogs = (List<Blog>) blogService.findBlogByUsers(users);
            if (blogs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(blogs, HttpStatus.OK);
            }
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));

        }

    }

    @GetMapping("/findByUserNameOrUserId")
    public ResponseEntity<List<Blog>> getBlogByUser(@RequestParam String userName, @RequestParam Long userId) {
        List<Blog> blogs = blogService.findBlogByUsersUserNameOrUsersUserId(userName, userId);
        if (blogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            blog.setUsers(users);
            blog.setCreated(new Date());
            blog.setBlogStatus(true);
            Blog blog1 = blogService.save(blog).get();
            return new ResponseEntity<>(blog1, HttpStatus.ACCEPTED);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        Blog blog1 = blogService.findById(id).get();
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            blog1.setUsers(users);
            blog1.setTitle(blog.getTitle());
            blog1.setContent(blog.getContent());
            blog1.setImages(blog.getImages());
            blog1.setCatalog(blog.getCatalog());
            blogService.save(blog1);
            return new ResponseEntity<>(blog1, HttpStatus.OK);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));
        }

    }

    @PutMapping("/changeStatusBlog/{id}")
    @PreAuthorize("hasAnyAuthority('USER','MODERATOR')")
    public ResponseEntity<?> changeStatusBlog(@PathVariable Long id){
        Users users = customUserDetailService.getUserPrinciple();
        if (users.isUserStatus()){
            Optional<Blog> blog= blogService.findById(id);
            if (blog.isPresent()&&(users.getUserId() == blog.get().getUsers().getUserId())) {
                blog.get().setBlogStatus(!blog.get().isBlogStatus());
                blogService.save(blog.get());
                return ResponseEntity.ok(new MessageResponse("Thay đổi thành công"));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập"));
        }

    }
    @GetMapping("/findBlogByTitle")
    public ResponseEntity<?> findBlogByTitle(@RequestParam String title){
        List<Blog> blogs= (List<Blog>) blogService.findBlogByTitle(title);
        if (blogs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(blogs,HttpStatus.OK);
        }
    }
}