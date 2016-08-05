package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.Response;
import com.chinamobile.iot.udm.api.reverse.async.ReverseAsync;
import com.chinamobile.iot.udm.api.reverse.async.UdmCancelPersonInfo;
import com.chinamobile.iot.udm.api.reverse.async.UdmCancelUserRequest;
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
 * 销户/批量销户
 */
public class CancelUserImpl  extends AbstractAsyncReq {

    public Response sendAsyncReq(String ip, int port, String jsonStr) {
        Response response = null;
        NettyTransceiver client = null;
        UdmCancelUserRequest request = createCancelUserRequest(jsonStr);
        System.out.print("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseAsync proxy = SpecificRequestor.getClient(ReverseAsync.class, client);
            response = proxy.applyCancelUser(request);
            System.out.println("Result: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return response;
    }

    public static UdmCancelUserRequest createCancelUserRequest(String xmlStr) {
        UdmCancelUserRequest cancelUserRequest = new UdmCancelUserRequest();
        Element root = XmlParser.getRootElement(xmlStr);
        List<Element> cancelPersonInfoElements = root.elements("CancelPersonInfo");
        List<UdmCancelPersonInfo> cancelPersonInfoList = new ArrayList();
        System.out.println("==============" + cancelPersonInfoElements.size());
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
