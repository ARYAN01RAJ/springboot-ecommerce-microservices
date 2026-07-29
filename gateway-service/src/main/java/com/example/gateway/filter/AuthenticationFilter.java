package com.example.gateway.filter;
import com.example.gateway.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.contains("/auth") || path.contains("/swagger-ui") || path.contains("/v3/api-docs") || path.contains("/webjars")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        try {
            jwtUtil.validateToken(token);
            String roles = jwtUtil.extractRole(token);

            if (!isAuthorized(path, roles)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Access Denied: You do not have permission to access this resource");
                return; // Stop the request immediately
            }

            HttpServletRequestWrapper modifiedRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getHeader(String name) {
                    if ("X-User-Role".equalsIgnoreCase(name)) {
                        return roles; // Return the roles extracted from the JWT
                    }
                    return super.getHeader(name);
                }
            };

            // Pass the MODIFIED request down the chain instead of the original one
            filterChain.doFilter(modifiedRequest, response);
            return;

        } catch (Exception e) {
            System.out.println("Invalid Token! " + e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or expired token");
            return;
        }
    }

    private boolean isAuthorized(String path, String roles) {
        if (roles == null) return false;
        if (roles.contains("ADMIN")) {
            return true;
        }
        if (path.contains("/api/item/getall") && roles.contains("USER")) {
            return true;
        }
        if (path.contains("/api/inventory") || path.contains("/api/item")) {
            return roles.contains("STAFF");
        }
        if (path.contains("/api/order")) {
            return roles.contains("USER");
        }
        return false;
    }
}