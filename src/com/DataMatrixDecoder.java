package com;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DataMatrix二维码解码
 * 
 * @author 王昂
 */
class DataMatrixDecoder {
	String text;

	DataMatrixDecoder() {
		try {
			String imagePath = "D:\\code.png";
			File file = new File(imagePath);
			DataMatrixReader reader = new DataMatrixReader();
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap imageBinaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType,Object> hints = new LinkedHashMap<DecodeHintType,Object>();
			// 解码设置编码方式为：utf-8，
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			//优化精度
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			//复杂模式，开启PURE_BARCODE模式
			hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
			Result result = reader.decode(imageBinaryBitmap, hints);
			text = new String(URLDecoder.decode(result.getText(), "utf-8").getBytes("iso-8859-1"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {

			DataMatrixDecoder DataMatrixdecode = new DataMatrixDecoder();
			System.out.println("解析成功");
			System.out.println("内容为：" + DataMatrixdecode.text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}