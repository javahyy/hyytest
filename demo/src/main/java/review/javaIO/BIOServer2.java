package review.javaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer2 {
    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) {
        int port = 9010;
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        try (ServerSocket ss = new ServerSocket(port);) {
            while (true) {//不断的接收客户端的连接：这个同时只能处理一个
                Socket s = ss.accept();//会阻塞等待客户端连接
                //新开一个线程去处理这个连接
//                new Thread(new ScoketProcess(s)).start();
                //换成线程池
                executorService.execute(new ScoketProcess(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ScoketProcess implements Runnable {
        Socket s;

        public ScoketProcess(Socket s) {
            super();
            this.s = s;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(), charset));
            ) {
                //接收连接
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
    }

}
