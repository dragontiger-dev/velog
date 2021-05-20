package io.velog.posting.zipInputStream.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ToString
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "opendart")
public class OpendartProperties {
    private String key;
    private String url;
}

