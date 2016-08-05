package com.msl.pbossIITestForJmeter.ec.request;

import com.chinamobile.iot.udm.api.reverse.async.Response;

/**
 * Created by mashilu on 2016/7/27.
 */
public abstract class AbstractAsyncReq {

    public abstract Response sendAsyncReq(String ip, int port, String jsonStr);
}
