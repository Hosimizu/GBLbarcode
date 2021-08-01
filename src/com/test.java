package com;

public class test {

	public static void main(String[] args) {
		
		offline("LWVEB405KB1720961");
		
	}

	//下线
	public static void offline(String vincode){
		ZplPrinter p = new ZplPrinter("\\\\192.168.0.12\\ZDesigner 105SLPlus-300dpi ZPL");  
    
        p.resetZpl();//注意要清除上次的打印信息  
        //2.打印中、英、数字、条码混合  
        //上边条码 
        String bar1Zpl = "^FO100,45^BY3,3.0,118^BCN,,Y,N,N^FD${data}^FS";//条码样式模板  
        p.setBarcode(vincode,bar1Zpl);  
        //p.setChar(vincode, 190, 130, 60, 60); 
        //p.setText("库位：K1-A01-01",450, 80, 40, 40, 30, 2, 2, 24); 
        //下边条码  
        String bar2Paper = "^FO100,255^BY4,4.0,218^BCN,,Y,N,N^FD${data}^FS";//条码样式模板  
        p.setBarcode(vincode,bar2Paper); 
        p.setText("湖南六谷大药房连锁有限公司", 380, 260, 60, 60, 30, 2, 2, 24); 
        //p.setChar(vincode, 280, 591, 60, 60); 
        
        //p.setText("库       位：K1-A01-01", 380, 420, 56, 56, 30, 2, 2, 24);  
        //p.setText("入库时间：2019-09-24 12：12：12", 380, 485, 56, 56, 30, 2, 2, 24);  
        //p.setText("产品名称：Aions - 珍珠白+棕色主题", 380, 565, 56, 56, 30, 2, 2, 24); 
        
        
        String zpl2 = p.getZpl(); 
        p.print(zpl2);
	}
}
