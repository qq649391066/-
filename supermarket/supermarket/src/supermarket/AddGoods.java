package supermarket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class AddGoods {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_2;
	private JTextField textField_2;
	private JLabel label_3;
	private JTextField textField_3;
	private JLabel label_4;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddGoods window = new AddGoods();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public AddGoods() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("添加商品");
		frame.setBounds(100, 100, 819, 444);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("\u8BA2\u5355\u53F7:");
		label.setBounds(57, 74, 72, 18);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(143, 71, 148, 24);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("\u5546\u54C1\u540D\u79F0:");
		label_1.setBounds(405, 74, 72, 18);
		frame.getContentPane().add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(519, 71, 187, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		label_2 = new JLabel("\u4EF7\u683C\uFF1A");
		label_2.setBounds(57, 174, 72, 18);
		frame.getContentPane().add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(143, 171, 148, 24);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		label_3 = new JLabel("\u6570\u91CF\uFF1A");
		label_3.setBounds(405, 174, 72, 18);
		frame.getContentPane().add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(519, 171, 187, 24);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		label_4 = new JLabel("\u5907\u6CE8\uFF1A");
		label_4.setBounds(57, 250, 72, 18);
		frame.getContentPane().add(label_4);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(143, 248, 566, 80);
		frame.getContentPane().add(textArea);
		
		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url="jdbc:mysql://localhost/supermarket?characterEncoding=utf-8";
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
					 String name_=textField_1.getText();
					 String price_=textField_2.getText();
					 String amount_=textField_3.getText();
					 String note_=textArea.getText();
					 String sql="INSERT INTO `goods` (`id`, `name`, `price`, `amount`, `note`) "
					 		+ "VALUES ('"+id_+"', '"+name_+"', '"+price_+"', '"+amount_+"', '"+note_+"')";
					 
					 int i=st.executeUpdate(sql);     //更新数据库
					 con.close();
					 st.close();
					 JOptionPane.showMessageDialog(null,"添加成功！");
					 
				}catch(Exception a){
					System.out.print(a.toString());
				}
			}
			});
	
			
		//确定按钮
		button.setBounds(153, 344, 113, 27);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("重置");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textArea.setText("");
			}
		});
		button_1.setBounds(450, 344, 113, 27);
		frame.getContentPane().add(button_1);
	}
}
			
	
