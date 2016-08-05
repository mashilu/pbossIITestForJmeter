package com.msl.pbossIITestForJmeter.ec.jmeter;

import com.chinamobile.iot.udm.api.reverse.async.Response;
import com.chinamobile.iot.udm.api.reverse.sync.UdmECProductResponse;
import com.chinamobile.iot.udm.api.reverse.sync.UdmOrderResponse;
import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.impl.QueryECProdInfoImpl;
import com.msl.pbossIITestForJmeter.ec.request.impl.QueryOrderInfoImpl;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mashilu on 2016/7/19.
 */
public class ReqUtils {

    private String serverIP = "";
    private String serverPort = "";
    private String jsonStr = "";
    private AbstractAsyncReq req = null;
    private JavaSamplerContext arg = null;
    private String reqType = "";
    private String resultData = "";   // 反馈到jmeter界面的结果数据

    private static Map<String, String> getReqTypeMapClass = new HashMap<String, String>(){{
        // 激活/待激活
        put("UserActivateReq", "com.msl.pbossIITestForJmeter.ec.request.impl.UserActivateImpl");
        // 号码销户
        put("CancelUserRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.CancelUserImpl");
        // APN开关管理
        put("ApnServiceReq", "com.msl.pbossIITestForJmeter.ec.request.impl.ApnServiceImpl");
        // 基础服务暂停恢复
        put("BasicServiceReq", "com.msl.pbossIITestForJmeter.ec.request.impl.BasicServiceImpl");
        // HFC黑白名单设置
        put("SysNumInfoReq", "com.msl.pbossIITestForJmeter.ec.request.impl.SysNumInfoImpl");
        // 停复机申请
        put("UserStatusChgReq", "com.msl.pbossIITestForJmeter.ec.request.impl.UserStatusChgImpl");
        // 套餐变更申请
        put("PersonOrderInfoReq", "com.msl.pbossIITestForJmeter.ec.request.impl.OrderManageImpl");
        // 订单查询
        put("OrderRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.QueryOrderInfoImpl");
        // 订购查询
        put("ECProdRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.QueryECProdInfoImpl");
    }};

    public ReqUtils(JavaSamplerContext arg0) {
        this.arg = arg0;
    }

    public static Arguments setDefaultParameters() {

        Arguments args = new Arguments();
        args.addArgument("ip", "localhost");
        args.addArgument("port", "8888");
        args.addArgument("json", "");

        return args;
    }


    public SampleResult run() {
        SampleResult sr = new SampleResult();

        serverIP = arg.getParameter("ip");
        serverPort = arg.getParameter("port");
        jsonStr = arg.getParameter("json");

        if (serverIP == null || serverIP.trim().length() == 0 ||
                serverPort == null || serverPort.trim().length() == 0 ||
                jsonStr == null || jsonStr.trim().length() == 0) {
            resultData = "parameter error";
            sr.setResponseData(resultData, null);
            sr.setDataType(SampleResult.TEXT);
            sr.setSuccessful(false);
            return sr;
        }

        // 获取请求类型
        reqType = genReqType(jsonStr);
        System.out.println(reqType);
        if (!getReqTypeMapClass.keySet().contains(reqType)) {
            System.out.println("Request Type is wrong: " + reqType);
            resultData = "parameter error";
            sr.setResponseData(resultData, null);
            sr.setDataType(SampleResult.TEXT);
            sr.setSuccessful(false);
            return sr;
        }

        if (reqType != "OrderRequest" && reqType != "ECProdRequest") {
            try {
                System.out.println(getReqTypeMapClass.get(reqType));
                req = (AbstractAsyncReq) Class.forName(getReqTypeMapClass.get(reqType)).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                sr.sampleStart();
                Response resp = req.sendAsyncReq(serverIP, Integer.parseInt(serverPort), jsonStr);
                //JSONObject jsonObject = JSONObject.fromObject(resp);
                resultData = resp.toString();
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        else if (reqType == "OrderRequest") {
            System.out.println(getReqTypeMapClass.get(reqType));
            QueryOrderInfoImpl orderReq = new QueryOrderInfoImpl();
            try {
                sr.sampleStart();
                UdmOrderResponse resp = orderReq.sendOrderSyncReq(serverIP, Integer.parseInt(serverPort), jsonStr);
                resultData = resp.toString();
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        else if (reqType == "ECProdRequest") {
            System.out.println(getReqTypeMapClass.get(reqType));
            QueryECProdInfoImpl ecProdReq = new QueryECProdInfoImpl();
            try {
                sr.sampleStart();
                UdmECProductResponse resp = ecProdReq.sendECProdSyncReq(serverIP, Integer.parseInt(serverPort), jsonStr);
                resultData = resp.toString();
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }

        if (resultData != null && resultData.length() > 0) {
            sr.setResponseData(resultData, null);
            sr.setDataType(SampleResult.TEXT);
        }
        sr.setSuccessful(true);



        return sr;
    }

    // 根据json串获取对应接口
    public String genReqType(String jsonStr) {

        if (jsonStr.contains("UserActivateReq"))                // 激活/待激活
            return "UserActivateReq";
        else if (jsonStr.contains("CancelUserReq"))             // 号码销户
            return "CancelUserRequest";
        else if (jsonStr.contains("ApnServiceReq"))             // APN开关管理
            return "ApnServiceReq";
        else if (jsonStr.contains("BasicServiceReq"))           // 基础服务暂停恢复
            return "BasicServiceReq";
        else if (jsonStr.contains("SysNumInfoReq"))             // HFC黑白名单设置
            return "SysNumInfoReq";
        else if (jsonStr.contains("UserStatusChgReq"))          // 停复机申请
            return "UserStatusChgReq";
        else if (jsonStr.contains("PersonOrderInfoReq"))        // 套餐变更申请
            return "PersonOrderInfoReq";
        else if (jsonStr.contains("OrderRequest"))              // 订单查询
            return "OrderRequest";
        else if (jsonStr.contains("ECProdRequest"))             // 订购查询
            return "ECProdRequest";
        else
            return null;
    }
}
