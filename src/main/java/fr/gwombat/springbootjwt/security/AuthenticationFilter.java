package fr.gwombat.springbootjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.gwombat.springbootjwt.security.configuration.JwtProperties;
import fr.gwombat.springbootjwt.user.ApplicationUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties         jwtProperties;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ApplicationUser credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), ApplicationUser.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    new ArrayList<>(0)
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final String token = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret().getBytes())
                .compact();
        response.addHeader(jwtProperties.getHeader(), jwtProperties.getTokenPrefix() + token);
    }
}
