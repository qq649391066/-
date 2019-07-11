package supermarket;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import db.DBUtil;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Update extends JFrame {
	// 声明滚动面板
	private JScrollPane spTable;
	// 声明一个盛放按钮的面板
	private JPanel pButtons;
	private JButton bthDelete, btnFlush;
	// 声明默认表格模式
	private DefaultTableModel model;
	// 声明表格
	private JTable table;
	private JTextField textField;
	private JLabel label;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_2;
	private JTextField textField_3;

	public Update() {
		super("供应商信息");

		// 创建默认表格模式
		model = new DefaultTableModel();
		getContentPane().setLayout(null);
		// 创建表格
		table = new JTable(model);
		// 设置表格选择模式为单一选择
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// 创建一个滚动面板，包含表格
		spTable = new JScrollPane(table);
		spTable.setBounds(0, 0, 482, 176);
		// 将滚动面板添加到窗体中央
		getContentPane().add(spTable);

		// 创建按钮
		bthDelete = new JButton("修改");
		bthDelete.setBounds(198, 5, 63, 27);
		btnFlush = new JButton("刷新");
		btnFlush.setBounds(298, 5, 63, 27);
		// 创建面板
		pButtons = new JPanel();
		pButtons.setBounds(0, 316, 482, 37);
		pButtons.setLayout(null);
		// 将按钮添加到面板中
		pButtons.add(bthDelete);
		pButtons.add(btnFlush);
		// 将盛放按钮的面板添加到窗体的南部（下面）
		getContentPane().add(pButtons);
		
		JButton button = new JButton("\u663E\u793A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=table.getSelectedRow();			
				String num1=table.getValueAt(index, 0).toString();
				String num2=table.getValueAt(index, 1).toString();
				String num3=table.getValueAt(index, 2).toString();
				String num4=table.getValueAt(index, 3).toString();
				textField.setText(num1);
				textField.setEditable(false);
				textField_1.setText(num2);
				textField_2.setText(num3);
				textField_3.setText(num4);				
			}
		});
		button.setBounds(96, 5, 63, 27);
		pButtons.add(button);
		
		JLabel lblNewLabel = new JLabel("姓名：");
		lblNewLabel.setBounds(10, 201, 72, 18);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(71, 198, 124, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		label = new JLabel("\u5730\u5740\uFF1A");
		label.setBounds(245, 201, 72, 18);
		getContentPane().add(label);
		
		textField_1 = new JTextField();
		textField_1.setBounds(312, 198, 124, 24);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		lblNewLabel_1 = new JLabel("电话");
		lblNewLabel_1.setBounds(10, 269, 72, 18);
		getContentPane().add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(71, 266, 124, 24);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		lblNewLabel_2 = new JLabel("银行账号");
		lblNewLabel_2.setBounds(245, 269, 72, 18);
		getContentPane().add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(312, 266, 124, 24);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);

		// 注册监听
		bthDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url="jdbc:mysql://localhost:3306/supermarket";
				String user="root";
				String pwd="123";
				
				ResultSet rs;
				java.sql.Connection con;
				java.sql.Statement  st;	
				
				try{
					 Class.forName("com.mysql.jdbc.Driver");
					 con=DriverManager.getConnection(url,user,pwd);
					 st=con.createStatement();
					 
					
					 
					 String id_=textField.getText();
					 String address_=textField_1.getText();
					 String tel_=textField_2.getText();
					 String bank_=textField_3.getText();
					 
					 String sql="UPDATE `tb_provide` SET `address` = '"+address_+"', `tel` = '"+tel_+"', `bank_account` = '"+bank_+"' WHERE `tb_provide`.`name` = '"+id_+"'";
					 int i=st.executeUpdate(sql);     //更新数据库
					 con.close();
					 st.close();
					 JOptionPane.showMessageDialog(null,"修改成功！");
				}catch(Exception a){
					System.out.print(a.toString());
				}
				
				
				
				
				
				
			}
		});//修改
		btnFlush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 调用显示数据的方法
				showData();
			}
		});

		// 初始化显示表格数据
		this.showData();

		// 设定窗口大小
		this.setSize(500, 400);
		// 设定窗口左上角坐标（X轴200像素，Y轴100像素）
		this.setLocation(200, 100);
		// 设定窗口默认关闭方式为退出应用程序
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口可视（显示）
		this.setVisible(true);
	}

	// 查看goods表，并显示到表格中
	private void showData() {
		// 查询goods表
		String sql = "select * from tb_provide";
		//String sql = "select id as ID号,name as 商品名,price as 价格,amount as 数量,note as 信息 from goods";
		// 数据库访问
		DBUtil db = new DBUtil();
		try {
			db.getConnection();
			ResultSet rs = db.executeQuery(sql, null);
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取列数
			int colCount = rsmd.getColumnCount();
			// 存放列名
			Vector<String> title = new Vector<String>();
			// 列名
			for (int i = 1; i <= colCount; i++) {
				title.add(rsmd.getColumnLabel(i));
			}
			// 表格数据
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
				// 行数据
				Vector<String> rowdata = new Vector<String>();
				for (int i = 1; i <= colCount; i++) {
					rowdata.add(rs.getString(i));
				}
				data.add(rowdata);
			}
			if (rowCount == 0) {
				model.setDataVector(null, title);
			} else {
				model.setDataVector(data, title);
			}
		} catch (Exception ee) {
			System.out.println(ee.toString());
			JOptionPane.showMessageDialog(this, "系统出现异常错误。请检查数据库。系统即将退出！！！",
					"错误", 0);
		} finally {
			db.closeAll();
		}
	}
}
