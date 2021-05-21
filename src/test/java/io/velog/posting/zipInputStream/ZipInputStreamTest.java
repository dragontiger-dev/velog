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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ZipInputStreamTest {

    @Autowired
    OpendartProperties opendartProperties;

    @Test
    @DisplayName("zip 파일 다운로드")
    public void downloadCorpCodeZipFile() throws InterruptedException, IOException {

        // Given
        RestTemplate restTemplate = new RestTemplate();
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(opendartProperties.getUrl())
                .pathSegment("corpCode.xml")
                .queryParam("crtfc_key", opendartProperties.getKey())
                .build();

        // When
        Path file = restTemplate.execute(uriComponents.toUriString(), HttpMethod.GET, null, response -> {
            Path zipFile = Files.createTempFile("opendart-", ".zip");
            Files.write(zipFile, response.getBody().readAllBytes());

            return zipFile;
        });

        // Then
        assertNotNull(file);
        assertEquals("opendart", file.getFileName().toString().substring(0, 8));

        // 테스트 후 삭제
        Files.delete(file);
    }

    @Test
    @DisplayName("zip 파일 압축 파일 내 CORPCODE.xml 파일 추출")
    public void getCorpCodeXmlFile() throws IOException {

        // Given
        Path zipFile = Opendart.downloadCorpCodeZip();
        byte[] buf = Files.readAllBytes(zipFile);
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(buf));
        ZipEntry zipEntry = null;

        String testDir = "C:\\Users\\dragontiger\\Desktop\\test\\";
        String fileName = "CORPCODE.xml";

        // 이미 존재하는 파일이 있으면 삭제
        Path path = Path.of(testDir + fileName);
        Files.deleteIfExists(path);

        // When
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            Files.copy(zipInputStream, Paths.get(testDir + zipEntry.getName()));
        }
        zipInputStream.closeEntry();
        zipInputStream.close();

        // Then
        String[] fileNameArr = new File(testDir).list();
        assertNotNull(fileNameArr);
        assertEquals(fileName, fileNameArr[0]);

        Files.delete(zipFile);
    }
}
