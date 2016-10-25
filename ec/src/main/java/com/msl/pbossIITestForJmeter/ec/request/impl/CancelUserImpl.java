package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.UdmCancelPersonInfo;
import com.chinamobile.iot.udm.api.reverse.async.UdmCancelUserRequest;
import com.chinamobile.iot.udm.api.reverse.async.Header;

import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * 销户/批量销户
 */
public class CancelUserImpl  extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmCancelUserRequest request = createCancelUserRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmCancelUserRequest createCancelUserRequest(String xmlStr) {
        UdmCancelUserRequest cancelUserRequest = new UdmCancelUserRequest();
        Element root = XmlParser.getRootElement(xmlStr);

        List<Element> cancelPersonInfoElements = root.elements("CancelPersonInfo");
        List<UdmCancelPersonInfo> cancelPersonInfoList = new ArrayList();

        // Header
        Header header = Utils.getAsyncHeader(root);
        cancelUserRequest.setHeader(header);

        for (Element cancelPersonInfoElement : cancelPersonInfoElements) {
            UdmCancelPersonInfo cancelPersonInfo = new UdmCancelPersonInfo();
            cancelPersonInfo.setSubsID(cancelPersonInfoElement.elementTextTrim("SubsID"));
            cancelPersonInfo.setMobNum(cancelPersonInfoElement.elementTextTrim("MobNum"));
            cancelPersonInfo.setProvinceID(cancelPersonInfoElement.elementTextTrim("ProvinceID"));
            cancelPersonInfoList.add(cancelPersonInfo);
        }
        cancelUserRequest.setPersonInfo(cancelPersonInfoList);

        return cancelUserRequest;

    }
}
