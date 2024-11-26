package dio.spring.security.service;

import dio.spring.security.model.User;
import dio.spring.security.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Busca o usuário no banco de dados
		User user = userRepository.getUserByUsername(username);

		// Mapeia as roles para autoridades do Spring Security
		var authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toList());

		// Retorna um UserDetails implementado pelo próprio Spring Security
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				authorities
		);
	}
}
