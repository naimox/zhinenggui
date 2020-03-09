package com.jeesite.modules.utils;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.stream.FileImageOutputStream;

public class HandleImageUtil {
	/**
	 * 字节转换为图片
	 * @param data
	 * @param path
	 */
	public static void byte2image(byte[] data, String path) {
		if (data.length < 3 || path.equals("")){
		return;
		}
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		}
		catch (Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}
	/**
	 * 图片转换为字节
	 * @param imgSrc
	 * @return
	 * @throws Exception
	 */
	public static byte[] image2Bytes(String imgSrc) throws Exception{
		FileInputStream fin = new FileInputStream(new File(imgSrc));
		//可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
		byte[] bytes  = new byte[fin.available()];
		//将文件内容写入字节数组，提供测试的case
		fin.read(bytes);
		
		fin.close();
		return bytes;
	}
}
