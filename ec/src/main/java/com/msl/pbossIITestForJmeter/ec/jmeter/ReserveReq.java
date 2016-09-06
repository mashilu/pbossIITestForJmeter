package com.msl.pbossIITestForJmeter.ec.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * Created by mashilu on 2016/7/26.
 */
public class ReserveReq extends AbstractJavaSamplerClient {

    public SampleResult runTest(JavaSamplerContext arg0) {
        ReqUtils req = new ReqUtils(arg0);
        return req.run();
    }

    /**
     * JMeter界面中可手工输入参数,代码里面通过此方法获取
     */
    public Arguments getDefaultParameters() {
        return ReqUtils.setDefaultParameters();
    }
}
