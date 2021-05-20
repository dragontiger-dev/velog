package io.velog.posting.config;

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
    public void OpendartPropertiesInjectionTest() {

        // Given & When
        String key = opendartProperties.getKey();

        // Then
        assertNotNull(key);
        assertEquals(System.getenv("OPENDART_KEY"), key);
    }
}