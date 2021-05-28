package io.velog.posting.jaxb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JaxbStudyTest {

    @Test
    @DisplayName("unmarshal 테스트")
    public void jaxbUnmarshalTest() throws JAXBException, IOException {

        // Given
        FileInputStream fileInputStream = new FileInputStream("test-data/data1.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlListTag.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // When
        XmlListTag xmlListTag = (XmlListTag) unmarshaller.unmarshal(fileInputStream);
        fileInputStream.close();

        // Then
        assertNotNull(xmlListTag);
        assertNotNull(xmlListTag.getSmartPhoneTags());
        assertEquals(4, xmlListTag.getSmartPhoneTags().length);
        assertEquals("iPhone", xmlListTag.getSmartPhoneTags()[0].getModel());

        for (SmartPhoneTag smartPhoneTag : xmlListTag.getSmartPhoneTags()) {
            System.out.println(smartPhoneTag.toString());
        }
    }

    @Test
    @DisplayName("marshal 테스트")
    public void jaxbMarshalTest() throws JAXBException, IOException {

        // Given
        File file = new File("test-data/data3.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlListTag2.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        XmlListTag2 xmlListTag = new XmlListTag2();
        List<SmartPhoneTag> smartPhoneTags = new ArrayList<>();

        SmartPhoneTag smartPhoneTag = SmartPhoneTag.builder()
                .model("샘성")
                .price(1230000)
                .firmware("1.2.3")
                .build();

        SmartPhoneTag smartPhoneTag2 = SmartPhoneTag.builder()
                .model("엘징")
                .price(1150000)
                .firmware("2.5")
                .build();

        smartPhoneTags.add(smartPhoneTag);
        smartPhoneTags.add(smartPhoneTag2);
        xmlListTag.setSmartPhoneTags(smartPhoneTags);

        // When
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(xmlListTag, file);

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = fileInputStream.readAllBytes();
        String content = new String(bytes);

        // Then
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<list>\n" +
                "    <smart-phone>\n" +
                "        <model>샘성</model>\n" +
                "        <price>1230000</price>\n" +
                "        <firmware>1.2.3</firmware>\n" +
                "    </smart-phone>\n" +
                "    <smart-phone>\n" +
                "        <model>엘징</model>\n" +
                "        <price>1150000</price>\n" +
                "        <firmware>2.5</firmware>\n" +
                "    </smart-phone>\n" +
                "</list>\n", content);
    }
}