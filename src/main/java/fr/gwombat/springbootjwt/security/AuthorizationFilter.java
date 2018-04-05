package fr.gwombat.springbootjwt.security;

import fr.gwombat.springbootjwt.security.configuration.JwtProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtProperties jwtProperties;

    public AuthorizationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties) {
        super(authenticationManager);
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader(jwtProperties.getHeader());

        if (header == null || !header.startsWith(jwtProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        final UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader(jwtProperties.getHeader());
        final String user = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret().getBytes())
                .parseClaimsJws(token.replace(jwtProperties.getTokenPrefix(), ""))
                .getBody()
                .getSubject();

        if (user != null)
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>(0));

        return null;
    }
}
