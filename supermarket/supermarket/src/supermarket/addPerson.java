package supermarket;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class addPerson {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=-11,14
	 */
	private final JPanel panel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public addPerson() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("添加员工");
		frame.setBounds(100, 100, 679, 476);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 661, 0);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u5458\u5DE5\u7F16\u53F7\uFF1A");
		label_1.setBounds(14, 27, 81, 18);
		frame.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setBounds(88, 24, 113, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label_2 = new JLabel("\u5458\u5DE5\u540D\u5B57\uFF1A");
		label_2.setBounds(312, 27, 81, 18);
		frame.getContentPane().add(label_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(403, 24, 122, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_4 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\u7801\uFF1A");
		label_4.setBounds(312, 116, 97, 18);
		frame.getContentPane().add(label_4);
		
		textField_2 = new JTextField();
		textField_2.setBounds(403, 113, 122, 24);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_5 = new JLabel("\u94F6\u884C\u5361\u53F7\uFF1A");
		label_5.setBounds(14, 201, 81, 18);
		frame.getContentPane().add(label_5);
		
		textField_3 = new JTextField();
		textField_3.setBounds(88, 198, 113, 24);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel label_6 = new JLabel("\u7535\u8BDD\u53F7\u7801\uFF1A");
		label_6.setBounds(312, 216, 81, 18);
		frame.getContentPane().add(label_6);
		
		textField_4 = new JTextField();
		textField_4.setBounds(403, 213, 122, 24);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel label_7 = new JLabel("\u5730\u5740\uFF1A");
		label_7.setBounds(14, 285, 72, 18);
		frame.getContentPane().add(label_7);
		
		textField_5 = new JTextField();
		textField_5.setBounds(98, 282, 103, 24);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		//确定按钮
		JButton button = new JButton("\u786E\u5B9A");
		button.setBounds(116, 356, 122, 35);
		frame.getContentPane().add(button);
		
		
		JButton button_1 = new JButton("\u91CD\u7F6E");
		button_1.setBounds(301, 360, 122, 31);
		frame.getContentPane().add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
			}
		});
		
		JLabel label_3 = new JLabel("\u6027\u522B\uFF1A");
		label_3.setBounds(14, 116, 72, 18);
		frame.getContentPane().add(label_3);
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton radioButton = new JRadioButton("\u7537");
		bg.add(radioButton);
		radioButton.setBounds(58, 112, 57, 27);
		frame.getContentPane().add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u5973");
		bg.add(radioButton_1);
		radioButton_1.setBounds(118, 112, 72, 27);
		frame.getContentPane().add(radioButton_1);
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
					 
					 String id_= textField.getText();
					 String name_= textField_1.getText();
					 String id_number_= textField_2.getText();
					 String bank_account_= textField_3.getText();
					 String tel_= textField_4.getText();
					 String address_= textField_5.getText();
				     String sex_=null;
				     if(radioButton.isSelected()) {
				    	 sex_="男";
				     }else {
				    	 sex_="女";
				     }
					 String sql = " INSERT INTO `tb_message` (`id`, `name`, `sex`, `id_number`, `bank_account`, `tel`, `address`)"
					 		+ " VALUES ('"+id_+"', '"+name_+"', '"+sex_+"', '"+id_number_+"', '"+bank_account_+"', '"+tel_+"', '"+address_+"')";
					 int i=st.executeUpdate(sql);     //更新数据库
					 con.close();
					 st.close();
					 JOptionPane.showMessageDialog(null,"添加成功");
				}catch(Exception a){
					System.out.print(a.toString());
				}				
			}			
		});

	}
}
