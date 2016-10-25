package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.UdmBasicServiceInfo;
import com.chinamobile.iot.udm.api.reverse.async.UdmBasicServiceRequest;
import com.chinamobile.iot.udm.api.reverse.async.UdmBasicServiceProdInfo;
import com.chinamobile.iot.udm.api.reverse.async.Header;

import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * 基础服务暂停恢复
 */
public class BasicServiceImpl extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmBasicServiceRequest request = CreateBasicServiceRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmBasicServiceRequest CreateBasicServiceRequest(String xmlStr) {
        UdmBasicServiceRequest basicServiceRequest = new  UdmBasicServiceRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        List<Element> basicServiceInfoElements = root.elements("BasicServiceInfo");
        List<UdmBasicServiceInfo> basicServiceInfoList = new ArrayList();

        // Header
        Header header = Utils.getAsyncHeader(root);
        basicServiceRequest.setHeader(header);

        for (Element basicServiceInfoElement : basicServiceInfoElements) {
            UdmBasicServiceInfo basicServiceInfo = new  UdmBasicServiceInfo();

            basicServiceInfo.setSubsID(basicServiceInfoElement.elementTextTrim("SubsId"));
            basicServiceInfo.setMobNum(basicServiceInfoElement.elementTextTrim("MobNum"));

            List<Element> basicServiceProdInfoElements = basicServiceInfoElement.elements("BasicServiceProdInfo");
            List<UdmBasicServiceProdInfo> basicServiceProdInfoList = new ArrayList();
            for (Element basicServiceProdInfoElement : basicServiceProdInfoElements) {
                UdmBasicServiceProdInfo basicServiceProdInfo = new UdmBasicServiceProdInfo();
                basicServiceProdInfo.setProdID(basicServiceProdInfoElement.elementTextTrim("ProdID"));
                basicServiceProdInfo.setProdInstID(basicServiceProdInfoElement.elementTextTrim("ProdInstID"));
                basicServiceProdInfo.setOprType(basicServiceProdInfoElement.elementTextTrim("OprType"));
                basicServiceProdInfoList.add(basicServiceProdInfo);
            }
            basicServiceInfo.setProdInfo(basicServiceProdInfoList);
            basicServiceInfo.setProvinceID(basicServiceInfoElement.elementTextTrim("ProvinceID"));

            basicServiceInfoList.add(basicServiceInfo);
        }
        basicServiceRequest.setBasicServiceInfo(basicServiceInfoList);

        return basicServiceRequest;
    }
}
