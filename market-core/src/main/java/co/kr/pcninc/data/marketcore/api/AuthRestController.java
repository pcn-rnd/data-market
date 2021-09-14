package co.kr.pcninc.data.marketcore.api;

import co.kr.pcninc.data.marketcore.configuration.authorization.JwtTokenUtil;
import co.kr.pcninc.data.marketcore.configuration.authorization.JwtUserDetailsService;
import co.kr.pcninc.data.marketcore.domain.auth.JwtRequest;
import co.kr.pcninc.data.marketcore.domain.auth.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = AuthRestController.URL_PREFIX)
public class AuthRestController extends RestControllerBase{
    static final String URL_PREFIX = API_URL_PREFIX + "/auth";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        authenticate(request.getUsername(), request.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return okResponse(new JwtResponse(token));
    }

    private void authenticate(String username, String pwd) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        }catch (DisabledException e) {

        }catch (BadCredentialsException e) {

        }
    }

}
