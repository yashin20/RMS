package project.RMS.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    // PasswordEncoder Bean 등록 - password 암호화 (방식 - BCryptPasswordEncoder)
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // WebSecurityCustomizer Bean 등록 - 정적 resources 접근을 위함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스가 위치한 파일의 보안 처리를 무시 (누구든 접근 가능)
        return (web -> web.ignoring()
                .requestMatchers("/img/**", "/css/**"));
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/", "/members/login", "/members/join").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin((form) ->
                        form
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage("/members/login")
                                .loginProcessingUrl("/login")
                                .failureUrl("/members/login?error=true")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") //로그아웃 처리 URL
                                .logoutSuccessUrl("/") //로그아웃 성공 후 리다이렉트 할 URL
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
        ;

        return http.build();
    }


}
