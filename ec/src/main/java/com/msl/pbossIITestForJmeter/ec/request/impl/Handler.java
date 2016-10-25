package com.msl.pbossIITestForJmeter.ec.request.impl;

import com.chinamobile.iot.udm.api.reverse.async.ReverseAsync;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaofeng on 2016/10/14.
 */
public class Handler {
    private static ExecutorService executorService;

    public static void start(Object request, String ip, int port, int threadNum, int msgNum) {
        NettyTransceiver transceiver = null;
        try {
            transceiver = new NettyTransceiver(new InetSocketAddress(ip, port),
                    new NioClientSocketChannelFactory(
                            Executors.newFixedThreadPool(1, new TransceiverThreadFactory("Avro " + NettyTransceiver.class.getSimpleName() + " Boss")),
                            Executors.newFixedThreadPool(120, new TransceiverThreadFactory("Avro " + NettyTransceiver.class.getSimpleName() + " I/O Worker"))));
            ReverseAsync.Callback client = SpecificRequestor.getClient(ReverseAsync.Callback.class, transceiver);
            executorService = Executors.newFixedThreadPool(threadNum);
            CountDownLatch latch = new CountDownLatch(msgNum);
            Service[] services = new Service[msgNum];
            for (int i = 0; i < msgNum; i++) {
                services[i] = new Service(request, latch, client);
            }
            for (int i = 0; i < msgNum; i++) {
                executorService.submit(services[i]);
            }
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            transceiver.close();
        }
    }
}
