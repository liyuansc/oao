package com.liyu.oao.gateway.configuration;

import com.liyu.oao.gateway.security.OaoTokenAuthenticationConverter;
import com.liyu.oao.gateway.security.OaoTokenAuthenticationManager;
import com.liyu.oao.security.JwtTokenManage;
import com.liyu.oao.webflux.security.OaoServerAccessDeniedHandler;
import com.liyu.oao.webflux.security.OaoServerAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Bean
    public OaoTokenAuthenticationManager oaoTokenAuthenticationManager() {
        return new OaoTokenAuthenticationManager();
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
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        //token转换器
        OaoTokenAuthenticationConverter tokenAuthenticationConverter = new OaoTokenAuthenticationConverter();
//        tokenAuthenticationConverter.setAllowUriQueryParameter(true);

        ServerAuthenticationEntryPoint authenticationEntryPoint = serverAuthenticationEntryPoint();

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(oaoTokenAuthenticationManager());
        authenticationWebFilter.setServerAuthenticationConverter(tokenAuthenticationConverter);
//        authenticationWebFilter.setAuthenticationSuccessHandler();
        authenticationWebFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint));

        http.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http

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
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(serverAccessDeniedHandler())
                .authenticationEntryPoint(serverAuthenticationEntryPoint())
                .and().build();


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
}
