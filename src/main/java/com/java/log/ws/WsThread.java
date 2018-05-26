package com.java.log.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WsThread extends Thread {
    private BufferedReader reader;
    public WsThread(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public void run() {
        super.run();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                WsPool.sendMessageToAll(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error "+(e==null?"":e.getMessage()));
        }
    }

    public void close(){
        interrupt();
        if (reader!=null){
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WsPool.closeAll();
    }
}
