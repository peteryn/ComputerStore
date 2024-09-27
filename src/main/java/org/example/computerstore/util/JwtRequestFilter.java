package org.example.computerstore.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.computerstore.services.ComputerUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    private ComputerUserDetailsService computerUserDetailsService;

    public JwtRequestFilter(ComputerUserDetailsService computerUserDetailsService, JwtUtil jwtUtil) {
        this.computerUserDetailsService = computerUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/api/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        if ("/api/register".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        var x = request.getCookies();
        Cookie jwtCookie = null;
        if (x == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }

        for (var cookie : x) {
            if (cookie.getName().equals("JWT")) {
                jwtCookie = cookie;
                break;
            }
        }

        if (jwtCookie == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String username = null;
            String jwt = jwtCookie.getValue();

            username = jwtUtil.extractUsername(jwt); // maybe check for badly formed JWTs here

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.computerUserDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
