package com.turkcell.microserviceutil.util;

import com.turkcell.microserviceutil.enumaration.LogKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class MDCUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MDCUtil.class);

    public MDCUtil() {
    }

    static {
        try {
            String hostIp = InetAddress.getLocalHost().getHostAddress();
            MDC.put(LogKey.HOST_IP.getValue(), hostIp);
            LOGGER.info("Set host-ip: {}", hostIp);
        } catch (UnknownHostException e) {
            LOGGER.error("An error occured while putting HOST_IP to MDC");
        }
    }

    public static void setUpMDC(HttpServletRequest request) {
        String remoteHost = request.getRemoteHost();
        String remoteAddress = request.getRemoteAddr();
        int remotePort = request.getRemotePort();
        String forwardedIp = request.getHeader("X-Forwarded-For");

        addRequestId(request);
        addForwardedIpIfExists(forwardedIp);
        addRemoteAddress(remoteAddress);
        addRemotePort(remotePort);
        addRemoteHost(remoteHost);
    }

    public static void tearDownMDC() {
        MDC.clear();
    }

    private static void addRemoteHost(String remoteHost) {
        MDC.put(LogKey.REMOTE_HOST.getValue(), remoteHost);
    }

    private static void addRemoteAddress(String remoteAddress) {
        MDC.put(LogKey.REMOTE_ADDRESS.getValue(), remoteAddress);
    }
    private static void addRemotePort(int remotePort) {
        MDC.put(LogKey.REMOTE_PORT.getValue(), String.valueOf(remotePort));
    }

    private static void addRequestId(HttpServletRequest request) {
        String uniqueId = request.getHeader(LogKey.REQUEST_ID.getValue());
        if(uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }
        MDC.put(LogKey.REQUEST_ID.getValue(), uniqueId);
    }

    public static void addUserId(String userId) {
        MDC.put(LogKey.USER_ID.getValue(), userId);
    }
    
    public static void addRetailerId(String retailerId) {
        MDC.put(LogKey.RETALER_ID.getValue(), retailerId);
    }

    private static void addForwardedIpIfExists(String forwardedIp) {
        if(forwardedIp != null && !forwardedIp.isEmpty()){
            forwardedIp = forwardedIp.split(",")[0];
            MDC.put(LogKey.FORWARDED_ADDRESS.getValue(), forwardedIp);
        }
    }
}
