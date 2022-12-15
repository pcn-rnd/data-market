package co.kr.pcninc.data.marketcore.api;

import co.kr.pcninc.data.marketcore.common.message.HttpResponse;
import co.kr.pcninc.data.marketcore.configuration.authorization.JwtTokenUtil;
import co.kr.pcninc.data.marketcore.configuration.authorization.JwtUserDetailsService;
import co.kr.pcninc.data.marketcore.domain.User;
import co.kr.pcninc.data.marketcore.domain.auth.JwtRequest;
import co.kr.pcninc.data.marketcore.domain.auth.JwtResponse;
import co.kr.pcninc.data.marketcore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = AuthRestController.URL_PREFIX)
public class AuthRestController extends RestControllerBase{
    static final String URL_PREFIX = API_URL_PREFIX + "/auth";
    static final String USER_ID = "/{userId}";

    static final String PWD = "/{pwd}";
    static final String EMAIL = "/email";
    static final String NAMES = "/names";
    static final String KEYS = "/keys";


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody JwtRequest request) {
        Authentication authentication = authenticate(request.getUsername(), request.getPassword());
        final String token = jwtTokenUtil.generateToken(authentication);

        return okResponse(new JwtResponse(token, request.getUsername()));
    }

    private Authentication authenticate(String username, String pwd) {
        try{
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        }catch (DisabledException e) {
            throw new DisabledException("USER_DISASBLED", e);
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return okResponse("로그아웃 완료");
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser() {
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(authentication.equals("anonymousUser")) {
            return okResponse(null);
        }

        log.info("current user : {}", ((UserDetails) authentication).getUsername());
        return okResponse(((UserDetails) authentication).getUsername());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        userService.saveUser(user);

        return okResponse(user.getUserId());
    }



    //공인인증용
    @RequestMapping(
            value = USER_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getUserInfo(
        @PathVariable String userId
    ) {
        Optional<User> user = userService.getUser(userId);

        //return okResponse(purchaseList);
        //공인인증용
        return okResponse(user);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAll() {
        List<User> user = userService.getAll();

        return okResponse(user);
    }

    @RequestMapping(
            value = USER_ID,
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> modifyUser(
            @PathVariable String userId
    ) {

        return okResponse(userId + " 사용자 정보 수정");
    }

    @RequestMapping(
            value = USER_ID,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> deleteUser(
            @PathVariable String userId
    ) {

        userService.deleteUser(userId);

        return okResponse(userId + " 사용자 삭제 완료");
    }


    @RequestMapping(
            value = EMAIL,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllEmails() {
        List<String> emails = userService.getAllEmail();

        return okResponse(emails);
    }

    @RequestMapping(
            value = NAMES,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllNames() {
        List<String> names = userService.getAllNames();

        return okResponse(names);
    }

    @RequestMapping(
            value = KEYS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<?>> getAllKeys() {
        List<String> keys = userService.getAllKeys();

        return okResponse(keys);
    }

    @RequestMapping(
            value = "/check" + PWD,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<HttpResponse<String>> checkKey(@PathVariable String pwd) {

        return okResponse("success");
    }
}
