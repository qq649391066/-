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
	// �����������
	private JScrollPane spTable;
	// ����һ��ʢ�Ű�ť�����
	private JPanel pButtons;
	private JButton bthDelete, btnFlush;
	// ����Ĭ�ϱ��ģʽ
	private DefaultTableModel model;
	// �������
	private JTable table;
	private JTextField textField;
	private JLabel label;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_2;
	private JTextField textField_3;

	public Update() {
		super("��Ӧ����Ϣ");

		// ����Ĭ�ϱ��ģʽ
		model = new DefaultTableModel();
		getContentPane().setLayout(null);
		// �������
		table = new JTable(model);
		// ���ñ��ѡ��ģʽΪ��һѡ��
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// ����һ��������壬�������
		spTable = new JScrollPane(table);
		spTable.setBounds(0, 0, 482, 176);
		// �����������ӵ���������
		getContentPane().add(spTable);

		// ������ť
		bthDelete = new JButton("�޸�");
		bthDelete.setBounds(198, 5, 63, 27);
		btnFlush = new JButton("ˢ��");
		btnFlush.setBounds(298, 5, 63, 27);
		// �������
		pButtons = new JPanel();
		pButtons.setBounds(0, 316, 482, 37);
		pButtons.setLayout(null);
		// ����ť��ӵ������
		pButtons.add(bthDelete);
		pButtons.add(btnFlush);
		// ��ʢ�Ű�ť�������ӵ�������ϲ������棩
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
		
		JLabel lblNewLabel = new JLabel("������");
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
		
		lblNewLabel_1 = new JLabel("�绰");
		lblNewLabel_1.setBounds(10, 269, 72, 18);
		getContentPane().add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(71, 266, 124, 24);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		lblNewLabel_2 = new JLabel("�����˺�");
		lblNewLabel_2.setBounds(245, 269, 72, 18);
		getContentPane().add(lblNewLabel_2);
		
		textField_3 = new JTextField();
		textField_3.setBounds(312, 266, 124, 24);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);

		// ע�����
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
					 int i=st.executeUpdate(sql);     //�������ݿ�
					 con.close();
					 st.close();
					 JOptionPane.showMessageDialog(null,"�޸ĳɹ���");
				}catch(Exception a){
					System.out.print(a.toString());
				}
				
				
				
				
				
				
			}
		});//�޸�
		btnFlush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������ʾ���ݵķ���
				showData();
			}
		});

		// ��ʼ����ʾ�������
		this.showData();

		// �趨���ڴ�С
		this.setSize(500, 400);
		// �趨�������Ͻ����꣨X��200���أ�Y��100���أ�
		this.setLocation(200, 100);
		// �趨����Ĭ�Ϲرշ�ʽΪ�˳�Ӧ�ó���
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڿ��ӣ���ʾ��
		this.setVisible(true);
	}

	// �鿴goods������ʾ�������
	private void showData() {
		// ��ѯgoods��
		String sql = "select * from tb_provide";
		//String sql = "select id as ID��,name as ��Ʒ��,price as �۸�,amount as ����,note as ��Ϣ from goods";
		// ���ݿ����
		DBUtil db = new DBUtil();
		try {
			db.getConnection();
			ResultSet rs = db.executeQuery(sql, null);
			ResultSetMetaData rsmd = rs.getMetaData();
			// ��ȡ����
			int colCount = rsmd.getColumnCount();
			// �������
			Vector<String> title = new Vector<String>();
			// ����
			for (int i = 1; i <= colCount; i++) {
				title.add(rsmd.getColumnLabel(i));
			}
			// �������
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
				// ������
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
			JOptionPane.showMessageDialog(this, "ϵͳ�����쳣�����������ݿ⡣ϵͳ�����˳�������",
					"����", 0);
		} finally {
			db.closeAll();
		}
	}
}
