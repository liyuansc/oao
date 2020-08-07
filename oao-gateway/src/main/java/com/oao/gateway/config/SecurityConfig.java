package com.oao.gateway.config;

import com.oao.gateway.properties.SecurityProperties;
import com.oao.gateway.security.*;
import com.oao.security.JwtTokenManage;
import com.oao.security.OaoUserAuthenticationConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebFluxSecurity
public class SecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return new OaoOauth2AuthenticationManager(tokenStore());
    }

    @Bean
    public JwtTokenManage jwtTokenManage() {
        return new JwtTokenManage();
    }

    @Bean
    public ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return new OaoServerAccessDeniedHandler();
    }

    @Bean
    public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return new OaoServerAuthenticationEntryPoint();
    }

    @Bean
    public OaoAuthorizationManager oaoAuthorizationManager() {
        return new OaoAuthorizationManager();
    }

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        //token转换器
        OaoTokenAuthenticationConverter tokenAuthenticationConverter = new OaoTokenAuthenticationConverter();
//        tokenAuthenticationConverter.setAllowUriQueryParameter(true);

        ServerAuthenticationEntryPoint authenticationEntryPoint = serverAuthenticationEntryPoint();

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager());
        authenticationWebFilter.setServerAuthenticationConverter(tokenAuthenticationConverter);
//        authenticationWebFilter.setAuthenticationSuccessHandler();
        authenticationWebFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint));
        authenticationWebFilter.setAuthenticationSuccessHandler(new OaoAuthenticationSuccessHandler());
        http.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchange = http

                .csrf().disable()
                //用我们自己的登录
                .formLogin()
                .disable()
//                    .authenticationManager(ReactiveAuthenticationManager)
//                .cors() // We don't need CSRF for JWT based authentication

//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll();
//        List<String> permitUris = securityProperties.getPermitUris();
//        if (permitUris != null) {
//            authorizeExchange.pathMatchers(permitUris.stream().toArray(String[]::new)).permitAll();
//        }
        authorizeExchange.anyExchange()
                .access(oaoAuthorizationManager());
        SecurityWebFilterChain chain =  authorizeExchange
                .and()
                .exceptionHandling()
                .accessDeniedHandler(serverAccessDeniedHandler())
                .authenticationEntryPoint(serverAuthenticationEntryPoint())
                .and().build();

        return chain;


//        ServerHttpSecurity.FormLoginSpec formLoginSpec = http.formLogin();
//        formLoginSpec.authenticationSuccessHandler(createAuthenticationSuccessHandler())
//                .loginPage("/login")
//                .authenticationFailureHandler(createAuthenticationFailureHandler());
//        return formLoginSpec.and()
//                .csrf().disable()
//                .httpBasic().disable()
//                .authorizeExchange()
//                .pathMatchers(AUTH_WHITELIST).permitAll()
//                .anyExchange().authenticated()
//                .and().build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//
//                .csrf().disable()
//                //用我们自己的登录
//                .formLogin()
//                .disable()
////                .cors() // We don't need CSRF for JWT based authentication
//
////                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(new OaoAccessDeniedHandlerImpl())
//                .authenticationEntryPoint(new OaoAuthenticationEntryPoint())
////                .and()
////                .authorizeRequests()
////                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
////                    .antMatchers(LOGIN_ENTRY_POINT).permitAll()
//
////                .and()
////                .authorizeRequests()
////                .anyRequest().permitAll()
////                    .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()
//
//
////                .and()
////                .exceptionHandling()
////                    .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
//        ;
//        // disable cache
////        http.headers().cacheControl();
//
////            http.addFilterBefore(new JwtAuthenticationTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123e131421412");
        DefaultAccessTokenConverter ac = new DefaultAccessTokenConverter();
        ac.setUserTokenConverter(new OaoUserAuthenticationConvert());
        converter.setAccessTokenConverter(ac);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
