package supermarket;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import db.DBUtil;

import javax.swing.JButton;

public class perMessage {

	private JFrame frame;
	private JTextField textField;
	private DefaultTableModel model;
	private JTable table;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					perMessage window = new perMessage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public perMessage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("员工查询");
		frame.setBounds(100, 100, 615, 471);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 583, 62);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(291, 5, 0, 0);
		panel.add(label);
		
		JComboBox comboBox = new JComboBox(new String[] {"员工编号","员工名称"});
		comboBox.setBounds(43, 25, 91, 24);
		panel.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(217, 25, 157, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("\u67E5\u8BE2");
	
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		   select();
		  // select1();
		   
		}

			});	
		button.setBounds(423, 24, 113, 27);
		panel.add(button);
		
		JButton button_1 = new JButton("\u91CD\u7F6E");
		button_1.setBounds(266, 384, 113, 27);
		frame.getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});
		
		model = new DefaultTableModel();
		table = new JTable(model);
		table.setBounds(14, 112, 569, 248);
		frame.getContentPane().add(table);
		
		JLabel label_1 = new JLabel("\u7F16\u53F7");
		label_1.setBounds(14, 94, 72, 18);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5458\u5DE5\u540D\u5B57");
		label_2.setBounds(97, 94, 72, 18);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u6027\u522B");
		label_3.setBounds(189, 94, 72, 18);
		frame.getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\u7801");
		label_4.setBounds(266, 94, 81, 18);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("\u94F6\u884C\u8D26\u53F7");
		label_5.setBounds(362, 94, 72, 18);
		frame.getContentPane().add(label_5);
		
		JLabel label_6 = new JLabel("\u7535\u8BDD\u53F7\u7801");
		label_6.setBounds(437, 94, 72, 18);
		frame.getContentPane().add(label_6);
		
		JLabel label_7 = new JLabel("\u5730\u5740");
		label_7.setBounds(511, 94, 72, 18);
		frame.getContentPane().add(label_7);
	}
	private void select() {
		// TODO Auto-generated method stub
		// 查询tb_message表
		String str_= textField.getText();
		String sql="SELECT * FROM `tb_message` WHERE `id` = '"+str_+"'";
	
		// 数据库访问
		DBUtil db = new DBUtil();
		try {
			db.getConnection();
			ResultSet rs = db.executeQuery(sql, null);
			ResultSetMetaData rsmd =rs.getMetaData();
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
			//JOptionPane.showInternalMessageDialog(this, "系统出现异常错误。请检查数据库。系统即将退出！！！",	"错误", 0);
			 
		} finally {
		db.closeAll();
		}
	}
}



	/*private void select1() {
		// TODO Auto-generated method stub
		// 查询tb_message表
		String str_= textField.getText();
		String sql="SELECT * FROM `tb_message` WHERE `name` = '"+str_+"'";
	
		// 数据库访问
		DBUtil db = new DBUtil();
		try {
			db.getConnection();
			ResultSet rs = db.executeQuery(sql, null);
			ResultSetMetaData rsmd =rs.getMetaData();
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
			//JOptionPane.showInternalMessageDialog(this, "系统出现异常错误。请检查数据库。系统即将退出！！！",	"错误", 0);
			 
		} finally {
			db.closeAll();
		}
	}
}
*/