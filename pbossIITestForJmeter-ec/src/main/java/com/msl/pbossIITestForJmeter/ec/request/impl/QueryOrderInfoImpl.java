package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.UdmOrderResponse;
import com.chinamobile.iot.udm.api.reverse.sync.ReverseSync;
import com.chinamobile.iot.udm.api.reverse.sync.UdmOrderRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.dom4j.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaofeng on 2016/7/27.
 * 订单查询
 */
public class QueryOrderInfoImpl {

    public UdmOrderResponse sendOrderSyncReq(String ip, int port, String jsonStr) {
        UdmOrderResponse orderResponse = null;
        NettyTransceiver client = null;
        UdmOrderRequest request = createOrderRequest(jsonStr);
        System.out.print("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseSync proxy = SpecificRequestor.getClient(ReverseSync.class, client);
            orderResponse = proxy.queryOrder(request);
            System.out.println("Result: " + orderResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return orderResponse;
    }

    public static UdmOrderRequest createOrderRequest(String xmlStr) {
        UdmOrderRequest orderRequest = new UdmOrderRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        orderRequest.setProvinceID(root.elementTextTrim("ProvinceID"));
        List<Element> queryOprSeqElements = root.elements("QueryOprSeq");
        List<CharSequence> queryOprSeqList = new ArrayList();
        for (Element e : queryOprSeqElements) {
            queryOprSeqList.add(e.getText());
        }
        orderRequest.setQueryOprSeq(queryOprSeqList);

        return orderRequest;
    }
}
