package by.dzmitryi.ebank.jwt;

import by.dzmitryi.ebank.constants.JWTConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecretKey key = JwtKeysManagement.getSecretKeyEncrypted();
        String jwt = Jwts.builder().setIssuer("Ebank").setIssuedAt(new Date()).setSubject("JWT Token")
                .claim("Name", auth.getName())
                .claim("authorities",
                        auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                .setExpiration(new Date(new Date().getTime() + 180000))
                .signWith(key).compact();
        response.setHeader(JWTConstant.JWT_HEADER, jwt);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().endsWith("/login");
    }

}
