package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.*;
import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.dom4j.Element;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * 停复机申请
 */
public class UserStatusChgImpl extends AbstractAsyncReq {

    public Response sendAsyncReq(String ip, int port, String jsonStr) {
        Response response = null;
        NettyTransceiver client = null;
        UdmUserStatusChgRequest request = createUserStatusChgRequest(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseAsync proxy = SpecificRequestor.getClient(ReverseAsync.class, client);
            response = proxy.applyUserStatusChg(request);
            System.out.println("Result: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return response;
    }

    public static UdmUserStatusChgRequest createUserStatusChgRequest(String str) {
        UdmUserStatusChgRequest userStatusChgReq = new UdmUserStatusChgRequest();
        Element root= XmlParser.getRootElement(str);

        List<Element> userStatusInfoElements=root.elements("UserStatusInfo");
        List<UdmUserStatusInfo> userStatusInfoList = new ArrayList();

        // header
        Header header = new Header();
        header.setApplicationId(root.element("Header").elementTextTrim("applicationId"));
        header.setOriginHost(root.element("Header").elementTextTrim("originHost"));
        userStatusChgReq.setHeader(header);

        for(Element userStatusInfoElement: userStatusInfoElements)
        {
            UdmUserStatusInfo userStatusInfo = new UdmUserStatusInfo();
            userStatusInfo.setSubsID(userStatusInfoElement.elementTextTrim("SubsID"));
            userStatusInfo.setProvinceID(userStatusInfoElement.elementTextTrim("ProvinceID"));
            userStatusInfo.setMobNum(userStatusInfoElement.elementTextTrim("MobNum"));
            userStatusInfo.setStatusOprTime(userStatusInfoElement.elementTextTrim("StatusOprTime"));
            userStatusInfo.setUserStatus(userStatusInfoElement.elementTextTrim("UserStatus"));
            userStatusInfoList.add(userStatusInfo);

        }
        userStatusChgReq.setUserStatusInfo(userStatusInfoList);
        return userStatusChgReq;
    }
}
