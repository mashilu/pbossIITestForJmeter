package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.Response;
import com.chinamobile.iot.udm.api.reverse.async.UdmCardNumInfoRequest;
import com.chinamobile.iot.udm.api.reverse.async.ReverseAsync;
import com.chinamobile.iot.udm.api.reverse.async.UdmCardNumInfo;
import com.chinamobile.iot.udm.api.reverse.async.Header;

import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
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
 * Created by mashilu on 2016/8/21.
 * 补换卡
 */
public class CardNumInfoReqImpl extends AbstractAsyncReq {
    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmCardNumInfoRequest request = createCardNumInfoReq(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmCardNumInfoRequest createCardNumInfoReq(String xmlStr) {
        UdmCardNumInfoRequest cardNumInfoRequest = new UdmCardNumInfoRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        List<Element> cardNumInfoElemets = root.elements("CardNumInfo");
        List<UdmCardNumInfo> cardNumInfoLists = new ArrayList();

        // Header
        Header header = Utils.getAsyncHeader(root);
        cardNumInfoRequest.setHeader(header);

        for (Element e : cardNumInfoElemets) {
            UdmCardNumInfo cardNumInfo = new UdmCardNumInfo();
            cardNumInfo.setSubsID(e.elementTextTrim("SubsID"));
            cardNumInfo.setMobNum(e.elementTextTrim("MobNum"));
            cardNumInfo.setCardType(e.elementTextTrim("CardType"));
            cardNumInfo.setCardPhysicalType(e.elementTextTrim("CardPhysicalType"));
            cardNumInfo.setIMSI(e.elementTextTrim("IMSI"));
            cardNumInfo.setICCID(e.elementTextTrim("ICCID"));
            cardNumInfo.setOldIMSI(e.elementTextTrim("OldIMSI"));
            cardNumInfo.setOldICCID(e.elementTextTrim("OldICCID"));
            cardNumInfo.setProvinceID(e.elementTextTrim("ProvinceID"));

            cardNumInfoLists.add(cardNumInfo);
        }
        cardNumInfoRequest.setCardNumInfo(cardNumInfoLists);

        return cardNumInfoRequest;
    }
}
