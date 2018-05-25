package com.java.log.ws;

import com.java.log.util.FileUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.*;
import java.net.InetSocketAddress;

public class WsServer extends WebSocketServer {

    private Process process;
    private InputStream inputStream;
    private String logFile;
    private WsThread wsThread;
    private PrintStream ps;

    public WsServer(int port,String logFilePath) {
        super(new InetSocketAddress(port));
        if (logFilePath==null||logFilePath.equals("")){
            logFilePath = System.getProperty("java.io.tmpdir") + File.separator + System.getProperty("sun.java.command") + ".log";
        }
        this.logFile = logFilePath;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SaveInFile();
            }
        }).start();
    }

    public WsServer(int port) {
        this(port,System.getProperty("java.io.tmpdir") + File.separator + System.getProperty("sun.java.command") + ".log");
    }

    public void release(){
        if (ps!=null){
            ps.close();
        }
        if (process!=null){
            process.destroy();
        }
        try {
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (wsThread!=null){
            wsThread.close();
        }

        if (logFile!=null&&!logFile.equals("")){
            FileUtil.delete(logFile);
        }
    }

    public WsServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        userJoin(conn,conn.getRemoteSocketAddress().getHostName());
        // ws连接的时候触发的代码，onOpen中我们不做任何操作
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        //断开连接时候触发代码
        System.out.println(reason);
        userLeave(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
        if (null != message && message.startsWith("online")) {
            String userName = message.replaceFirst("online", message);//用户名
            userJoin(conn, userName);//用户加入
        } else if (null != message && message.startsWith("offline")) {
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //错误时候触发的代码
        System.out.println("on error");
        ex.printStackTrace();
    }

    /**
     * 去除掉失效的websocket链接
     *
     * @param conn
     */
    private void userLeave(WebSocket conn) {
        WsPool.removeUser(conn);
    }

    /**
     * 将websocket加入用户池
     *
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn, String userName) {
        System.out.println("## userJoin userName" + conn.getRemoteSocketAddress().toString());
        WsPool.addUser(userName, conn);
        if (wsThread == null){
            readLog();
        }
    }


    public void SaveInFile() {
        try {
            //文件生成路径
            ps = new PrintStream(logFile);
            System.setOut(ps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readLog() {
        try {
            // 执行tail -f命令
//            process = Runtime.getRuntime().exec("tail -f /var/log/syslog");
            process = Runtime.getRuntime().exec("tail -f " + logFile);
            inputStream = process.getInputStream();
            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            wsThread = new WsThread(inputStream);
            wsThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
