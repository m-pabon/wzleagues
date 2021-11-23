package gg.wzleagues.wzleagues;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableEncryptableProperties
@PropertySource(name="EncryptedProperties", value = "classpath:encrypted.properties")
public class WzleaguesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WzleaguesApplication.class, args);
	}

}
