package com.java.log.ws;

import com.java.log.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

public class WebServerImpl extends NanoHTTPD {
    public WebServerImpl(int port) {
        super(port);
    }
    public WebServerImpl(String hostname, int port) {
        super(hostname, port);
    }
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        return processFile(uri);
    }

    private NanoHTTPD.Response processFile(String uri) {
        String file_name = uri.substring(1);
        // 默认的页面名称设定为index.html
        if (file_name.equalsIgnoreCase("")) {
            file_name = "index.html";
        }else{
            try {
                file_name = file_name.replaceAll("\\\\", "/");
            }catch (Exception e){}
        }

        byte[] data= null;
        try {
            InputStream in = WebServerImpl.class.getResourceAsStream("/webapp/" + file_name);
            data = FileUtil.toByteArray(in);//NetUtil.loadContent("index.html", mAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("====WebServerImpl==== "+file_name);
        if (data == null){
            return null;
        }
        return new NanoHTTPD.Response(new String(data));
    }
}
