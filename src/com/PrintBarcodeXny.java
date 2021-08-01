package com;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;


//连接电脑打印 新能源
public class PrintBarcodeXny implements DocumentListener {

    public static void main(String[] args) {
        new PrintBarcodeXny().createFrame();
    }

    JFrame f;
    JTextField tf;
    static String type = "下线";

    JTextField fileTextField;

    public void createFrame() {
        // 创建窗体
        f = new JFrame("新能源条码打印");
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

        //单先按钮
        // 创建两个单选按钮
        final JRadioButton radioBtn1 = new JRadioButton("下线");
        final JRadioButton radioBtn2 = new JRadioButton("在库");
        final JRadioButton radioBtn3 = new JRadioButton("出库");

        radioBtn1.setBounds(210, 120, 80, 40);
        radioBtn2.setBounds(310, 120, 80, 40);
        radioBtn3.setBounds(410, 120, 80, 40);

        // 创建按钮组，把两个单选按钮添加到该组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioBtn1);
        btnGroup.add(radioBtn2);
        btnGroup.add(radioBtn3);

        // 设置第一个单选按钮选中
        radioBtn1.setSelected(true);
        radioBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = radioBtn1.getText();
            }

        });

        radioBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = radioBtn2.getText();
            }

        });

        radioBtn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = radioBtn3.getText();
            }

        });


        // 创建一个button  b，定义button名称
        JButton b = new JButton("打印");
        // 定义button位置，大小
        b.setBounds(170, 180, 100, 40);
        // 内部类为按钮 b绑定事件监控器
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打印
                String vincode = tf.getText();
                vincode.replaceAll(" ", "");
                if (vincode == null || vincode.length() == 0) {
					/*JOptionPane.showConfirmDialog (null, "请输入要打印的条码信息！", "打印提示", 
							JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE);*/

                    JOptionPane.showMessageDialog(null, "请输入要打印的条码信息！");
                } else {
                    print(vincode);
                    tf.setText("");
                }
            }
        });

        // 创建一个button  c，定义button名称
        JButton c = new JButton("关闭");
        // 一定要和   b 的位置处理好，
        c.setBounds(370, 180, 100, 40);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭弹窗
                System.exit(0);
            }
        });

        //导入excel文件进行打印
        fileTextField = new JTextField();
        fileTextField.setFont(new Font("宋体", Font.PLAIN, 18));
        fileTextField.setBounds(50, 250, 550, 40);
        final JButton fileBtn = new JButton("选择导入文件");
        fileBtn.setBounds(610, 250, 150, 40);
        fileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按钮点击事件
                JFileChooser chooser = new JFileChooser();             //设置选择器
                chooser.setMultiSelectionEnabled(true);             //设为多选
                int returnVal = chooser.showOpenDialog(fileBtn);        //是否打开文件选择框
                System.out.println("returnVal=" + returnVal);
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型
                    String filepath = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    System.out.println(filepath);
                    fileTextField.setText(filepath);
                }
            }
        });
        JButton okBtn = new JButton("确认");
        okBtn.setBounds(300, 320, 100, 40);
        /* 确定点击 */
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filepath = fileTextField.getText();
                if (filepath == null || "".equals(filepath)) {
                    JOptionPane.showMessageDialog(null, "请先选择文件！");
                    return;
                }

                String suffix = filepath.substring(filepath.lastIndexOf(".") + 1);
                System.out.println(suffix);
                if (!(suffix.equals("xlsx") || (suffix.equals("xls")))) {
                    JOptionPane.showMessageDialog(null, "请选择Excel文件！");
                    return;
                }

                try {
                    printFile(filepath);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });


        f.add(fileTextField);
        f.add(fileBtn);
        f.add(okBtn);

        // 把按钮、显示框对象加载到窗体上
        f.add(label);
        f.add(radioBtn1);
        f.add(radioBtn2);
        f.add(radioBtn3);
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

    //扫描打印
    public static void print(String vincode) {
        PrintBean pb = com.MysqlJdbcUtil.instance().getPringBean(vincode, type);
        JOptionPane.showMessageDialog(null, pb.getProductName());
        if ("下线".equals(type) || "在库".equals(type)) {
            offlinePrint(pb);
        } else if ("出库".equals(type)) {
            outPrint(pb);
        }
    }

    //导入打印
    public static void printFile(String filepath) throws Exception {
        List<String> list = getBankListByExcel(filepath);
        if (list != null && list.size() > 0) {
            for (String vincode : list) {
                print(vincode);
            }
        }
    }

    /**
     * 根据文件后缀，自适应上传文件的版本
     * 描述 ： getWorkbook
     * 作者 ： 谢彬斌
     * 时间 ： 2017年6月13日
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  //2003-
        } else if (".xlsx".equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  //2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }


    /**
     * 获取IO流中的数据，组装成List<List<Object>>对象
     * 描述 ： getBankListByExcel
     * 作者 ： 谢彬斌
     * 时间 ：2017年6月13日
     */
    public static List<String> getBankListByExcel(String filepath) throws Exception {

        FileInputStream in = new FileInputStream(filepath);
        //创建Excel工作薄
        Workbook work = getWorkbook(in, filepath);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        List<String> list = new ArrayList<String>();
        //读取第一个工作表
        Sheet sheet = work.getSheetAt(0);
        //获取最后一行的num，即总行数。此处从0开始计数
        int maxRow = sheet.getLastRowNum();

        for (int row = 0; row <= maxRow; row++) {
            int maxRol = sheet.getRow(row).getLastCellNum();
            for (int rol = 0; rol < maxRol; rol++) {
                String vin = sheet.getRow(row).getCell(rol).toString();
                list.add(vin);
            }
        }
        in.close();
        return list;

    }

    //下线打印
    public static void offlinePrint(com.PrintBean pb) {
        com.ZplPrinter p = new com.ZplPrinter("\\\\192.168.0.12\\ZDesigner 105SLPlus-300dpi ZPL");

        p.resetZpl();//注意要清除上次的打印信息  
        //2.打印中、英、数字、条码混合  
        //上边条码 
        String bar1Zpl = "^FO80,45^BY3,3.0,118^BCN,,Y,N,N^FD${data}^FS";//条码样式模板  
        p.setBarcode(pb.getVincode(), bar1Zpl);
        if (!pb.getBinCode().equals("")) {
            p.setText(pb.getBinCode(), 880, 100, 40, 40, 30, 2, 2, 24);
        }

        //下边条码  
        String bar2Paper = "^FO100,290^BY4,4.0,218^BCN,,Y,N,N^FD${data}^FS";//条码样式模板  
        p.setBarcode(pb.getVincode(), bar2Paper);

        p.setText("库位:" + pb.getBinCode(), 80, 650, 56, 56, 30, 2, 2, 24);
        p.setText("入库时间:" + pb.getReceiveDate(), 80, 750, 56, 56, 30, 2, 2, 24);
        p.setText("产品名称:" + pb.getProductName(), 80, 850, 56, 56, 30, 2, 2, 24);

        String zpl2 = p.getZpl();
        p.print(zpl2);
    }

    //出库打印
    public static void outPrint(com.PrintBean pb) {
        com.ZplPrinter p = new com.ZplPrinter("\\\\192.168.0.12\\ZDesigner 105SLPlus-300dpi ZPL");

        p.resetZpl();//注意要清除上次的打印信息  
        //2.打印中、英、数字、条码混合  
        //上边条码 
        String bar1Zpl = "^FO80,45^BY3,3.0,118^BCN,,Y,N,N^FD${data}^FS";//条码样式模板  
        p.setBarcode(pb.getVincode(), bar1Zpl);
        if (!pb.getBinCode().equals("")) {
            p.setText(pb.getBinCode(), 880, 100, 40, 40, 30, 2, 2, 24);
        }

        //下边条码  
        String bar2Paper = "^FO100,290^BY4,4.0,218^BCN,,Y,N,N^FD${data}^FS";//条码样式模板  
        p.setBarcode(pb.getVincode(), bar2Paper);

        p.setText("整列道:" + pb.getBinCode(), 80, 650, 56, 56, 30, 2, 2, 24);
        p.setText("销售店:" + pb.getDestName(), 80, 750, 56, 56, 30, 2, 2, 24);
        p.setText("产品名称:" + pb.getProductName(), 80, 850, 56, 56, 30, 2, 2, 24);

        String zpl2 = p.getZpl();
        p.print(zpl2);
    }


    public static void PrintImage(String PrinterName, String ZplCode) {
        try {
            // 获得打印属性
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            pras.add(new Copies(1));
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();//默认的PrintService,即默认的打印机设备
            Doc doc = new SimpleDoc(ZplCode.getBytes(), DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
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
