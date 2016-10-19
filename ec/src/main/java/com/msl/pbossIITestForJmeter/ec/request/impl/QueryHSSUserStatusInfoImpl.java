package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.Header;
import com.chinamobile.iot.udm.api.reverse.sync.Response;
import com.chinamobile.iot.udm.api.reverse.sync.ReverseSync;
import com.chinamobile.iot.udm.api.reverse.sync.UdmQueryHSSUserStatusInfoRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
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

    public void sendQueryHSSUserStatusInfoReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmQueryHSSUserStatusInfoRequest request = createQueryHSSUserStatusInfoRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    private static UdmQueryHSSUserStatusInfoRequest createQueryHSSUserStatusInfoRequest(String xmlStr) {
        UdmQueryHSSUserStatusInfoRequest queryHSSUserStatusInfoRequest = new UdmQueryHSSUserStatusInfoRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        if (root == null)
            return null;

        // header
        Header header = Utils.getSyncHeader(root);
        queryHSSUserStatusInfoRequest.setHeader(header);

        queryHSSUserStatusInfoRequest.setSubsID(root.elementTextTrim("SubsID"));
        queryHSSUserStatusInfoRequest.setMobNum(root.elementTextTrim("MobNum"));

        return queryHSSUserStatusInfoRequest;
    }

}
