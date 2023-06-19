package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.response.MessageResponse;
import ra.model.entity.Comment;
import ra.model.entity.Reply;
import ra.model.entity.Users;
import ra.model.service.ICommentService;
import ra.model.service.IReplyService;
import ra.security.CustomUserDetailService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/replys")
public class ReplyController {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private IReplyService replyService;
    @Autowired
    private ICommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createReplys(@RequestBody Reply reply) {
        Users user = customUserDetailService.getUserPrinciple();
        if (user.isUserStatus()) {
            Optional<Comment> comment = commentService.findById(reply.getComments().getCommentId());
            if (comment.isPresent()) {
                reply.setUsers(user);
                reply.setStatus(true);
                reply.setReplyDate(LocalDate.now());
                replyService.save(reply);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập!!"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReply(@PathVariable Long id, @RequestBody Reply reply) {
        Optional<Reply> reply1 = replyService.findById(id);
        Users user = customUserDetailService.getUserPrinciple();
        if (user.isUserStatus()) {
            if (reply1.isPresent()) {
                reply.setReplyId(id);
                reply.setUsers(user);
               replyService.save(reply);
               return new ResponseEntity<>(reply,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập!!"));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReply(@PathVariable Long id){
        Optional<Reply> reply = replyService.findById(id);
        Users user = customUserDetailService.getUserPrinciple();
        if (user.isUserStatus()){
            replyService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Không có quyền truy cập!!"));
        }
    }


}
