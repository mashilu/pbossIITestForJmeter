package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.*;
import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.dom4j.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * 激活/待激活
 */
public class UserActivateImpl extends AbstractAsyncReq {

    public Response sendAsyncReq(String ip, int port, String jsonStr) {
        Response response = null;
        NettyTransceiver client = null;
        UdmUserActivateRequest request = CreateUserActivateReq(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseAsync proxy = SpecificRequestor.getClient(ReverseAsync.class, client);
            response = proxy.applyUserActivate(request);
            System.out.println("Result: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return response;
    }

    public static UdmUserActivateRequest CreateUserActivateReq(String xmlStr) {
        UdmUserActivateRequest userActivateRequest = new  UdmUserActivateRequest();
        Element root= XmlParser.getRootElement(xmlStr);
        List<Element> userActivateInfoElements = root.elements("UserActivateInfo");
        List< UdmUserActivateInfo> userActivateInfoList = new ArrayList();

        // header
        Header header = new Header();
        header.setApplicationId(root.element("Header").elementTextTrim("applicationId"));
        header.setOriginHost(root.element("Header").elementTextTrim("originHost"));
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
