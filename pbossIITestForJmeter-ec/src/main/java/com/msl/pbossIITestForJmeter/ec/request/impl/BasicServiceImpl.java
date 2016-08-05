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
 * 基础服务暂停恢复
 */
public class BasicServiceImpl extends AbstractAsyncReq {

    public Response sendAsyncReq(String ip, int port, String jsonStr) {
        Response response = null;
        NettyTransceiver client = null;
        UdmBasicServiceRequest request = CreateBasicServiceRequest(jsonStr);
        System.out.print("Request:" + request);
        try {
            client = new NettyTransceiver(new InetSocketAddress(ip, port));
            ReverseAsync proxy = SpecificRequestor.getClient(ReverseAsync.class, client);
            response = proxy.applyBasicService(request);
            System.out.println("Result: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.close(true);
        }

        return response;
    }

    public static UdmBasicServiceRequest CreateBasicServiceRequest(String xmlStr) {
        UdmBasicServiceRequest basicServiceRequest=new  UdmBasicServiceRequest();
        Element root= XmlParser.getRootElement(xmlStr);
        List<Element> basicServiceInfoElements = root.elements("BasicServiceInfo");
        List< UdmBasicServiceInfo> basicServiceInfoList = new ArrayList();
        for (Element basicServiceInfoElement : basicServiceInfoElements) {
            UdmBasicServiceInfo basicServiceInfo = new  UdmBasicServiceInfo();

            basicServiceInfo.setSubsID(basicServiceInfoElement.elementTextTrim("SubsId"));
            basicServiceInfo.setMobNum(basicServiceInfoElement.elementTextTrim("MobNum"));

            List<Element> basicServiceProdInfoElements = basicServiceInfoElement.elements("BasicServiceProdInfo");
            List<UdmBasicServiceProdInfo> basicServiceProdInfoList = new ArrayList();
            for (Element basicServiceProdInfoElement : basicServiceProdInfoElements) {
                UdmBasicServiceProdInfo basicServiceProdInfo = new UdmBasicServiceProdInfo();
                basicServiceProdInfo.setProdID(basicServiceProdInfoElement.elementTextTrim("ProdID"));
                basicServiceProdInfo.setProdInstID(basicServiceProdInfoElement.elementTextTrim("ProdInstID"));
                basicServiceProdInfo.setOprType(basicServiceProdInfoElement.elementTextTrim("OprType"));
                basicServiceProdInfoList.add(basicServiceProdInfo);
            }
            basicServiceInfo.setProdInfo(basicServiceProdInfoList);

            basicServiceInfoList.add(basicServiceInfo);
        }
        basicServiceRequest.setBasicServiceInfo(basicServiceInfoList);

        return basicServiceRequest;
    }
}
