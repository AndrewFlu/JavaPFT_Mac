package com.cdyne.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2019-01-26T18:09:55.821+03:00
 * Generated source version: 3.2.7
 *
 */
@WebService(targetNamespace = "http://ws.cdyne.com/", name = "IP2GeoHttpGet")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface IP2GeoHttpGet {

    /**
     * Use a License Key of 0 for Testing
     */
    @WebMethod(operationName = "ResolveIP")
    @WebResult(name = "IPInformation", targetNamespace = "http://ws.cdyne.com/", partName = "Body")
    public IPInformation resolveIP(
        @WebParam(partName = "ipAddress", name = "ipAddress", targetNamespace = "")
        java.lang.String ipAddress,
        @WebParam(partName = "licenseKey", name = "licenseKey", targetNamespace = "")
        java.lang.String licenseKey
    );
}