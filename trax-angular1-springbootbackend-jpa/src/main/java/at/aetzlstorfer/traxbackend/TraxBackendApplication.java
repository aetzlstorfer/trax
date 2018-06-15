package at.aetzlstorfer.traxbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackageClasses = {TraxBackendApplication.class/*, Jsr310JpaConverters.class*/})
public class TraxBackendApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TraxBackendApplication.class, args);
    }

}
