package com.java.log;

import com.java.log.ws.WebServerImpl;
import com.java.log.ws.WsServer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class JavaLog {
    public static  WebServerImpl webServer;
    private static WsServer wsServer;
    public static void startLog(int port){
        try {
            webServer = new WebServerImpl(port);
            webServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        wsServer = new WsServer(8125);
        wsServer.start();
    }

    public static void stopLog(){
        if (webServer!=null){
            webServer.stop();
        }
        if (wsServer!=null){
            wsServer.release();
        }
    }
}
