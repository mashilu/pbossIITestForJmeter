package com.msl.pbossIITestForJmeter.ec.jmeter;

import com.msl.pbossIITestForJmeter.ec.request.AbstractAsyncReq;
import com.msl.pbossIITestForJmeter.ec.request.impl.*;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mashilu on 2016/7/19.
 */
public class ReqUtils {

    private String serverIP = "";
    private String serverPort = "";
    private String jsonStr = "";
    private String threadNum = "";
    private String msgNum = "";
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
        // 补换卡
        put("CardNumInfoReq", "com.msl.pbossIITestForJmeter.ec.request.impl.CardNumInfoReqImpl");
        // 套餐变更申请
        put("PersonOrderInfoReq", "com.msl.pbossIITestForJmeter.ec.request.impl.OrderManageImpl");
        // 订单查询
        put("OrderRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.QueryOrderInfoImpl");
        // 订购查询
        put("ECProdRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.QueryECProdInfoImpl");
        // HSS用户状态查询
        put("QueryHSSUserStatusInfoReq", "com.msl.pbossIITestForJmeter.ec.request.impl.QueryHSSUserStatusInfoImpl");
        // 余额查询接口
        put("QryInfoRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.QryInfoRequestImpl");
        // 余量查询接口
        put("LeftFlowRequest", "com.msl.pbossIITestForJmeter.ec.request.impl.QryInfoRequestImpl.LeftFlowRequestImpl");
    }};

    // 同步请求类型
    private static String[] asyncReqType = {"UserActivateReq", "CancelUserRequest", "ApnServiceReq", "BasicServiceReq",
            "UserStatusChgReq", "SysNumInfoReq", "CardNumInfoReq", "PersonOrderInfoReq"};
    // 异步请求类型
    private static String[] getAsyncReqTyepe = {"OrderRequest", "ECProdRequest",
            "QueryHSSUserStatusInfoReq", "QryInfoRequest", "LeftFlowRequest"};

    public ReqUtils(JavaSamplerContext arg0) {
        this.arg = arg0;
    }

    public static Arguments setDefaultParameters() {

        Arguments args = new Arguments();
        args.addArgument("ip", "localhost");
        args.addArgument("port", "8888");
        args.addArgument("json", "");
        args.addArgument("thread", "1");
        args.addArgument("message", "1");

        return args;
    }


    public SampleResult run() {
        SampleResult sr = new SampleResult();

        serverIP = arg.getParameter("ip");
        serverPort = arg.getParameter("port");
        jsonStr = arg.getParameter("json");
        threadNum = arg.getParameter("thread");
        msgNum = arg.getParameter("message");


        if (serverIP == null || serverIP.trim().length() == 0 ||
                serverPort == null || serverPort.trim().length() == 0 ||
                jsonStr == null || jsonStr.trim().length() == 0 ||
                threadNum == null || threadNum.trim().length() == 0 ||
                msgNum == null || msgNum.trim().length() == 0) {
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
        System.out.println(getReqTypeMapClass.get(reqType));

        // 异步
        if (Arrays.asList(asyncReqType).contains(reqType)) {
            try {
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
                req.sendAsyncReq(serverIP, Integer.parseInt(serverPort), jsonStr,
                        Integer.parseInt(threadNum), Integer.parseInt(msgNum));
                //JSONObject jsonObject = JSONObject.fromObject(resp);
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        // 同步-订单查询
        else if (reqType.equals("OrderRequest")) {
            QueryOrderInfoImpl orderReq = new QueryOrderInfoImpl();
            try {
                sr.sampleStart();
                orderReq.sendOrderSyncReq(serverIP, Integer.parseInt(serverPort), jsonStr,
                        Integer.parseInt(threadNum), Integer.parseInt(msgNum));
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        // 同步-订购查询
        else if (reqType.equals("ECProdRequest")) {
            QueryECProdInfoImpl ecProdReq = new QueryECProdInfoImpl();
            try {
                sr.sampleStart();
                ecProdReq.sendECProdSyncReq(serverIP, Integer.parseInt(serverPort), jsonStr,
                        Integer.parseInt(threadNum), Integer.parseInt(msgNum));
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        // 同步-HSS用户状态查询
        else if (reqType.equals("QueryHSSUserStatusInfoReq")) {
            QueryHSSUserStatusInfoImpl queryHSSUserStatusInfoReq = new QueryHSSUserStatusInfoImpl();
            try {
                sr.sampleStart();
                queryHSSUserStatusInfoReq.sendQueryHSSUserStatusInfoReq(
                        serverIP,
                        Integer.parseInt(serverPort), jsonStr,
                        Integer.parseInt(threadNum), Integer.parseInt(msgNum));
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        // 同步-余量查询接口
        else if (reqType.equals("QryInfoRequest")) {
            QryInfoRequestImpl qryInfoRequest = new QryInfoRequestImpl();
            try {
                sr.sampleStart();
                qryInfoRequest.sendQryInfoRequest(serverIP, Integer.parseInt(serverPort), jsonStr,
                        Integer.parseInt(threadNum), Integer.parseInt(msgNum));
            } catch (Exception e) {
                sr.setSuccessful(false);
                e.printStackTrace();
            } finally {
                sr.sampleEnd();
            }
        }
        // 同步-余额查询接口
        else if (reqType.equals("LeftFlowRequest")) {
            LeftFlowRequestImpl leftFlowRequest = new LeftFlowRequestImpl();
            try {
                sr.sampleStart();
                leftFlowRequest.sendLeftFlowRequest(serverIP, Integer.parseInt(serverPort), jsonStr,
                        Integer.parseInt(threadNum), Integer.parseInt(msgNum));
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
    private String genReqType(String jsonStr) {

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
        else if (jsonStr.contains("CardNumInfoReq"))            // 补换卡
            return "CardNumInfoReq";
        else if (jsonStr.contains("PersonOrderInfoReq"))        // 套餐变更申请
            return "PersonOrderInfoReq";
        else if (jsonStr.contains("OrderRequest"))              // 订单查询
            return "OrderRequest";
        else if (jsonStr.contains("ECProdRequest"))             // 订购查询
            return "ECProdRequest";
        else if (jsonStr.contains("QueryHSSUserStatusInfoReq")) // HSS用户状态查询
            return "QueryHSSUserStatusInfoReq";
        else if (jsonStr.contains("QryInfoRequest"))            // 余额查询接口
            return "QryInfoRequest";
        else if (jsonStr.contains("LeftFlowRequest"))
            return "LeftFlowRequest";
        else
            return null;
    }
}
