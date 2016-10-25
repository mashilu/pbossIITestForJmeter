package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.*;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaofeng on 2016/10/13.
 */
public class Service implements Callable<Response> {
    private Object                request;
    private ReverseAsync.Callback client;
    private CountDownLatch        latch;


    public Service(Object request, CountDownLatch latch, ReverseAsync.Callback client) {
        this.request = request;
        this.latch = latch;
        this.client = client;
    }

    @Override
    public Response call() throws Exception {
        Response response = null;
        try {
            response = doQuery(client, request);
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Response doQuery(ReverseAsync proxy, Object request) throws Exception {
        if (request instanceof UdmApnServiceRequest) {
            return proxy.applyApnService((UdmApnServiceRequest) request);
        }
        if (request instanceof UdmBasicServiceRequest) {
            return proxy.applyBasicService((UdmBasicServiceRequest) request);
        }
        if (request instanceof UdmCancelUserRequest) {
            return proxy.applyCancelUser((UdmCancelUserRequest) request);
        }
        if (request instanceof UdmCardNumInfoRequest) {
            return proxy.applyCardNumInfo((UdmCardNumInfoRequest) request);
        }
        if (request instanceof UdmOrderManageRequest) {
            return proxy.applyOrderManage((UdmOrderManageRequest) request);
        }
        if (request instanceof UdmSysNumInfoRequest) {
            return proxy.applySysNumInfo((UdmSysNumInfoRequest) request);
        }
        if (request instanceof UdmUserActivateRequest) {
            return proxy.applyUserActivate((UdmUserActivateRequest) request);
        }
        if (request instanceof UdmUserStatusChgRequest) {
            return proxy.applyUserStatusChg((UdmUserStatusChgRequest) request);
        }
        return null;
    }
}
