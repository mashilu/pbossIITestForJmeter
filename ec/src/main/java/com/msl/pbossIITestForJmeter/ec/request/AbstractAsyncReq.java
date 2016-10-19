package com.msl.pbossIITestForJmeter.ec.request;

/**
 * Created by mashilu on 2016/7/27.
 */
public abstract class AbstractAsyncReq {

    public abstract void sendAsyncReq(String ip, int port, String jsonStr, int threadNum, int msgNum);
}
