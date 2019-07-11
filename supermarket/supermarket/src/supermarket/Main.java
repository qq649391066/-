package supermarket;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;



public class Main extends JFrame implements ActionListener{
	private JPanel p;
	private JMenuBar menuBar;
	private JMenu shop,person,provider;
	private JMenuItem addshop,findshop,addperson,permessage,addprovider,findprovider;
	private JLabel  label;
	private JButton btnNewButton;
	
	public Main() {
		super("超市管理");
		p=new JPanel();
		p.setBounds(0, 0, 582, 1);
		menuBar=new JMenuBar();
		this.setJMenuBar(menuBar);
		this.setResizable(false);
		shop=new JMenu("商品");
		person=new JMenu("员工");
		provider=new JMenu("供应商");
		
		menuBar.add(shop);
		menuBar.add(person);
		menuBar.add(provider);
		
		addshop=new JMenuItem("添加商品");
		findshop=new JMenuItem("商品信息");
		addperson=new JMenuItem("添加员工");
		permessage=new JMenuItem("查询员工");
		addprovider=new JMenuItem("添加供应商");
		findprovider=new JMenuItem("供应商信息");
		
		shop.add(addshop);
		addshop.addActionListener( this);
		shop.add(findshop);
		findshop.addActionListener( this);
		person.add(addperson);
		addperson.addActionListener( this);
		person.add(permessage);
		permessage.addActionListener( this);
		provider.add(addprovider);
		addprovider.addActionListener( this);
		provider.add(findprovider);
		findprovider.addActionListener( this);
		getContentPane().setLayout(null);
		p.setLayout(null);
		
		
		getContentPane().add(p);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 582, 50);
		getContentPane().add(toolBar);
		
		btnNewButton = new JButton();
		ImageIcon icon_1=new ImageIcon("src/image/5.jpg");
		btnNewButton.setIcon(icon_1);
		btnNewButton.setHideActionText(true);
		toolBar.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddGoods();
			}
			});
		
		JButton btnNewButton_2 = new JButton();
		ImageIcon icon_2=new ImageIcon("src/image/3.jpg");
		btnNewButton_2.setIcon(icon_2);
		btnNewButton_2.setHideActionText(true);
		toolBar.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new perMessage();
			}
			});
		
		JButton btnNewButton_3= new JButton();
		ImageIcon icon_3=new ImageIcon("src/image/6.jpg");
		btnNewButton_3.setIcon(icon_3);
		btnNewButton_3.setHideActionText(true);
		toolBar.add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Update();
			}
			});
		
		JButton btnNewButton_4= new JButton();
		ImageIcon icon_4=new ImageIcon("src/image/4.jpg");
		btnNewButton_4.setIcon(icon_4);
		btnNewButton_4.setHideActionText(true);
		toolBar.add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 47, 582, 330);
		lblNewLabel.setIcon(new ImageIcon("src/image/12.jpg"));
		getContentPane().add(lblNewLabel);
		this.setSize(600,450);
		this.setLocation(200,100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj==addshop){
			new AddGoods();
		}
		if(obj==findshop) {
		new findshop();
		}
		if(obj==addperson) {
			new addPerson();
		}
		if(obj==addprovider) {
			new addProvider();
		}
		if(obj==permessage) {
			new perMessage();
		}
		if(obj==findprovider) {
			new Update();
		}
	}
}