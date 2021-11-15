package com;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Hosimizu
 * @Date 2021/8/1 13:39
 * @Description: 连接电脑打印
 */
public class PrintBarcode implements DocumentListener {

    public static void main(String[] args) {
        new PrintBarcode().createFrame();
    }

    JFrame f;
    JTextField tf;

    public void createFrame() {
        // 创建窗体
        f = new JFrame("传祺条码打印");
        // 设置窗体大小 setSize(int width, int height)
        f.setSize(800, 600);

        // 设置窗体位置，null 为空，则是将窗口设置于屏幕中央
        f.setLocationRelativeTo(null);
        // 设置屏幕的背景 
        f.setBackground(Color.BLUE);

        JLabel label = new JLabel("请输入输入条码：");
        label.setBounds(75, 52, 130, 30);

        // 创建文本框对象
        tf = new JTextField();
        tf.setFont(new Font("宋体", Font.PLAIN, 30));
        tf.requestFocus(); //设置焦点
        // 设定文本框的位置以及大小 x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点 width:组件的长度 height:组件的高度
        tf.setBounds(210, 52, 350, 50);
        tf.getDocument().addDocumentListener(this);
		/*tf.addFocusListener(new FocusListener() { 
		    public void focusLost(FocusEvent e) {//失去焦点时 
		        System.out.println("失去了焦点"); 
		    } 
		    public void focusGained(FocusEvent e) {//获得焦点时 
		        System.out.println("获得了焦点"); 

		        JTextField textField = getTextField(e);  
				if (textField != null) {
					String vincode = textField.getText();
					System.out.println("vincode = " + vincode);
					if (vincode != null && vincode.length() > 0) {
						print(vincode);
						tf.setText("");
					}
				}       
		    } 
		});*/


        // 创建一个button  b，定义button名称
        JButton b = new JButton("打印");
        // 定义button位置，大小
        b.setBounds(170, 150, 100, 40);
        // 内部类为按钮 b绑定事件监控器
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打印
                String vincode = tf.getText();
                if (vincode == null || vincode.length() == 0) {
                    JOptionPane.showConfirmDialog(null, "请输入要打印的条码信息！", "打印提示",
                            JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                } else {
                    print(vincode);
                    tf.setText("");
                }
            }
        });

        // 创建一个button  c，定义button名称
        JButton c = new JButton("关闭");
        // 一定要和   b 的位置处理好，
        c.setBounds(370, 150, 100, 40);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭弹窗
                System.exit(0);
            }
        });

        // 把按钮、显示框对象加载到窗体上
        f.add(label);
        f.add(b);
        f.add(tf);
        f.add(c);
        // 清空布局，添加组件
        f.setLayout(null);
        f.setVisible(true);
    }

    public static JTextField getTextField(EventObject evt) {
        if (evt != null) {
            Object o = evt.getSource();
            if (o instanceof Component) {
                // find the parent text area
                Component c = (Component) o;
                for (; ; ) {
                    if (c instanceof JTextField) {
                        return (JTextField) c;
                    } else if (c == null) {
                        break;
                    } else {
                        c = c.getParent();
                    }
                }
            }
        }
        return null;
    }

    public static void print(String vincode) {
        //10*5.5
	/*	String barcode ="CT~~CD,~CC^~CT~"+
"^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD30^JUS^LRN^CI0^XZ"+
"^XA"+
"^MMT"+
"^PW1228"+
"^LL0600"+
"^LS0"+
"^BY3,3,118^FT475,355^BCN,,N,N"+
"^FD>:vincode^FS"+
"^FT117,445^BQN,2,10"+
"^FDLA,vincode^FS"+
"^FT543,411^A0N,58,57^FB529,1,0,C^FH\\^FDvincode^FS"+
"^PQ1,0,1,Y^XZ";*/

        //10*3.5
/*        String barcode ="CT~~CD,~CC^~CT~"+
                "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD30^JUS^LRN^CI0^XZ"+
                "^XA"+
                "^MMT"+
                "^PW1228"+
                "^LL0600"+
                "^LS0"+
                "^BY3,3,118^FT465,285^BCN,,N,N"+
                "^FD>:vincode^FS"+
                "^FT107,375^BQN,2,10"+
                "^FDLA,vincode^FS"+

                "^FT533,341^A0N,56,55^FB529,1,0,C^FH\\^FDvincode^FS"+
                "^PQ1,0,1,Y^XZ";*/
/*        String barcode = "CT~~CD,~CC^~CT~" +
                "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ\n" +
                "^XA" +
                "^MMT" +
                "^PW1181" +
                "^LL0354" +
                "^LS0" +
                "^FT386,210^A0N,88,84^FB760,1,0,R^FH\\^FDvincode^FS" +
                "^BY342,330^FT23,354^BXN,19,200,0,0,1,~" +
                "^FH\\^FDvincode^FS" +
                "^PQ1,0,1,Y^XZ";*/
/*        String barcode = "CT~~CD,~CC^~CT~"+
                "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD15^JUS^LRN^CI0^XZ" +
                "^XA" +
                "^MMT" +
                "^PW1181" +
                "^LL0354" +
                "^LS0" +
                "^FT391,279^A0N,79,76^FB723,1,0,R^FH\\^FDvincode^FS" +
                "^BY342,331^FT6,354^BXN,19,200,0,0,1,~" +
                "^FH\\^FDvincode^FS" +
                "^BY3,3,128^FT427,188^BCN,,N,N" +
                "^FD>:vincode^FS" +
                "^PQ1,0,1,Y^XZ";*/
        //左DM
/*        String barcode = "CT~~CD,~CC^~CT~"+
                "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD30^JUS^LRN^CI0^XZ" +
                "^XA" +
                "^MMT" +
                "^PW1166" +
                "^LL0339" +
                "^LS0" +
                "^BY3,3,112^FT451,166^BCN,,N,N" +
                "^FD>:vincode^FS" +
                "^FT482,264^A0N,62,60^FH\\^FDvincode^FS" +
                "^BY306,272^FT73,339^BXN,17,200,0,0,1,~" +
                "^FH\\^FDvincode^FS" +
                "^PQ1,0,1,Y^XZ";*/
        //左QR
/*        String barcode = "CT~~CD,~CC^~CT~"+
                "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD30^JUS^LRN^CI0^XZ" +
                "^XA" +
                "^MMT" +
                "^PW1166" +
                "^LL0339" +
                "^LS0" +
                "^BY3,3,112^FT451,166^BCN,,N,N" +
                "^FD>:vincode^FS" +
                "^FT482,264^A0N,62,60^FH\\^FDvincode^FS" +
                "^FT79,369^BQN,2,10" +
                "^FDHA,vincode^FS" +
                "^PQ1,0,1,Y^XZ";*/
        //左右护法
        //FT27,339^BXN TO FT27,359^BXN
        String barcode = "CT~~CD,~CC^~CT~"+
                "^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR6,6~SD30^JUS^LRN^CI0^XZ" +
                "^XA" +
                "^MMT" +
                "^PW1166" +
                "^LL0339" +
                "^LS0" +
                "^BY2,3,96^FT387,228^BCN,,N,N" +
                "^FD>:LMGMU1G83M>5108545>61^FS" +
                "^FT382,306^A0N,54,48^FH\\^FDvincode^FS" +
                "^FT855,369^BQN,2,10" +
                "^FDHA,vincode^FS" +
                "^BY324,274^FT27,359^BXN,18,200,0,0,1,~" +
                "^FH\\^FDvincode^FS" +
                "^PQ1,0,1,Y^XZ";
        //test + gitlab

        barcode = barcode.replaceAll("vincode", vincode);
        printImage("ZDesigner ZT410-300dpi ZPL", barcode);
    }

    public static void printImage(String printerName, String zplCode) {
        try {
            // 获得打印属性
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();//默认的PrintService,即默认的打印机设备
            Doc doc = new SimpleDoc(zplCode.getBytes(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
            service.createPrintJob().print(doc, pras);
        } catch (Exception e) {
            e.printStackTrace();
            //this.parameter = this.parameter + getExceptionStack(e);
            //System.out.println( e.getMessage());
        }
    }

    //文本框输入监听事件
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == tf.getDocument()) {
            Document doc = e.getDocument();
            try {
                String vincode = doc.getText(0, doc.getLength());
                vincode = vincode.replaceAll(" ", "");
                if (vincode != null && vincode.length() == 17) {
                    //System.out.println("输入条码内容：" + vincode);
                    print(vincode);
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            tf.setText("");
                            tf.requestFocus(); //设置焦点
                        }
                    });
                }

            } catch (BadLocationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

}
