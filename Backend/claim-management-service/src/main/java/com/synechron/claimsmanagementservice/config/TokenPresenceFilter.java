package com.synechron.claimsmanagementservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.util.PropertyUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class TokenPresenceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSignKey())
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                String username = claims.getSubject();
                String role = (String) claims.get("role");

                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing or invalid.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing or invalid.");
        }
    }

    public static String loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertyUtils.class
                .getClassLoader()
                .getResourceAsStream("application.properties");
        configuration.load(inputStream);
        inputStream.close();
        String property = configuration.getProperty("JWT_SECRET");
        return property;
    }


    private Key getSignKey() {
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = Decoders.BASE64.decode(TokenPresenceFilter.loadProperties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}