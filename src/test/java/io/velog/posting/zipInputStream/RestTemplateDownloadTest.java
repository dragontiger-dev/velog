package io.velog.posting.zipInputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTemplateDownloadTest {

    @Test
    @DisplayName("CorpCode zip 파일 다운로드")
    public void getCorpCodeZipFile() throws InterruptedException, IOException {

        // Given
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://opendart.fss.or.kr/api/corpCode.xml?crtfc_key=";

        // When
        Path file = restTemplate.execute(url, HttpMethod.GET, null, clientHttpResponse -> {

            Path ret = Files.createTempFile("opendart-", ".zip");
            Files.write(ret, clientHttpResponse.getBody().readAllBytes());

            return ret;
        });

        // Then
        assert file != null;
        assertEquals("opendart", file.getFileName().toString().substring(0, 8));

        // 테스트 후 삭제
        Files.delete(file);
    }
}
