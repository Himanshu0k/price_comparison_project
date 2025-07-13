//package training.project.price_comparison.Configuration;//package price.comparison.training_project.Configuration;
//
//import com.example.jira.jira.Service.UserDetailServices;
//import com.example.jira.jira.Util.JWTUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//
//    @Autowired private UserDetailServices userDetailServices;
//    @Autowired private JWTUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        // Skip filtering for /api/auth/** endpoints
//        String path = request.getRequestURI();
//        if (path.startsWith("/api/auth/") || path.startsWith("/oauth2/") || path.startsWith("/login/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        // runs for every request to check whether user is logged in or not
//        String authHeader = request.getHeader("Authorization");
//        String token = "";
//        String username = "";
//
//        if(authHeader != null && authHeader.startsWith("Bearer")) {
//           token = authHeader.substring(7);
//           username = jwtUtil.extractUsername(token);
//        }
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userDetailServices.loadUserByUsername(username);
//
//            // check if token is valid and belongs to particular user
//            if(jwtUtil.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities()
//                );
//
//                // set the token in security context, that means user is authenticated for rest of the requests
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        // pass the request to next filter or controller
//        filterChain.doFilter(request, response);
//
//    }
//}
