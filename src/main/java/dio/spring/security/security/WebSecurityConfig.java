package dio.spring.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

	private final JWTFilter jwtFilter;
	private final AuthenticationProvider authenticationProvider;

	public WebSecurityConfig(JWTFilter jwtFilter, AuthenticationProvider authenticationProvider) {
		this.jwtFilter = jwtFilter;
		this.authenticationProvider = authenticationProvider;
	}

	@Bean
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeRequests((auth) -> auth
				.requestMatchers("/h2-console/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/login").permitAll()
				.requestMatchers(HttpMethod.POST,"/users").permitAll()
				.requestMatchers(HttpMethod.GET,"/users").hasAnyRole("USERS","MANAGERS")
				.requestMatchers("/managers").hasAnyRole("MANAGERS")
				.anyRequest().authenticated())
				.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}


}
