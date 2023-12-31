package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.LoginRequest;
import ra.dto.request.SignupRequest;
import ra.dto.response.JwtResponse;
import ra.dto.response.MessageResponse;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.ERole;
import ra.model.entity.Roles;
import ra.model.entity.Users;
import ra.model.service.IRoleService;
import ra.model.service.IUserService;
import ra.security.CustomUserDetail;
import ra.security.CustomUserDetailService;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest, BindingResult bindingResult) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Tên đăng nhập đã tồn tại"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email đã tồn tại"));
        }
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Một trường nào đó không đúng định dạng"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            user.setCreated(sdf.parse(strNow));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // lấy ra các quyền của người dùng dưới dạng String
        Set<String> roles = signupRequest.getListRoles();

        Set<Roles> roleList = new HashSet<>();
        // nếu quyền của người dùng không có thì mặc định là người dùng
        if (roles == null || roles.isEmpty()) {
            // mặc định là ngừi dùng
            Roles useRole = roleService.findAllByRoleName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roleList.add(useRole);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findAllByRoleName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roleList.add(adminRole);
                    case "moderator":
                        Roles modRole = roleService.findAllByRoleName(ERole.MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roleList.add(modRole);
                    case "user":
                        Roles userRole = roleService.findAllByRoleName(ERole.USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roleList.add(userRole);
                }

            });
        }
        user.setListRoles(roleList);
        userService.save(user);
        return ResponseEntity.ok(new MessageResponse("Thêm mới thành công!!"));
    }

    @PostMapping("/signin")
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
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(){
//        Users users = customUserDetailService.getUserPrinciple();
//
//    }

}
