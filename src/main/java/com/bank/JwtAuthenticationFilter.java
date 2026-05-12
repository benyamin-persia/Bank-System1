package com.bank;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException; // Imports servlet exception
import java.io.IOException;
import com.bank.service.BankUserDetailsService;
import com.bank.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component // Registers filter as Spring bean
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired // Injects JWT service
    private JwtService jwtService;              // Used to read and validate token

    @Autowired // Injects user details service
    private BankUserDetailsService bankUserDetailsService; // Used to load user by username

    @Override // Overrides filter method
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { // Runs for each request

        final String authHeader = request.getHeader("Authorization"); // Gets Authorization header
        final String jwt; // Stores JWT token
        final String username; // Stores username from token

        if (authHeader == null || !authHeader.startsWith("Bearer ")) { // Checks if header is missing or invalid
            filterChain.doFilter(request, response); // Continues request
            return; // Stops method
        }

        jwt = authHeader.substring(7); // Removes Bearer prefix
        username = jwtService.extractUsername(jwt); // Extracts username from token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Checks username and auth state
            UserDetails userDetails = bankUserDetailsService.loadUserByUsername(username); // Loads user details

            if (jwtService.isTokenValid(jwt, userDetails)) { // Validates token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // Creates auth object

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Adds request details
                SecurityContextHolder.getContext().setAuthentication(authToken); // Saves authentication
            }
        }

        filterChain.doFilter(request, response); // Continues filter chain
    }
}
