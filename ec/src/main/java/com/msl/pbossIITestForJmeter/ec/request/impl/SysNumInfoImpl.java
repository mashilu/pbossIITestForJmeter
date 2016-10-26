package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.*;
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
 * Created by mashilu on 2016/7/27.
 * HFC黑白名单设置
 */
public class SysNumInfoImpl extends AbstractAsyncReq {

    public Response sendAsyncReq(String ip, int port, String jsonStr) {
        Response response = null;
        NettyTransceiver client = null;
        UdmSysNumInfoRequest request = createSysNumInfoRequest(jsonStr);
        System.out.println("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseAsync proxy = SpecificRequestor.getClient(ReverseAsync.class, client);
            response = proxy.applySysNumInfo(request);
            System.out.println("Result: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return response;
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
