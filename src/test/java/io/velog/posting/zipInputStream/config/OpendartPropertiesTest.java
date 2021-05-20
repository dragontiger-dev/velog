package io.velog.posting.zipInputStream.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpendartPropertiesTest {

    @Autowired
    OpendartProperties opendartProperties;

    @Test
    @DisplayName("환경변수 불러오기")
    public void OpendartPropertiesLoadTest() {

        // Given & When
        String key = opendartProperties.getKey();

        // Then
        assertNotNull(key);
        assertEquals(System.getenv("OPENDART_KEY"), key);
    }
}