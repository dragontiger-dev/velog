package io.velog.posting.zipInputStream;

import io.velog.posting.zipInputStream.config.OpendartProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class Opendart {

    private static OpendartProperties opendartProperties;

    public Opendart(OpendartProperties opendartProperties) {
        Opendart.opendartProperties = opendartProperties;
    }

    public static Path downloadCorpCodeZip() {

        RestTemplate restTemplate = new RestTemplate();
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(opendartProperties.getUrl())
                .path("/corpCode.xml")
                .queryParam("crtfc_key", opendartProperties.getKey())
                .build();

        return restTemplate.execute(uriComponents.toUriString(), HttpMethod.GET, null, clientHttpResponse -> {

            Path ZipFile = Files.createTempFile("opendart-", ".zip");
            Files.write(ZipFile, clientHttpResponse.getBody().readAllBytes());

            return ZipFile;
        });
    }
}
