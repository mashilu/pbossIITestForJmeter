package com.msl.pbossIITestForJmeter.ec.test;

import com.msl.pbossIITestForJmeter.ec.request.impl.QueryOrderInfoImpl;

/**
 * Created by mashilu on 2016/8/4.
 */
public class TestOrderRequest {
    public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<OrderRequest>\n" +
                "    <ProvinceID>1</ProvinceID>\n" +
                "    <QueryOprSeq>343434</QueryOprSeq>\n" +
                "</OrderRequest>";

        QueryOrderInfoImpl orderInfo = new QueryOrderInfoImpl();
        orderInfo.sendOrderSyncReq("192.168.200.20", 8082, str, 1, 1);
    }
}
