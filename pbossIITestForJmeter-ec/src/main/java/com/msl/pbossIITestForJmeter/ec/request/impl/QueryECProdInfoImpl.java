package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.ReverseSync;
import com.chinamobile.iot.udm.api.reverse.sync.UdmECProdRequest;
import com.chinamobile.iot.udm.api.reverse.sync.UdmECProductResponse;
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

    public UdmECProductResponse sendECProdSyncReq(String ip, int port, String jsonStr) {
        UdmECProductResponse orderResponse = null;
        NettyTransceiver client = null;
        UdmECProdRequest request = createECProdRequest(jsonStr);
        System.out.print("Request:" + request);
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

    public static UdmECProdRequest createECProdRequest(String xmlStr) {
        UdmECProdRequest ecProdRequest = new UdmECProdRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        ecProdRequest.setProvinceID(root.elementTextTrim("ProvinceID"));
        ecProdRequest.setQueryType(root.elementTextTrim("QueryType"));
        ecProdRequest.setQueryValue(root.elementTextTrim("QueryValue"));

        return ecProdRequest;
    }

}
