package de.hhu.propra2.material2.mops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SuppressWarnings("all")
@SpringBootApplication
@ConfigurationPropertiesScan( {"de.hhu.propra2.material2.mops.domain.services"})
@EnableScheduling
public class Material2Application {

    public static void main(String[] args) {
        SpringApplication.run(Material2Application.class, args);
    }

}
