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
 * 激活/待激活
 */
public class UserActivateImpl extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmUserActivateRequest request = CreateUserActivateReq(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmUserActivateRequest CreateUserActivateReq(String xmlStr) {
        UdmUserActivateRequest userActivateRequest = new  UdmUserActivateRequest();
        Element root= XmlParser.getRootElement(xmlStr);
        List<Element> userActivateInfoElements = root.elements("UserActivateInfo");
        List< UdmUserActivateInfo> userActivateInfoList = new ArrayList();

        // header
        Header header = Utils.getAsyncHeader(root);
        userActivateRequest.setHeader(header);

        for (Element userActivateInfoElement : userActivateInfoElements) {
            UdmUserActivateInfo userActivateInfo = new  UdmUserActivateInfo();
            userActivateInfo.setSubsID(userActivateInfoElement.elementTextTrim("SubsID"));
            userActivateInfo.setMobNum(userActivateInfoElement.elementTextTrim("MobNum"));
            userActivateInfo.setUserStatus(userActivateInfoElement.elementTextTrim("UserStatus"));
            userActivateInfo.setStatusOprTime(userActivateInfoElement.elementTextTrim("StatusOprTime"));
            userActivateInfo.setProvinceID(userActivateInfoElement.elementTextTrim("ProvinceID"));
            userActivateInfoList.add(userActivateInfo);
        }
        userActivateRequest.setUserActivateInfo(userActivateInfoList);

        return userActivateRequest;
    }
}
