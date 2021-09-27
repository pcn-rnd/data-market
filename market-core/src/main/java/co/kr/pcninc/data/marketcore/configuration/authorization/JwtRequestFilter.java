package co.kr.pcninc.data.marketcore.configuration.authorization;

import co.kr.pcninc.data.marketcore.common.Constants;
import co.kr.pcninc.data.marketcore.util.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * request 마다 거치는 filter, 토큰 정보 유효 확인
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {// 모든 서블릿에 일관된 요청을 처리하기 위한 Filter

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String requestTokenHeader = request.getHeader(Constants.API_ACCESS_TOKEN_ATTRIBUTE_NAME);
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith(Constants.API_ACCESS_TOKEN_PREFIX)) {
            jwtToken = requestTokenHeader.substring(6);
            try{
                username = jwtTokenUtil.getUserNameFromToken(jwtToken);
            }catch (IllegalArgumentException e) {
                log.error("{}", "Unable to get JWT Token");
            }catch (ExpiredJwtException e) {
                log.error("{}", "JWT Token has expired");
            }catch (Exception ex) {
                throw new RuntimeException("11 Username from token error");
            }
        }else{
            logger.warn("JWT Token does not begin with Bearer String");
        }

        //토큰 유효성 검사
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            // 토큰이 유효하면 spring security setting
            if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 컨텍스트에서 인증을 설정한 후 현재 사용자가 인증되었음을 지정.
                // Spring Security Configuration 전달
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.API_ACCESS_TOKEN_ATTRIBUTE_NAME);

        if(StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(Constants.API_ACCESS_TOKEN_PREFIX)) {
            return bearerToken.substring(Constants.API_ACCESS_TOKEN_PREFIX.length());
        }
        return null;
    }
}
