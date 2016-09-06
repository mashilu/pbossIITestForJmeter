package com.msl.pbossIITestForJmeter.ec.test;

import com.msl.pbossIITestForJmeter.ec.request.impl.BasicServiceImpl;

/**
 * Created by mashilu on 2016/8/2.
 */
public class TestBasicServiceImpl {
    public static void main(String[] args) {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n" +
                "<BasicServiceRequest>     \n" +
                "\t<BasicServiceInfo>         \n" +
                "\t\t<MobNum>  1 </MobNum>         \n" +
                "\t\t<SubsId>  2 </SubsId>         \n" +
                "\t\t<BasicServiceProdInfo>             \n" +
                "\t\t\t<OprType> 3 </OprType>             \n" +
                "\t\t\t<ProdID> 4  </ProdID>             \n" +
                "\t\t\t<ProdInstID> 5 </ProdInstID>         \n" +
                "\t\t</BasicServiceProdInfo>         \n" +
                "\t\t<BasicServiceProdInfo>             \n" +
                "\t\t\t<OprType> 31 </OprType>             \n" +
                "\t\t\t<ProdID> 41 </ProdID>             \n" +
                "\t\t\t<ProdInstID> 51 </ProdInstID>         \n" +
                "\t\t</BasicServiceProdInfo>     \n" +
                "\t</BasicServiceInfo> \n" +
                "</BasicServiceRequest>";


        BasicServiceImpl basicService = new BasicServiceImpl();
        basicService.sendAsyncReq("localhost", 65111, str);
    }
}
