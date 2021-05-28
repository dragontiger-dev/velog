package io.velog.posting.jaxb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "list")
@Getter @Setter @ToString
public class XmlListTag2 {

    @XmlElement(name = "smart-phone")
    private List<SmartPhoneTag> smartPhoneTags;
}
