package authentication.com.controller;

import authentication.com.model.AuthUser;
import authentication.com.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {


    private final AuthUserRepository repository;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthUser user){
        try {
            if (this.repository.findByUserName(user.getUserName()).isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("UserName Already Exist");
            }else {
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
                AuthUser save = this.repository.save(user);
                return ResponseEntity.ok(HttpStatus.CREATED);
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }




}
