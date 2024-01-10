package net.fribbtastic.miniaturesvault.backend;

import com.github.lkqm.spring.api.version.EnableApiVersioning;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApiVersioning
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
