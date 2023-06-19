package ra.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.dto.request.LoginRequest;
import ra.dto.response.JwtResponse;
import ra.dto.response.MessageResponse;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.Blog;
import ra.model.entity.Users;
import ra.model.service.IBlogService;
import ra.model.service.IUserService;
import ra.model.serviceImpl.ImageServiceImpl;
import ra.security.CustomUserDetail;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final IUserService userService;

    private final PasswordEncoder encoder;



    @GetMapping("/getUser")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        // taọ token trả về client
        String jwt = tokenProvider.generateToken(customUserDetail);
        // lấy các quyền cuả user
        List<String> list = customUserDetail.getAuthorities().stream().map(
                ietm -> ietm.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(),
                customUserDetail.getPhone(), list));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                        @RequestBody Users user) {
        Users userUpdate = userService.findById(id).get();
        if (userUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (userUpdate.getEmail() == user.getEmail() || userUpdate.getPhone() == user.getPhone()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nhập vào Email hoặc số điện thoại mới"));
        } else {
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPhone(user.getPhone());
            userService.save(userUpdate);
            return new ResponseEntity<>(userUpdate, HttpStatus.CREATED);
        }
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable("id") Long id, @RequestBody Users user) {
        Users userU = userService.findById(id).get();
        if (userU == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (userU.getPassword() == user.getPassword()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Trùng mật khẩu cũ, nhập vào mật khẩu mới"));
        } else {
            userU.setPassword(encoder.encode(user.getPassword()));
            userService.save(userU);
            return ResponseEntity.ok(new MessageResponse("Thay đổi thành công"));
        }
    }



}
