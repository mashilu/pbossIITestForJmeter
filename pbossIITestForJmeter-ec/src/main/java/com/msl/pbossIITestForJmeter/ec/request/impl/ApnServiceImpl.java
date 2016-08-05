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
 * APN开关管理
 */
public class ApnServiceImpl extends AbstractAsyncReq {

    public Response sendAsyncReq(String ip, int port, String jsonStr) {
        Response response = null;
        NettyTransceiver client = null;
        UdmApnServiceRequest request = CreateApnServiceRequest(jsonStr);
        System.out.print("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseAsync proxy = SpecificRequestor.getClient(ReverseAsync.class, client);
            response = proxy.applyApnService(request);
            System.out.println("Result: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return response;
    }

    public static UdmApnServiceRequest CreateApnServiceRequest(String xmlStr) {
        UdmApnServiceRequest apnServiceRequest = new UdmApnServiceRequest();
        Element root = XmlParser.getRootElement(xmlStr);
        List<Element> apnInfoElements = root.elements("ApnInfo");
        System.out.println("-----------" + apnInfoElements.size());
        List<UdmApnInfo> apnInfoList = new ArrayList();
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
