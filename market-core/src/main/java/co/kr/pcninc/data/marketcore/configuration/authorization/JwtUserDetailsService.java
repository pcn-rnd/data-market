package co.kr.pcninc.data.marketcore.configuration.authorization;

import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * DB에서 얻어온 UserDetail을 AuthenticationManager에게 제공
 *
 */
@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.getUser(username);

        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        log.info(user.getUserId() + "details load completed.");
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPwd(), new ArrayList<>());
    }
}
