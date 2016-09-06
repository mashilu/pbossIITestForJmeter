package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.Header;
import com.chinamobile.iot.udm.api.reverse.sync.Response;
import com.chinamobile.iot.udm.api.reverse.sync.ReverseSync;
import com.chinamobile.iot.udm.api.reverse.sync.UdmQueryHSSUserStatusInfoRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.dom4j.Element;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by mashilu on 2016/8/17.
 */
public class QueryHSSUserStatusInfoImpl {

    public Response sendQueryHSSUserStatusInfoReq(String ip, int port, String jsonStr) {
        Response orderResponse = null;
        NettyTransceiver client = null;
        UdmQueryHSSUserStatusInfoRequest request = createQueryHSSUserStatusInfoRequest(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseSync proxy = SpecificRequestor.getClient(ReverseSync.class, client);
            orderResponse = proxy.queryHSSUserStatusInfo(request);
            System.out.println("Result: " + orderResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return orderResponse;
    }

    private static UdmQueryHSSUserStatusInfoRequest createQueryHSSUserStatusInfoRequest(String xmlStr) {
        UdmQueryHSSUserStatusInfoRequest queryHSSUserStatusInfoRequest = new UdmQueryHSSUserStatusInfoRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        if (root == null)
            return null;

        // header
        Header header = new Header();
        header.setApplicationId(root.element("Header").elementTextTrim("applicationId"));
        header.setOriginHost(root.element("Header").elementTextTrim("originHost"));
        queryHSSUserStatusInfoRequest.setHeader(header);

        queryHSSUserStatusInfoRequest.setSubsID(root.elementTextTrim("SubsID"));
        queryHSSUserStatusInfoRequest.setMobNum(root.elementTextTrim("MobNum"));

        return queryHSSUserStatusInfoRequest;
    }

}
