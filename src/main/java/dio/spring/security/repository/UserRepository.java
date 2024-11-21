package dio.spring.security.repository;

import dio.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from user u join fetch u.roles where u.username = (:username)" )
	public User getUserByUsername(String username);
}
