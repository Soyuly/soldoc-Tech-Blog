package com.soldoc.tech.oauth.config.security;

import com.soldoc.tech.oauth.api.repository.user.UserRefreshTokenRepository;
import com.soldoc.tech.oauth.config.properties.AppProperties;
import com.soldoc.tech.oauth.config.properties.CorsProperties;
import com.soldoc.tech.oauth.security.entity.RoleType;
import com.soldoc.tech.oauth.security.exception.RestAuthenticationEntryPoint;
import com.soldoc.tech.oauth.security.filter.TokenAuthenticationFilter;
import com.soldoc.tech.oauth.security.handler.OAuth2AuthenticationFailureHandler;
import com.soldoc.tech.oauth.security.handler.OAuth2AuthenticationSuccessHandler;
import com.soldoc.tech.oauth.security.handler.TokenAccessDeniedHandler;
import com.soldoc.tech.oauth.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.soldoc.tech.oauth.security.service.CustomOAuth2UserService;
import com.soldoc.tech.oauth.security.service.CustomUserDetailsService;
import com.soldoc.tech.oauth.security.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
// 웹 보안 기능 초기화 및 설정이 가능핟록 해주는 클래스
// 여기
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    // UserDatail 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()// CORS 설정
                .and()
                //Session 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //폼 비활성화(REST API라 사실 필요없을 것 같음)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()

                // 예외처리 기능
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 인가되지 않은 사용자가 보안된 리소스 접근시 에러 예외처리
                .accessDeniedHandler(tokenAccessDeniedHandler) //권한이 부족할 때 나타나는 에러 예외처리
                .and()

                // 각 URL별 요청을 나눈다.
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/authtest/**").hasAnyAuthority(RoleType.USER.getCode())
                .antMatchers("/api/authtest/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .anyRequest().permitAll()
                .and()

                //로그인 했을 때 액션
                .oauth2Login()

                //인가에 대한 요청의 서비스 -> 여기에서 적은 url 접속시 oauth로그인 요청
                // http://locahost:9000/oauth2/authrorization으로 접속해야함.
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")

                // 인가 요청을 시작한 지점부 인가 요청 받는 시점까지 쿠키에서 인가 권한을 유지해준다.
                .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository())
                .and()

                //authoization 응답이 처리될 URI
                .redirectionEndpoint()
                .baseUri("oauth2/redirect/*")
                .and()

                // 로그인 성공 이의 설정을 시작
                .userInfoEndpoint()
                .userService(oAuth2UserService) //CustomOAuthUserService에서 시작하겠다.
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler()) //로그인 성공했을 때 handler
                .failureHandler(oAuth2AuthenticationFailureHandler()); // 실패

        // 토큰을 인증하는 필터
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // 인증에 관한 주요 전략 인터페이스
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
     * security 설정 시, 사용할 인코더 설정
     * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                httpCookieOAuth2AuthorizationRequestRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(httpCookieOAuth2AuthorizationRequestRepository());
    }

    // Cors 설정
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
