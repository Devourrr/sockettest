package prj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	Socket s;
	DataInputStream datain;
	DataOutputStream dataout;
	
	public Client() {
		try {
			s = new Socket("localhost",5050);
			datain = new DataInputStream(s.getInputStream());
			dataout = new DataOutputStream(s.getOutputStream());
			System.out.println("서버 접속요청");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		new Client();
	}
}
