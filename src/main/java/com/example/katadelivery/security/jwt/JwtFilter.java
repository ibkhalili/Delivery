package com.example.katadelivery.security.jwt;

import com.example.katadelivery.exceptions.UnauthorizedAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        return (path.startsWith("/api/auth/") && HttpMethod.POST.matches(method))
                || (path.startsWith("/swagger-ui") && HttpMethod.GET.matches(method))
                || (path.startsWith("/swagger-ui") && HttpMethod.POST.matches(method))
                || (path.startsWith("/v3/api-docs") && (HttpMethod.POST.matches(method) || HttpMethod.GET.matches(method)));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());


            if ((authorizationHeader.isEmpty() || authorizationHeader.isBlank()) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");


            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            throw new UnauthorizedAuthException("Token cannot be trusted");
        }
    }
}