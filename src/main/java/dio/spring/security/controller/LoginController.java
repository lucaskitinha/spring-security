package dio.spring.security.controller;

import dio.spring.security.dtos.Login;
import dio.spring.security.dtos.Session;
import dio.spring.security.security.JwtService;
import dio.spring.security.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsServiceImpl userDetailsService;

	public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/login")
	public Session login(@RequestBody Login authRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
		);
		UserDetails user = userDetailsService.loadUserByUsername(authRequest.getUsername());
		Session session = new Session();
		String token = "Bearer " + jwtService.generateToken(user);
		session.setToken(token);
		session.setLogin(authRequest.getUsername());

		return session;
	}
}