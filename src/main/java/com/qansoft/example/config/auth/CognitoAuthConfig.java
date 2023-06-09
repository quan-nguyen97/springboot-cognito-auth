package com.qansoft.example.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CognitoAuthConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwtAuthenticationConverter(new JwtConverter())
            )
        );
        return http.build();
    }

    private class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

        @Override
        public AbstractAuthenticationToken convert(Jwt source) {
            Map<String, Object> attributes = source.getClaims();
            List<String> groups = (List<String>) attributes.get("cognito:groups");

            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            if (groups != null) {
                for (String groupName : groups) {
                    grantedAuthorities.add(new OAuth2UserAuthority("GROUP_" + groupName, attributes));
                }
            }

            AbstractAuthenticationToken token = new JwtAuthenticationToken(source, grantedAuthorities);

            return token;
        }
    }
}
