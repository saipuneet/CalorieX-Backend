package com.CalorieX.CalorieX_Backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private JwtService jwtService;

    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // the request coming from the Frontend
        final String authHeader = request.getHeader("Authorization");

        //whether the token is null or it dont start with the standard jwt tojken standard
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return ;
        }

        // so the backend only need the token its dont need the Bearer that is the reason with are starting with substring
        final String jwtToken = authHeader.substring(7);


        // extracting the username of the joken
        final String username = jwtService.extractUsername(jwtToken);

        // Does Request already have authenciated user using securityContextHolder
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //Load UserDeatils from Database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validation Jwt against userdetails
            if(jwtService.isTokenValid(jwtToken,userDetails)){

                //Creating the new Authentication Object
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                //Store Authenication inside the SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        String path = request.getRequestURI();

        if(path.contains("/swagger-ui") || path.contains("/v3/api-docs")) {
            //Continue Request
            filterChain.doFilter(request, response);
            return;
        }
    }
}
