package io.velog.posting.jaxb;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter @Setter
@ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartPhoneTag {

    @XmlElement(name = "model")
    private String model;

    @XmlElement(name = "price")
    private int price;

    @XmlElement(name = "firmware")
    private String firmware;

}
