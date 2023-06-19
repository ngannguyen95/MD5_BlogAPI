package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ra.dto.response.MessageResponse;
import ra.model.entity.Blog;
import ra.model.entity.Likes;
import ra.model.entity.Users;
import ra.model.service.IBlogService;
import ra.model.service.ILikeService;
import ra.model.service.IUserService;
import ra.security.CustomUserDetail;
import ra.security.CustomUserDetailService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likes/")
public class LikeController {
    private final ILikeService likeService;
    private final IBlogService iBlogService;
    private final IUserService userService;
    private final CustomUserDetailService customUserDetailService;


    @PutMapping("/create")
    public ResponseEntity<?> addLike(@RequestBody Likes like) {
      Users users=customUserDetailService.getUserPrinciple();
        Blog blog = new Blog();
        Optional<Blog> blogOptional = iBlogService.findById(like.getBlog().getBlogId());
        if (blogOptional.isPresent()) {
            blog = blogOptional.get();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<Likes> likesOptional = likeService.findByBlogAndUsers(blog, users);
        if (likesOptional.isPresent()) {
            Likes likes = likesOptional.get();
            likes.setStatus(!likes.isStatus());
            likeService.save(likes);
        } else {
            Likes likes = Likes.builder().blog(blog).users(users).status(true).build();
            likeService.save(likes);
        }
        return ResponseEntity.ok(new MessageResponse("Like thành công!!!!"));
    }

    @GetMapping("/countLikeByBlogId/{id}")
    public ResponseEntity<?> countLikeByBlogId(@PathVariable Long id) {
        Optional<Blog> blogOptional = iBlogService.findById(id);
        if (blogOptional.isPresent()) {
            Long count = likeService.countLikesByBlogId(id);
            return new ResponseEntity<>("Lượt Like :   "+count, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   @GetMapping("/findLikeByUser/{id}")
    public ResponseEntity<Iterable<Likes>> findLikeByUser(@PathVariable Long id){
        Users users= userService.findById(id).get();
       List<Likes> likes= (List<Likes>) likeService.findLikesByUsers(users);
       if (likes.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }else {
           return new ResponseEntity<>(likes,HttpStatus.OK);
       }
   }

}
