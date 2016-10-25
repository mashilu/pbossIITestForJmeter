package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.*;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/8/23.
 * 余额查询接口
 */
public class QryInfoRequestImpl {

    public void sendQryInfoRequest(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmQryInfoRequest request = createQryInfoRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
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
