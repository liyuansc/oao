package com.liyu.oao.user.configuration;

import com.liyu.oao.security.JwtTokenManage;
import com.liyu.oao.user.security.OaoReactiveUserDetailsService;
import com.liyu.oao.webflux.security.OaoServerAccessDeniedHandler;
import com.liyu.oao.webflux.security.OaoServerAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebFluxSecurity
public class SecurityConfiguration {
    @Autowired
    private OaoReactiveUserDetailsService userDetailsService;


    @Bean
    public ServerAccessDeniedHandler serverAccessDeniedHandler() {
        return new OaoServerAccessDeniedHandler();
    }

    @Bean
    public ServerAuthenticationEntryPoint serverAuthenticationEntryPoint() {
        return new OaoServerAuthenticationEntryPoint();
    }

    @Bean
    public JwtTokenManage jwtTokenManage() {
        return new JwtTokenManage();
    }


    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        //默认使用用户名密码登录
        UserDetailsRepositoryReactiveAuthenticationManager udrManger = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        udrManger.setPasswordEncoder(passwordEncoder());

        List<ReactiveAuthenticationManager> managers = Stream.of(udrManger).collect(Collectors.toList());
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //TODO 测试用
        return NoOpPasswordEncoder.getInstance();
    }

//
//    @Bean
//    public ReactiveClientRegistrationRepository clientRegistrations() {
//        ClientRegistration clientRegistration = ClientRegistrations
//                .fromIssuerLocation("https://idp.example.com/auth/realms/demo")
//                .clientId("spring-gateway")
//                .clientSecret("6cea952f-10d0-4d00-ac79-cc865820dc2c")
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
//    }

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http

                .csrf().disable()
                //用我们自己的登录
                .formLogin().disable()
                .logout().disable()

//                    .authenticationManager(ReactiveAuthenticationManager)
//                .cors() // We don't need CSRF for JWT  based authentication

//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                .and()
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(serverAccessDeniedHandler())
                .authenticationEntryPoint(serverAuthenticationEntryPoint())

//                .and()
//                .oauth2Login()
//                .authenticationManager()
//                .authorizationRequestRepository()
//                .authorizationRequestResolver()

//                .authorizationRequestRepository()
//                .authorizationRequestRepository()
//
//                .authorizationRequestResolver()
//                .authenticationConverter()
//                .authenticationConverter()
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
