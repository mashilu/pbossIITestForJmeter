package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.*;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.dom4j.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/8/23.
 * 余量查询
 */
public class LeftFlowRequestImpl {
    public Response sendLeftFlowRequest(String ip, int port, String jsonStr) {
        Response leftFlowResponse = null;
        NettyTransceiver client = null;
        UdmLeftFlowRequest request = createLeftFlowRequest(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseSync proxy = SpecificRequestor.getClient(ReverseSync.class, client);
            leftFlowResponse = proxy.queryLeftFlow(request);
            System.out.println("Result: " + leftFlowResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return leftFlowResponse;
    }

    private static UdmLeftFlowRequest createLeftFlowRequest(String xmlStr) {
        UdmLeftFlowRequest leftFlowRequest = new UdmLeftFlowRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        if (root == null)
            return null;

        List<Element> leftFlowInfoElements = root.elements("LeftFlowInfo");
        List<UdmLeftFlowInfo> leftFlowInfoList = new ArrayList();

        // header
        Header header = new Header();
        header.setApplicationId(root.element("Header").elementTextTrim("applicationId"));
        header.setOriginHost(root.element("Header").elementTextTrim("originHost"));
        leftFlowRequest.setHeader(header);

        for (Element leftFlowInfoElement : leftFlowInfoElements) {
            UdmLeftFlowInfo leftFlowInfo = new UdmLeftFlowInfo();
            leftFlowInfo.setOprCode(leftFlowInfoElement.elementTextTrim("OprCode"));
            leftFlowInfo.setSubsID(leftFlowInfoElement.elementTextTrim("SubsID"));
            leftFlowInfo.setMsisdn(leftFlowInfoElement.elementTextTrim("MSISDN"));
            leftFlowInfo.setApnName(leftFlowInfoElement.elementTextTrim("APNNAME"));
            leftFlowInfo.setQueryMonth(leftFlowInfoElement.elementTextTrim("QueryMonth"));
            leftFlowInfoList.add(leftFlowInfo);
        }
        leftFlowRequest.setUdmLeftFlowInfo(leftFlowInfoList);

        return leftFlowRequest;
    }
}
