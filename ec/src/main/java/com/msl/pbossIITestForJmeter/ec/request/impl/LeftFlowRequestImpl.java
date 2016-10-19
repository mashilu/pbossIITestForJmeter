package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.Header;
import com.chinamobile.iot.udm.api.reverse.sync.Response;
import com.chinamobile.iot.udm.api.reverse.sync.ReverseSync;
import com.chinamobile.iot.udm.api.reverse.sync.UdmLeftFlowInfo;
import com.chinamobile.iot.udm.api.reverse.sync.UdmLeftFlowRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
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

    public void sendLeftFlowRequest(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmLeftFlowRequest request = createLeftFlowRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    private static UdmLeftFlowRequest createLeftFlowRequest(String xmlStr) {
        UdmLeftFlowRequest leftFlowRequest = new UdmLeftFlowRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        if (root == null)
            return null;

        List<Element> leftFlowInfoElements = root.elements("LeftFlowInfo");
        List<UdmLeftFlowInfo> leftFlowInfoList = new ArrayList();

        // header
        Header header = Utils.getSyncHeader(root);
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
