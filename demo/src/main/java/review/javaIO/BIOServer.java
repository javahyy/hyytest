package review.javaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class BIOServer {
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) {
        int port = 9010;
        try (ServerSocket ss = new ServerSocket(port);) {
            while (true) {//不断的接收客户端的连接：这个同时只能处理一个
                try {
                    //接收连接
                    Socket s = ss.accept();//会阻塞等待客户端连接
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(), charset));
                    String msg = null;
                    //接收数据
                    while ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                    }
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
