package prj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	ServerSocket ss;
	Socket s;
	DataInputStream datain;
	DataOutputStream dataout;
	
	public Server(){
		try {
			ss = new ServerSocket(5050);
			System.out.println("서버준비");
			s = ss.accept();
			datain = new DataInputStream(s.getInputStream());
			dataout = new DataOutputStream(s.getOutputStream());
			System.out.println("클라이언트 소켓 생성");
		} catch (IOException e) {
			e.printStackTrace();
		}
		recv();
		send();
	}
	private void send() {
		Scanner sc = new Scanner(System.in);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while(true) {
						String send = sc.nextLine();
						dataout.writeUTF(send);
					}
				} catch (IOException e) {
					System.out.println("exit");
				}
			}
		}).start();
	}
	private void recv() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while(true) {
						String recv = datain.readUTF();
						System.out.println(recv);
					}
				} catch (IOException e) {
					System.out.println("exit");
				}
			}
		}).start();
	}
	public static void main(String[] args) {
		new Server();
	}
}
