package gui;

import info.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.CPYDDatabase;

//import database.CPYDDatabase;

public class UserLogin extends JFrame implements ActionListener{
	private JTextField fieldAccount = new JTextField();
	private JPasswordField fieldPassword = new JPasswordField();
	private JButton buttonLogin = new JButton("��¼");

	public static void main(String[] args) {
		new UserLogin();

	}

	public UserLogin() {
		this.setTitle("�û���¼");
		this.setLocation(400, 300);
		this.setSize(300, 400);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		
		JLabel labelAccount = new JLabel("�˺�");
		labelAccount.setAlignmentY(CENTER_ALIGNMENT);
		labelAccount.setBounds(50, 100, 100, 30);
		fieldAccount.setBounds(150, 100, 100, 30);
		this.getContentPane().add(labelAccount);
		this.getContentPane().add(fieldAccount);

		
		
		JLabel labelPwd = new JLabel("����");
		labelPwd.setAlignmentY(CENTER_ALIGNMENT);
		labelPwd.setBounds(50, 150, 100, 30);
		fieldPassword.setBounds(150, 150, 100, 30);
		this.getContentPane().add(labelPwd);
		this.getContentPane().add(fieldPassword, BorderLayout.CENTER);
		
		buttonLogin.setBounds(75, 250, 150, 30);
		this.getContentPane().add(buttonLogin);
		buttonLogin.addActionListener(this);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonLogin){
			String account = fieldAccount.getText();
			String password = new String(fieldPassword.getPassword());
			
			User user = verifyAccount(account, password);
			if( user != null){
				
				new TicketBooker(user);
				
				
				this.dispose();
			}
			else{
				JOptionPane.showMessageDialog(this, "�˺Ż��������");
				fieldPassword.setText(null);
			}
		}		
	}


	private User verifyAccount(String account, String password) {
		User user=CPYDDatabase.userQquery(account);
		if(user==null){
		System.out.println("�û�������");}
		else if(!user.verifyPwd(password)){
			System.out.println("�������");
			user =null;
		}
		return user;
}
}