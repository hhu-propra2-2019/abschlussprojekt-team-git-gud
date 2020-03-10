package de.hhu.propra2.material2.mops;

import de.hhu.propra2.material2.mops.Database.FileUploader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SuppressWarnings("all")
@SpringBootApplication
public class Material2Application {

    public static void main(String[] args) {
        SpringApplication.run(Material2Application.class, args);
    }

    @Bean
    public FileUploader minio() throws Exception{
        return new FileUploader();
    }
}
