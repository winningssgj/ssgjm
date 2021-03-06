
package cn.com.winning.ssgj.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for execBizProcessResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="execBizProcessResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BizProcessResult" type="{http://ws.livebos.apex.com/}bizProcessResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "execBizProcessResponse", propOrder = {
    "bizProcessResult"
})
public class ExecBizProcessResponse {

    @XmlElement(name = "BizProcessResult")
    protected BizProcessResult bizProcessResult;

    /**
     * Gets the value of the bizProcessResult property.
     * 
     * @return
     *     possible object is
     *     {@link BizProcessResult }
     *     
     */
    public BizProcessResult getBizProcessResult() {
        return bizProcessResult;
    }

    /**
     * Sets the value of the bizProcessResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link BizProcessResult }
     *     
     */
    public void setBizProcessResult(BizProcessResult value) {
        this.bizProcessResult = value;
    }

}
