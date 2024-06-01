package com.example.onlinebankingapp.filters;


import com.example.onlinebankingapp.components.JwtTokenUtils;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.services.Customer.CustomerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter  extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;


    //overriding the doFilterInternal function
    //in charge: Trieu
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Check if the request should bypass token validation
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); // Enable bypass for certain endpoints
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            // Check if Authorization header is present and starts with "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // If not, send an unauthorized error response
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "authHeader null or not started with Bearer");
                return;
            }
            // Extract the token from the header
            final String token = authHeader.substring(7);
            // Extract the subject (phone number) from the token
            final String phoneNumber = jwtTokenUtil.getSubject(token);
            // Check if the authentication context is null and the phone number extracted is not null
            if (phoneNumber != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details by phone number
                CustomerEntity customer = (CustomerEntity)  userDetailsService.loadUserByUsername(phoneNumber);
                // Validate the token
                if(jwtTokenUtil.validateToken(token, customer)) {
                    // If valid, set authentication token in security context
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    customer,
                                    null,
                                    customer.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); // Continue the filter chain
        }catch (Exception e) {
            // Handle exceptions
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }

    // Method to check if certain endpoints can bypass token validation
    //in charge: Trieu
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                // Healthcheck request, no JWT token required
                Pair.of(String.format("%s/customers/insertCustomer", apiPrefix), "POST"),
                Pair.of(String.format("%s/customers/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/customers/refreshToken", apiPrefix), "POST"),
                Pair.of(String.format("%s/rewards/image/**", apiPrefix), "GET"),

                // Swagger
                Pair.of("/api-docs","GET"),
                Pair.of("/api-docs/**","GET"),
                Pair.of("/swagger-resources","GET"),
                Pair.of("/swagger-resources/**","GET"),
                Pair.of("/configuration/ui","GET"),
                Pair.of("/configuration/security","GET"),
                Pair.of("/swagger-ui/**","GET"),
                Pair.of("/swagger-ui.html", "GET"),
                Pair.of("/swagger-ui/index.html", "GET")
        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : bypassTokens) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request path and method match any pair in the bypassTokens list
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}
