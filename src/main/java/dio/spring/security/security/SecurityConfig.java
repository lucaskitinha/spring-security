package dio.spring.security.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {

	public static String PREFIX;
	public static String KEY;
	public static Long EXPIRATION;

	public void setKey(String key) {
		KEY = key;
	}

    public void setExpiration(Long expiration) {
        EXPIRATION = expiration;
    }
	public void setPrefix(String prefix) {
        PREFIX = prefix;
    }
}
