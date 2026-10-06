package com.clinica.config;
import com.clinica.repository.UsuarioRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
  @Bean UserDetailsService userDetailsService(UsuarioRepository repo){
    return login -> repo.findByLogin(login).map(u -> User.withUsername(u.getLogin())
        .password(u.getSenha()).roles(u.getPerfil().name()).disabled(!u.isAtivo()).build())
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
  }
  @Bean SecurityFilterChain filter(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(a -> a
        .requestMatchers("/login","/cadastro","/css/**").permitAll()
        .requestMatchers("/medicos/**","/funcionarios/**").hasAnyRole("ADMIN","FUNCIONARIO")
        .requestMatchers("/pacientes/**").hasAnyRole("ADMIN","FUNCIONARIO","MEDICO")
        .requestMatchers("/prontuarios/**").hasAnyRole("ADMIN","MEDICO")
        .requestMatchers("/consultas/**").authenticated()
        .anyRequest().authenticated())
      .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/",true).permitAll())
      .logout(l -> l.logoutSuccessUrl("/login?logout").permitAll());
    return http.build();
  }
}
