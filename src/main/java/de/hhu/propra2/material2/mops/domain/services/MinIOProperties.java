package de.hhu.propra2.material2.mops.domain.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("minio")
@Getter
@Setter
@Component
public class MinIOProperties {
    private String endpoint = "http://localhost:9000/";
    private String accesskey = "accesskey";
    private String secretkey = "secretkey";
    private String bucketname = "defaultBucket";
}
