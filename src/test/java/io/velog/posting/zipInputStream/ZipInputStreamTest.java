package io.velog.posting.zipInputStream;

import io.velog.posting.zipInputStream.config.OpendartProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ZipInputStreamTest {

    @Autowired
    OpendartProperties opendartProperties;

    @Test
    @DisplayName("zip 파일 다운로드")
    public void getCorpCodeZipFile() throws InterruptedException, IOException {

        // Given
        RestTemplate restTemplate = new RestTemplate();
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(opendartProperties.getUrl())
                .path("/corpCode.xml")
                .queryParam("crtfc_key", opendartProperties.getKey())
                .build();

        // When
        Path file = restTemplate.execute(uriComponents.toUriString(), HttpMethod.GET, null, clientHttpResponse -> {

            Path ret = Files.createTempFile("opendart-", ".zip");
            Files.write(ret, clientHttpResponse.getBody().readAllBytes());

            return ret;
        });

        // Then
        assertNotNull(file);
        assertEquals("opendart", file.getFileName().toString().substring(0, 8));

        // 테스트 후 삭제
        Files.delete(file);
    }
}
