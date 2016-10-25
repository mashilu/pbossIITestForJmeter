package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.sync.Header;
import com.chinamobile.iot.udm.api.reverse.sync.UdmOrderRequest;
import com.msl.pbossIITestForJmeter.ec.request.utils.Utils;
import com.msl.pbossIITestForJmeter.ec.request.utils.XmlParser;
import org.dom4j.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaofeng on 2016/7/27.
 * 订单查询
 */
public class QueryOrderInfoImpl {

    public void sendOrderSyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmOrderRequest request = createOrderRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmOrderRequest createOrderRequest(String xmlStr) {
        UdmOrderRequest orderRequest = new UdmOrderRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        // header
        Header header = Utils.getSyncHeader(root);
        orderRequest.setHeader(header);

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
