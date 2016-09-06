package com.msl.pbossIITestForJmeter.ec.test;

import com.msl.pbossIITestForJmeter.ec.request.impl.QueryECProdInfoImpl;
import com.msl.pbossIITestForJmeter.ec.request.impl.QueryOrderInfoImpl;

/**
 * Created by mashilu on 2016/8/4.
 */
public class TestECProdRequest {
    public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ECProdRequest>\n" +
                "    <ProvinceID>2</ProvinceID>\n" +
                "    <QueryType>3</QueryType>\n" +
                "    <QueryValue>4</QueryValue>\n" +
                "</ECProdRequest>";

        QueryECProdInfoImpl ecProdInfo = new QueryECProdInfoImpl();
        ecProdInfo.sendECProdSyncReq("192.168.200.20", 8082, str);
    }
}
