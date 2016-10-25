package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.*;
import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * 套餐变更
 */
public class OrderManageImpl extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmOrderManageRequest request = createOrderManageRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmOrderManageRequest createOrderManageRequest(String xmlStr) {
        UdmOrderManageRequest orderManageRequest = new UdmOrderManageRequest();
        Element root= XmlParser.getRootElement(xmlStr);
        List<Element> personInfoElements = root.elements("PersonInfo");
        List<UdmPersonInfo> personInfoList = new ArrayList();

        // header
        Header header = Utils.getAsyncHeader(root);
        orderManageRequest.setHeader(header);

        for (Element personInfoElement : personInfoElements) {
            UdmPersonInfo personInfo = new UdmPersonInfo();
            personInfo.setEcCustID(personInfoElement.elementTextTrim("EcCustID"));
            personInfo.setMobNum(personInfoElement.elementTextTrim("MobNum"));
            personInfo.setOprType(personInfoElement.elementTextTrim("OprType"));
            personInfo.setProvinceID(personInfoElement.elementTextTrim("ProvinceID"));
            personInfo.setSubsID(personInfoElement.elementTextTrim("SubsID"));

            // ProdInfo
            List<Element> ProdInfoElements = personInfoElement.elements("ProdInfo");
            List<UdmProdInfo> prodInfoList = new ArrayList();
            for (Element prodInfoElement : ProdInfoElements) {
                UdmProdInfo prodInfo = new UdmProdInfo();
                prodInfo.setPkgProdID(prodInfoElement.elementTextTrim("PkgProdID"));
                prodInfo.setOperType(prodInfoElement.elementTextTrim("OperType"));
                prodInfo.setProdType(prodInfoElement.elementTextTrim("ProdType"));
                prodInfo.setProdID(prodInfoElement.elementTextTrim("ProdID"));
                prodInfo.setProdInstEffTime(prodInfoElement.elementTextTrim("ProdInstEffTime"));
                prodInfo.setProdInstExpTime(prodInfoElement.elementTextTrim("ProdInstExpTime"));
                prodInfo.setProdInstID(prodInfoElement.elementTextTrim("ProdInstID"));

                // ProdAttrInfo
                List<Element> prodAttrInfoElements =prodInfoElement.elements("ProdAttrInfo");
                List<UdmProdAttrInfo> prodAttrInfoList = new ArrayList();
                for (Element prodAttrInfoElement : prodAttrInfoElements) {
                    UdmProdAttrInfo prodAttrInfo = new UdmProdAttrInfo();
                    prodAttrInfo.setOperType(prodAttrInfoElement.elementTextTrim("OperType"));
                    prodAttrInfo.setAttrKey(prodAttrInfoElement.elementTextTrim("AttrKey"));
                    prodAttrInfo.setAttrValue(prodAttrInfoElement.elementTextTrim("AttrValue"));
                    prodAttrInfo.setServiceID(prodAttrInfoElement.elementTextTrim("ServiceID"));
                    prodAttrInfoList.add(prodAttrInfo);
                }
                prodInfo.setProdAttrInfo(prodAttrInfoList);
                prodInfoList.add(prodInfo);
            }
            personInfo.setProdInfo(prodInfoList);
            personInfoList.add(personInfo);
        }

        orderManageRequest.setPersonInfo(personInfoList);
        return orderManageRequest;

    }
}
