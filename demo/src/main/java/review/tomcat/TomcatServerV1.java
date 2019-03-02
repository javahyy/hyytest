package review.tomcat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServerV1 {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws Exception {

        //加载项目信息


        // Socket 网络编程 - BIO
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("tomcat服务启动成功");
        while (!serverSocket.isClosed()) {
            Socket request = serverSocket.accept();//获取新连接
            threadPool.execute(() -> {
                try {
                    //接收数据，打印
                    InputStream inputStream = request.getInputStream();
                    System.out.println("收到请求：");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String msg = null;
                    while ((msg = reader.readLine()) != null) {
                        if (msg.length() == 0) {
                            break;
                        }
                        System.out.println("msg");
                    }

                    // 我应该要去执行 开发者写的业务代码
                    // 根据请求的地址，去找到对应的项目，然后找到对应的servlet，最终调用

                    // 项目解析

                    // 又那些servlet

                    // url 和 servlet




                    System.out.println("===============================end");

                    //响应结果 200 符合HTTP协议
                    OutputStream outputStream = request.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n".getBytes());
                    outputStream.write("Hello World".getBytes());
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {//关闭资源
                    try {
                        request.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }
}
