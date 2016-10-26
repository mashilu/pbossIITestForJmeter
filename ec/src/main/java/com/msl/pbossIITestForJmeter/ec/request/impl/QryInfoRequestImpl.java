package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.*;
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
 * 余额查询接口
 */
public class QryInfoRequestImpl {
    public Response sendQryInfoRequest(String ip, int port, String jsonStr) {
        Response qryInfoResponse = null;
        NettyTransceiver client = null;
        UdmQryInfoRequest request = createQryInfoRequest(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseSync proxy = SpecificRequestor.getClient(ReverseSync.class, client);
            qryInfoResponse = proxy.queryAccBlance(request);
            System.out.println("Result: " + qryInfoResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return qryInfoResponse;
    }

    private static UdmQryInfoRequest createQryInfoRequest(String xmlStr) {
        UdmQryInfoRequest qryInfoRequest = new UdmQryInfoRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        if (root == null)
            return null;

        List<Element> qryInfoReqElements = root.elements("QryInfoReq");
        List<UdmQryInfoReq> qryInfoReqList = new ArrayList();

        // header
        Header header = Utils.getSyncHeader(root);
        qryInfoRequest.setHeader(header);

        for (Element qryInfoReqElement : qryInfoReqElements) {
            UdmQryInfoReq qryInfo = new UdmQryInfoReq();
            qryInfo.setIDItemRange(qryInfoReqElement.elementTextTrim("IDItemRange"));
            qryInfo.setIDType(qryInfoReqElement.elementTextTrim("IDType"));
            qryInfo.setSessionID(qryInfoReqElement.elementTextTrim("SessionID"));
            qryInfoReqList.add(qryInfo);
        }
        qryInfoRequest.setUdmQryInfoReq(qryInfoReqList);

        return qryInfoRequest;
    }
}
