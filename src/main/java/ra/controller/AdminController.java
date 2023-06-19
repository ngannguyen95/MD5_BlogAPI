package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.dto.response.MessageResponse;
import ra.model.entity.Blog;
import ra.model.entity.Catalog;
import ra.model.entity.Users;
import ra.model.service.IBlogService;
import ra.model.service.ICatalogService;
import ra.model.service.IUserService;
import ra.security.CustomUserDetailService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private  CustomUserDetailService customUserDetailService;


    // User Manager
    @GetMapping("/findAllUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<Users>> findAllUser(Pageable pageable){
        Page<Users> listUser=userService.findAll(pageable);
//        List<Users> listUser= (List<Users>) userService.findAll();
        if (listUser.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(listUser,HttpStatus.OK);
        }
    }

    @PutMapping("/changeStatusUer/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeStatusUser(@PathVariable("id") Long id) {
        Optional<Users> user= userService.findById(id);
        if (user.isPresent()) {
            user.get().setUserStatus(!user.get().isUserStatus());
            userService.save(user.get());
            return ResponseEntity.ok(new MessageResponse("Thay đổi thành công"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/changeStatusBlog/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    public ResponseEntity<?> changeStatusBlog(@PathVariable Long id){
        Optional<Blog> blog= blogService.findById(id);
        if (blog.isPresent()) {
            blog.get().setBlogStatus(!blog.get().isBlogStatus());
            blogService.save(blog.get());
            return ResponseEntity.ok(new MessageResponse("Thay đổi thành công"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/sortUserByUserName")
    public ResponseEntity<List<Users>> sortUserByUserName(@RequestParam String direction) {
        List<Users> listUser = userService.sortUserByUserName(direction);
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }

}
