package com;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;

import java.io.File;

/**
 * DataMatrix二维码编码
 * 
 * @author 王昂
 */
class DataMatrixEncoder {
	public void DataMatrixEncoder() throws Exception {
		try {
			String content = "你好，Zxing!";
			String imagePath = "D:\\code.png";
			File file = new File(imagePath);
			content = new String(content.getBytes("utf-8"), "iso-8859-1");
			DataMatrixWriter writerDM = new DataMatrixWriter();
			BitMatrix matrixDM = writerDM.encode(content, BarcodeFormat.DATA_MATRIX, 300, 300);
			MatrixToImageWriter.writeToPath(matrixDM, "png", file.toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			DataMatrixEncoder DataMatrixencode = new DataMatrixEncoder();
			DataMatrixencode.DataMatrixEncoder();
			System.out.println("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}