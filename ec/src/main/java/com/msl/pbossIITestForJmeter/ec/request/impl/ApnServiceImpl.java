package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.UdmApnInfo;
import com.chinamobile.iot.udm.api.reverse.async.UdmApnServiceRequest;
import com.chinamobile.iot.udm.api.reverse.async.Header;
import com.chinamobile.iot.udm.api.reverse.async.UdmApnServiceInfo;
import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * APN开关管理
 */
public class ApnServiceImpl extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmApnServiceRequest request = CreateApnServiceRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmApnServiceRequest CreateApnServiceRequest(String xmlStr) {
        UdmApnServiceRequest apnServiceRequest = new UdmApnServiceRequest();
        Element root = XmlParser.getRootElement(xmlStr);

        List<Element> apnInfoElements = root.elements("ApnInfo");
        List<UdmApnInfo> apnInfoList = new ArrayList();

        // header
        Header header = Utils.getAsyncHeader(root);
        apnServiceRequest.setHeader(header);

        for (Element apnInfoElement : apnInfoElements) {
            UdmApnInfo apnInfo = new UdmApnInfo();
            apnInfo.setSubsID(apnInfoElement.elementTextTrim("SubsId"));
            apnInfo.setMobNum(apnInfoElement.elementTextTrim("MobNum"));
            apnInfo.setProvinceID(apnInfoElement.elementTextTrim("ProvinceID"));

            List<Element> apnServiceInfoElements = apnInfoElement.elements("ApnServiceInfo");
            List<UdmApnServiceInfo> apnServiceInfoList = new ArrayList();
            for (Element apnServiceInfoElement : apnServiceInfoElements) {
                UdmApnServiceInfo apnServiceInfo = new UdmApnServiceInfo();
                apnServiceInfo.setApnName(apnServiceInfoElement.elementTextTrim("ApnName"));
                apnServiceInfo.setServiceCode(apnServiceInfoElement.elementTextTrim("ServiceCode"));
                apnServiceInfo.setServiceUsageState(apnServiceInfoElement.elementTextTrim("ServiceUsageState"));
                apnServiceInfoList.add(apnServiceInfo);
            }
            apnInfo.setApnServiceInfo(apnServiceInfoList);
            apnInfoList.add(apnInfo);
        }
        apnServiceRequest.setApnInfo(apnInfoList);
        return apnServiceRequest;
    }
}
