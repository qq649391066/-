package supermarket;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class addProvider {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addProvider window = new addProvider();
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
	public addProvider() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("添加供应商");
		frame.setBounds(100, 100, 735, 484);
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u4F9B\u5E94\u5546\u540D\u5B57:");
		label.setBounds(46, 51, 89, 30);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(149, 54, 139, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5730\u5740:");
		label_1.setBounds(358, 57, 72, 18);
		frame.getContentPane().add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(417, 54, 171, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_2 = new JLabel("\u7535\u8BDD\u53F7\u7801\uFF1A");
		label_2.setBounds(46, 194, 89, 30);
		frame.getContentPane().add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(149, 197, 139, 24);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_3 = new JLabel("\u94F6\u884C\u5361\u53F7:");
		label_3.setBounds(358, 200, 72, 18);
		frame.getContentPane().add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(433, 197, 155, 24);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		//确定按钮
		JButton button = new JButton("\u786E\u8BA4");
		button.setBounds(95, 315, 113, 27);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url="jdbc:mysql://localhost/supermarket?characterEncoding=utf-8";
				String user="root";
				String pwd="123";
				
				ResultSet rs;
				java.sql.Connection con;
				java.sql.Statement st;
				
				try{
					 Class.forName("com.mysql.jdbc.Driver");
					 con=DriverManager.getConnection(url,user,pwd);
					 st=con.createStatement();
					 
					 String name_= textField.getText();
					 String address_= textField_1.getText();
					 String tel_= textField_2.getText();
					 String bank_account_= textField_3.getText();
					 String sql = " INSERT INTO `tb_provide` (`name`,`address`,`tel`,`bank_account`)"
					 		+ " VALUES ('"+name_+"', '"+address_+"', '"+tel_+"', '"+bank_account_+"')";
					 int i=st.executeUpdate(sql);     //更新数据库
					 con.close();
					 st.close();
					 JOptionPane.showMessageDialog(null,"添加成功！");
				}catch(Exception a){
					System.out.print(a.toString());
				}				
			}			
		});

		
		JButton button_1 = new JButton("\u91CD\u7F6E");
		button_1.setBounds(417, 315, 113, 27);
		frame.getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
			}
		});
	}
}

