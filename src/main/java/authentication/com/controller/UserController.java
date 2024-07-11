package authentication.com.controller;

import authentication.com.model.AuthUser;
import authentication.com.model.UserRecord;
import authentication.com.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {


    private final AuthUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final BytesKeyGenerator saltGenerator = KeyGenerators.secureRandom();


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthUser user){
        try {
            if (this.repository.findByUserName(user.getUserName()).isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exist with the mail");
            }else {
                user.setId(UUID.randomUUID().toString());
                user.setPassword(this.passwordEncoder.encode(user.getPassword()));

                //check the mail

                // verify the otp

                // save the data

                this.repository.save(user);
                return ResponseEntity.ok(HttpStatus.CREATED);
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping("/verifyUser")
    public ResponseEntity<?> verifyUser(@PathVariable("code") String code){

        return null;
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") String userId){
        Optional<AuthUser> user = this.repository.findById(userId);
        try{
            if (user.isPresent()) {
                return ResponseEntity.ok(user);
            }
            else
                return ResponseEntity.badRequest().body("UserId Not present");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping("/userLogin")
    public ResponseEntity<?> userLogin(@RequestBody @NotNull UserRecord userRecord){
        Optional<AuthUser> user = this.repository.findByUserName(userRecord.userName());
        try{
            if (user.isPresent()) {

                if (passwordEncoder.matches(userRecord.password(), user.get().getPassword())){
                    return ResponseEntity.ok("Login Success");
                }
                return ResponseEntity.badRequest().body("Invalid Credentials");
            }else
                return ResponseEntity.badRequest().body("Email Not Found");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
