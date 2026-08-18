package com.ram.Authentication.Authorization.filters;

import com.ram.Authentication.Authorization.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component   // we want this filter before request come, so we let spring bean of it and inject
@Order(1)
public class JwtFilter extends OncePerRequestFilter { // to let know spring that, this is filter, use filter interface
    @Override // this filter get called every time request came
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty()){ // invalid token
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("invalid token, signin again");
            return;
        }

        if(token.startsWith("Bearer ")){
            token = token.substring(7);
        }

        try {
            Claims claims = JwtUtils.validateToken(token);
            String role = claims.get("roles", String.class);
            List<GrantedAuthority> authorities = role == null
                    ? List.of()
                    : List.of(new SimpleGrantedAuthority("ROLE_" + role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken); // persist Authentication Object {claim's subject and role} into
            // SecurityContextHolder...a global Context that has Authentication Object so any method can access it for Authorization purpose
        } catch (JwtException | IllegalArgumentException exception){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("jwt token not authorised");
            return;
        }

        filterChain.doFilter(request, response); // headed over to next filter
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/register")
                || request.getRequestURI().equals("/verifyRegistrationToken")
                || request.getRequestURI().equals("/signin");
    }
}
