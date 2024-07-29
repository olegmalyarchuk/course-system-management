package com.coursesystem.configuration.security.filter;

import com.coursesystem.configuration.security.TokenProvider;
import com.coursesystem.repository.UserRepository;
import com.coursesystem.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.coursesystem.model.User;
import com.coursesystem.model.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public JwtTokenFilter(final TokenProvider tokenProvider, final UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (Objects.nonNull(token) && tokenProvider.validateToken(token)) {
            String userEmail = tokenProvider.getEmailFromToken(token);
            var user = userRepository.findByEmail(userEmail);
            var authorities = getSimpleGrantedAuthorities(user);
            var auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(User user) {
        var userRoles = JsonUtil.deserialize(user.getUserRoles(), new TypeReference<Set<UserRole>>() {});
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(userRole.name())).collect(Collectors.toSet());
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
