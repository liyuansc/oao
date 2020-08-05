package com.oao.uaa.config;

import com.oao.common.constant.OaoSecurityConstant;
import com.oao.security.OaoUserAuthenticationConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * OAuth2 授权服务器配置
 *
 * @author zlt
 * @date 2018/10/24
 */
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter({AuthorizationServerEndpointsConfigurer.class})
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 注入authenticationManager 来支持 password grant type
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(randomValueAuthorizationCodeServices())
//                .exceptionTranslator(webResponseExceptionTranslator)

        ;
    }

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

    /**
     * 配置应用名称 应用id
     * 配置OAuth2的客户端相关信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("web")
                .secret("123456")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(12))
                .authorizedGrantTypes(OaoSecurityConstant.GrantType.AUTHORIZATION_CODE, OaoSecurityConstant.GrantType.CLIENT_CREDENTIALS)
                .redirectUris("http://www.baidu.com")
                .scopes("all")
        ;
    }

    @Bean
    public RandomValueAuthorizationCodeServices randomValueAuthorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()")
//                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                //让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients();
    }


}
