package supermarket;

import util.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import db.Database;

//import database.CPYDDatabase;

public class login extends JFrame implements ActionListener{
	private JTextField fieldAccount = new JTextField();
	private JPasswordField fieldPassword = new JPasswordField();
	private JButton buttonLogin = new JButton("µ«¬º");

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("Look and Feel Exception");
			System.exit(0);
		}
		new login();

	}

	public login() {
		this.setTitle("”√ªßµ«¬º");
		this.setLocation(400, 300);
		this.setSize(300, 400);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

	    JLabel 	label=new JLabel();
		label.setIcon(new ImageIcon("src/image/88.jpg"));
		label.setBounds(35, 10, 230, 120);
		this.getContentPane().add(label);
	
		
		JLabel labelAccount = new JLabel("’À∫≈");
		labelAccount.setAlignmentY(CENTER_ALIGNMENT);
		labelAccount.setBounds(70, 160, 100, 30);
		fieldAccount.setBounds(120, 160, 100, 30);
		this.getContentPane().add(labelAccount);
		this.getContentPane().add(fieldAccount);

		JLabel labelPwd = new JLabel("√‹¬Î");
		labelPwd.setAlignmentY(CENTER_ALIGNMENT);
		labelPwd.setBounds(70, 230, 100, 30);
		fieldPassword.setBounds(120, 230, 100, 30);
		this.getContentPane().add(labelPwd);
		this.getContentPane().add(fieldPassword, BorderLayout.CENTER);
		
		buttonLogin.setBounds(75,300 , 150, 30);
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
				
				new Main();
				
				
				this.dispose();
			}
			else{
				JOptionPane.showMessageDialog(this, "’À∫≈ªÚ√‹¬Î¥ÌŒÛ");
				fieldPassword.setText(null);
			}
		}		
	}


	private User verifyAccount(String account, String password) {
		User user=Database.userQquery(account);
		if(user==null){
		System.out.println("”√ªß≤ª¥Ê‘⁄");}
		else if(!user.verifyPwd(password)){
			System.out.println("√‹¬Î¥ÌŒÛ");
			user =null;
		}
		return user;
}
}
