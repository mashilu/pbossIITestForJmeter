package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.Header;
import com.chinamobile.iot.udm.api.reverse.sync.Response;
import com.chinamobile.iot.udm.api.reverse.sync.ReverseSync;
import com.chinamobile.iot.udm.api.reverse.sync.UdmECProdRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.dom4j.Element;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by zhaofeng on 2016/7/27.
 * 订购查询
 */
public class QueryECProdInfoImpl {

    public Response sendECProdSyncReq(String ip, int port, String jsonStr) {
        Response orderResponse = null;
        NettyTransceiver client = null;
        UdmECProdRequest request = createECProdRequest(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseSync proxy = SpecificRequestor.getClient(ReverseSync.class, client);
            orderResponse = proxy.queryECProd(request);
            System.out.println("Result: " + orderResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return orderResponse;
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
