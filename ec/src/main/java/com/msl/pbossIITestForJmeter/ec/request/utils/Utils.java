package com.msl.pbossIITestForJmeter.ec.request.utils;
import org.dom4j.Element;

/**
 * Created by mashilu on 2016/10/26.
 */

public class Utils {
    public static com.chinamobile.iot.udm.api.reverse.async.Header getAsyncHeader(Element root) {
        com.chinamobile.iot.udm.api.reverse.async.Header header = new com.chinamobile.iot.udm.api.reverse.async.Header();
        header.setApplicationId(root.element("Header").elementTextTrim("applicationId"));
        header.setOriginHost(root.element("Header").elementTextTrim("originHost"));
        Boolean enableMonitor = Boolean.valueOf(root.element("Header").elementTextTrim("enableMonitor"));
        header.setEnableMonitor(enableMonitor);
        header.setMonitorType(root.element("Header").elementTextTrim("monitorType"));
        header.setMonitorKey(root.element("Header").elementTextTrim("monitorKey"));

        return header;
    }

    public static com.chinamobile.iot.udm.api.reverse.sync.Header getSyncHeader(Element root) {
        com.chinamobile.iot.udm.api.reverse.sync.Header header = new com.chinamobile.iot.udm.api.reverse.sync.Header();
        header.setApplicationId(root.element("Header").elementTextTrim("applicationId"));
        header.setOriginHost(root.element("Header").elementTextTrim("originHost"));
        Boolean enableMonitor = Boolean.valueOf(root.element("Header").elementTextTrim("enableMonitor"));
        header.setEnableMonitor(enableMonitor);
        header.setMonitorType(root.element("Header").elementTextTrim("monitorType"));
        header.setMonitorKey(root.element("Header").elementTextTrim("monitorKey"));

        return header;
    }
}