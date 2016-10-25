package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.Header;
import com.chinamobile.iot.udm.api.reverse.sync.UdmECProdRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;

/**
 * Created by zhaofeng on 2016/7/27.
 * 订购查询
 */
public class QueryECProdInfoImpl {

    public void sendECProdSyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmECProdRequest request = createECProdRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    private static UdmECProdRequest createECProdRequest(String xmlStr) {
        UdmECProdRequest ecProdRequest = new UdmECProdRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        if (root == null)
            return null;

        // header
        Header header = Utils.getSyncHeader(root);
        ecProdRequest.setHeader(header);

        ecProdRequest.setProvinceID(root.elementTextTrim("ProvinceID"));
        ecProdRequest.setQueryType(root.elementTextTrim("QueryType"));
        ecProdRequest.setQueryValue(root.elementTextTrim("QueryValue"));

        return ecProdRequest;
    }

}
