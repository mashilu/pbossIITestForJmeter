package com.msl.pbossIITestForJmeter.ec.test;

import com.msl.pbossIITestForJmeter.ec.request.impl.ApnServiceImpl;

/**
 * Created by mashilu on 2016/8/1.
 */
public class TestApnServiceImpl {

    public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" +
                "<ApnServiceRequest>     \n" +
                "\t<ApnInfo>         \n" +
                "\t\t<MobNum>1</MobNum>         \n" +
                "\t\t<ProvinceID>2</ProvinceID>         \n" +
                "\t\t<SubsId>3</SubsId>         \n" +
                "\t\t<ApnServiceInfo>             \n" +
                "\t\t\t<ApnName>4</ApnName>             \n" +
                "\t\t\t<ServiceCode>5</ServiceCode>             \n" +
                "\t\t\t<ServiceUsageState>1</ServiceUsageState>         \n" +
                "\t\t</ApnServiceInfo>     \n" +
                "\t</ApnInfo> \n" +
                "</ApnServiceRequest>";

//        UserStatusChgImpl userStatusChg = new UserStatusChgImpl();
//        userStatusChg.sendAsyncReq("localhost", 65111, str);

//        SysNumInfoImpl sysNumInfo = new SysNumInfoImpl();
//        sysNumInfo.sendAsyncReq("localhost", 65111, str);

//        CancelUserImpl cancelUser = new CancelUserImpl();
//        cancelUser.sendAsyncReq("localhost", 65111, str);

        ApnServiceImpl apnService = new ApnServiceImpl();
        apnService.sendAsyncReq("192.168.200.20", 8081, str);
    }
}
