package com.college.yi.bookmanager.confing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.college.yi.bookmanager.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 認証プロバイダーのセット
            .authenticationProvider(authenticationProvider())

            // CSRFは有効にしたいなら disable() を外す（csrfトークン送信をテンプレートで対応してください）
            .csrf().disable()

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()  // ログインページと静的リソースは無条件許可
                .requestMatchers("/api/books").hasAnyRole("USER", "ADMIN")    // GET系アクセスはUSERまたはADMIN
                .requestMatchers("/api/books/**").hasRole("ADMIN")            // POST/PUT/DELETEはADMINのみ
                .anyRequest().authenticated()                                 // その他は認証済みであればOK
            )

            .formLogin(form -> form
                .loginPage("/login")              // 独自ログインページの指定
                .defaultSuccessUrl("/", true)    // ログイン成功後のリダイレクト先
                .failureUrl("/login?error")      // ログイン失敗時のリダイレクト先
                .permitAll()
            )

            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    // DaoAuthenticationProviderを登録し、UserDetailsServiceとPasswordEncoderをセット
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);  // 自作のUserDetailsServiceをセット
        provider.setPasswordEncoder(passwordEncoder());      // BCryptのパスワードエンコーダーをセット
        return provider;
    }

    // パスワードをハッシュ化するBean（BCryptを使う）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
