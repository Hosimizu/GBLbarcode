package com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//通过网络打印
public class PrintNetwork {
	public static void main(String[] args) {
		// 1 创建快递点
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try {

			socket = new Socket("172.18.112.76", 6101);
			// 2 准备要发送的数据
			Scanner scanner = new Scanner(
					"CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD30^JUS^LRN^CI0^XZ^XA^MMT^PW1181^LL0591^LS0^BY4,3,142^FT350,329^BCN,,N,N^FD>:LWVEB404XKB>5168917^FS^FT527,376^A0N,50,50^FB446,1,0,C^FH\\^FDLWVEB404XKB168917^FS^PQ1,0,1,Y^XZ");
			// 3 获得快递员
			os = socket.getOutputStream();
			is = socket.getInputStream();
			while (true) {
				String data = scanner.next();
				os.write(data.getBytes());
				os.flush();
				if ("over".equals(data)) {
					break;
				}

				// 收到回信
				byte[] bs = new byte[1024];
				int num = is.read(bs);
				String reault = new String(bs, 0, num);
				System.out.println("服务器回复的数据是  : " + reault);
				if ("over".equals(reault)) {
					break;
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Util.closed(null, socket, is, os);
		}
	}
}
