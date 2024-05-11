package authentication.com.service;

import authentication.com.model.AuthUser;
import authentication.com.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {


    private final AuthUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {

        Optional<AuthUser> authUser = this.repository.findByUserName(username.toLowerCase());
        if (authUser.isEmpty()) {
            return (UserDetails) new UsernameNotFoundException(username);
        } else {
            return User.builder()
                    .username(authUser.get().getUserName())
                    .password(authUser.get().getPassword())
                    .disabled(!authUser.get().isActive())
                    .build();
        }
//        or
//        return authUser.map(user -> User.builder()
//                .username(user.getUserName())
//                .password(user.getPassword())
//                .disabled(!user.isActive())
//                .build()).orElseGet(() -> (UserDetails) new UsernameNotFoundException(username));
    }




}
