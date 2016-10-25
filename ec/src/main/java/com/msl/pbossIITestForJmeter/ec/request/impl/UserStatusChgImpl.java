package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.*;
import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mashilu on 2016/7/27.
 * 停复机申请
 */
public class UserStatusChgImpl extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmUserStatusChgRequest request = createUserStatusChgRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmUserStatusChgRequest createUserStatusChgRequest(String str) {
        UdmUserStatusChgRequest userStatusChgReq = new UdmUserStatusChgRequest();
        Element root= XmlParser.getRootElement(str);

        List<Element> userStatusInfoElements=root.elements("UserStatusInfo");
        List<UdmUserStatusInfo> userStatusInfoList = new ArrayList();

        // header
        Header header = Utils.getAsyncHeader(root);
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
