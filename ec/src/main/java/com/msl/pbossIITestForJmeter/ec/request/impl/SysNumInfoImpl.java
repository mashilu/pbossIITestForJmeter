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
 * HFC黑白名单设置
 */
public class SysNumInfoImpl extends AbstractAsyncReq {

    public void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum) {
        UdmSysNumInfoRequest request = createSysNumInfoRequest(jsonStr);
        System.out.println("Request:" + request);
        Handler.start(request, ip, port, threadNum, msgNum);
    }

    public static UdmSysNumInfoRequest createSysNumInfoRequest(String xmlStr) {
        UdmSysNumInfoRequest sysNumInfoRequest = new UdmSysNumInfoRequest();
        Element root= XmlParser.getRootElement(xmlStr);

        List<Element> sysNumInfoElements = root.elements("SysNumInfo");
        List<UdmSysNumInfo> sysNumInfoList = new ArrayList();

        // header
        Header header = Utils.getAsyncHeader(root);
        sysNumInfoRequest.setHeader(header);

        for (Element sysNumInfoElement : sysNumInfoElements) {
            UdmSysNumInfo sysNumInfo = new UdmSysNumInfo();

            sysNumInfo.setGroupCode(sysNumInfoElement.elementTextTrim("GroupCode"));
            sysNumInfo.setProvinceID(sysNumInfoElement.elementTextTrim("ProvinceID"));

            List<Element> numInfoElements = sysNumInfoElement.elements("NumInfo");
            List<UdmNumInfo> numInfoList = new ArrayList();
            for (Element numInfoElement : numInfoElements) {
                UdmNumInfo numInfo = new UdmNumInfo();
                numInfo.setNumType(numInfoElement.elementTextTrim("NumType"));
                numInfo.setOperType(numInfoElement.elementTextTrim("OperType"));
                List<Element> numValueElements = numInfoElement.elements("NumValue");
                List<CharSequence> numValueList = new ArrayList();
                for (Element numValueElement : numValueElements) {
                    String numValue = numValueElement.getText();
                    numValueList.add(numValue);
                }
                numInfo.setNumValue(numValueList);
                numInfoList.add(numInfo);
            }
            sysNumInfo.setNumInfo(numInfoList);

            sysNumInfoList.add(sysNumInfo);
        }

        sysNumInfoRequest.setSysNumInfo(sysNumInfoList);

        return sysNumInfoRequest;


    }
}
