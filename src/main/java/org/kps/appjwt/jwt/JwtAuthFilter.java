package org.kps.appjwt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kps.appjwt.service.AppUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AppUserService appUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extract JWT token from header
            String token = authHeader.substring(7);

            // Extract email (or username) from the token
            String email = jwtUtils.extractEmail(token);

            // Load user details from the database
            UserDetails userDetails = appUserService.loadUserByUsername(email);

            // Proceed only if email is found and no authentication is already set
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Validate the token against the user details
                if(jwtUtils.isTokenValid(token, userDetails)) {

                    // Create an authentication token with user's authorities
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            email, null, userDetails.getAuthorities()
                    );

                    // Attach request details (like IP, session info) to the token
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication token in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    throw new RuntimeException("Invalid Token");
                }
            }

        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);
    }
}