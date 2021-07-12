package com.ekuy.cinema.api.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SendMsgUtil {

    /**
     * 发送消息 text/html;charset=utf-8
     * @param response
     * @param str
     * @throws Exception
     */
    public static void sendMessage(HttpServletResponse response, String str, String... contentType) {
        try {
            response.setContentType(contentType.length > 0 ? contentType[0] : "text/html; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(str);
            writer.close();
            response.flushBuffer();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将某个对象转换成json格式并发送到客户端
     * @param response
     * @param obj
     * @throws Exception
     */
    public static void sendJsonMessage(HttpServletResponse response, Object obj) {
        sendMessage(response, JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat), "application/json; charset=utf-8");
    }
}