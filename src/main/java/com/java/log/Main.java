package com.java.log;


public class Main {

    public static void main(String[] args) {
        //使用方法：
        //1.指定端口，浏览器访问：http://IP:8126
        JavaLog.startLog(8126);

        //2.指定端口和一个日志文件路径
        //JavaLog.startLog(8126,"/home/java/gateway.log");

        //3.关闭 JavaLog.stopLog();



        //=========================测试，循环打印log=========================
        testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("其傻屌..");
                        Thread.sleep(1111);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        testThread.start();
    }
    public static Thread testThread;
}
