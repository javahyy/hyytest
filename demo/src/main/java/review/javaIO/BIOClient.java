package review.javaIO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

public class BIOClient implements Runnable {

    private static Charset charset = Charset.forName("UTF-8");
    private String host = "127.0.0.1";
    private Integer port = 8080;

    public BIOClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket s = new Socket(host, port); OutputStream out = s.getOutputStream();) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入：");
            String msg = scanner.nextLine();
            out.write(msg.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        BIOClient client = new BIOClient("localhost", 9010);
        client.run();
    }
}