package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

import database.CPYDDatabase;
import info.*;

public class TicketBooker extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//������
	public static void main(String[] args)
	{
		User user = new User();
		user.getId();
		user.getName();
		user.getStuNum();
		new TicketBooker(user);
	}
	
	private User user = new User();
	private Vector <Shuttle> searchResult = new Vector <Shuttle>();
	
	//·�� -��㡢�յ㡢���ڣ�ʹ�������˵�
	private static final String arrayPlace[] = {"�д�","�Ϸ�ѧԺ"};
	private static final String arrayYear[] = {"2016","2017","2018","2019","2020"
		};
	private static final String arrayMonth[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	private static final String arrayDay[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	
	//���
	private JTable resultTable = null;
	private DefaultTableModel tableModelDefault;
	private JLabel jl1 = new JLabel("·��");
	private JComboBox<String> jc_start =new JComboBox<String>(arrayPlace);
	private JComboBox<String> jc_end =new JComboBox<String>(arrayPlace);
	private JComboBox<String> jc_year =new JComboBox<String>(arrayYear);
	private JComboBox<String> jc_month =new JComboBox<String>(arrayMonth);
	private JComboBox<String> jc_day =new JComboBox<String>(arrayDay);
	private JButton jbSearch = new JButton("��ѯ");
	private JButton jbBook = new JButton("Ԥ��");
	
	// ���췽��
	@SuppressWarnings("deprecation")
	public TicketBooker(User user) 
	{	
		this.setBounds(400,100,400,600);// ����x,y,width,height��һ��Ҫ����
		this.setTitle("��ƱԤ��");// ���ñ���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.user= user;
		/*
		 * �ϲ����
		 */
		JPanel upper_jp = new JPanel();
		upper_jp.setBounds(0, 0, 400, 100);
		
		//upper_jp1����
		upper_jp.setLayout(new FlowLayout());
		upper_jp.add(jl1);
		jc_start.setToolTipText("���");    		//����������ϻ�����Ӧ��ʾ
		upper_jp.add(jc_start);
		jc_end.setToolTipText("�յ�");
		upper_jp.add(jc_end);
		jc_year.setToolTipText("��");
		upper_jp.add(jc_year);
		jc_month.setToolTipText("��");
		upper_jp.add(jc_month);
		jc_day.setToolTipText("��");
		upper_jp.add(jc_day);
		//���õ�ǰ����
				Calendar ca = Calendar.getInstance();
				Date date = ca.getTime();
    			jc_year.setSelectedIndex(date.getYear() + 1900 - Integer.parseInt(arrayYear[0]));
				jc_month.setSelectedIndex(date.getMonth());
				jc_day.setSelectedIndex(date.getDate() - 1);
		upper_jp.add(jbSearch);
		ToolTipManager.sharedInstance().setInitialDelay(200);   //��ʾ����ʱ�����ã�0.2��
		this.add(upper_jp);
		/* 
		 * �в����
		 */
		JPanel middle_jp = new JPanel();
		middle_jp.setBounds(0, 100, 400, 400);
		//middle_jp ����
		middle_jp.setLayout(new BorderLayout());
		//��ʼ��resultTable
		createResultTable();
		JScrollPane sc = new JScrollPane(resultTable);
		middle_jp.add(sc);
		this.add(middle_jp);
		/*
		 * �²����
		 */
		JPanel bottom_jp = new JPanel();
		bottom_jp.setBounds(0, 500, 400, 70);
		//bottom_jp����
		bottom_jp.setLayout(new BorderLayout());
		bottom_jp.add(jbBook,BorderLayout.CENTER);
		this.add(bottom_jp);
		
		jbSearch.addActionListener(this);
		jbBook.addActionListener(this);
		
		this.setVisible(true);// ���ý���ɼ���Ĭ��false
	}
	private void createResultTable()
	{
		if(resultTable != null)
			return;
		Object[][] data = {};
		//JTable������Ϊʱ�䣬Ʊ�ۣ����г�Ʊ����ѡ���κ�һ�к󼴿ɵ�����Ԥ������ť
		String[] name = {"ʱ��","Ʊ��","ʣ��Ʊ��"};
		 tableModelDefault = new DefaultTableModel(data,name);
		 resultTable = new JTable(tableModelDefault);
		 //����Ϊ��ѡ
		 resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	private void updateResultTable (Vector <Shuttle> searchResult)
	{
		if(resultTable == null || tableModelDefault == null)
		return;
		//��ղ�ѯ���
		tableModelDefault.setRowCount(0);
		for(int i = 1; i <= searchResult.size(); i++)
		{
			Shuttle shuttle = searchResult.get(i-1);
			//���ӵ� i ��			
			String time = "" + shuttle.getDate().getHours() + "ʱ" + shuttle.getDate().getMinutes() + "��";
			Object data[] = { time, shuttle.getFee(), shuttle.getSeating()};
			tableModelDefault.addRow(data);
		}
	}
	
	private Vector <Shuttle> shuttleSearch()
	{
		String starting = (String) jc_start.getSelectedItem();
		String ending = (String) jc_end.getSelectedItem();
		int year = Integer.parseInt((String) jc_year.getSelectedItem());
		int month = Integer.parseInt((String) jc_month.getSelectedItem());
		int day = Integer.parseInt((String) jc_day.getSelectedItem());
		Vector <Shuttle> searchResult = CPYDDatabase.shuttleQquery(starting, ending, year, month, day);
		//��ѯ�Ƿ��а೵
		//����̶����Σ�ÿ�춼����ͬ��ʱ�̵ĳ���
		//�д�->�Ϸ�ѧԺ��10:30
		//�Ϸ�ѧԺ->�д�12:30
		//����ϴβ�ѯ��¼
		//searchResult.clear();
		Shuttle shuttle = new Shuttle(50);
//		Calendar ca = Calendar.getInstance();
//		int hour = shuttle.getHourOfDay();
//		int minute = shuttle.getMinute();
//		shuttle.setFee(22);
//		shuttle.getId();
//		shuttle.getRoute();
//		searchResult.add(shuttle);
		System.out.println(shuttle.getDate());
		return searchResult;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbSearch)
		{
			searchResult = shuttleSearch();
			updateResultTable(searchResult);
		}
		else if(e.getSource() == jbBook)
		{
			//resultTable �� searchResult �������ǰ�˳��һһ��Ӧ��
			int selectedRow = resultTable.getSelectedRow();
			//����Ƿ�ѡ��ĳ���೵
			if(selectedRow < 0)
			{
				JOptionPane.showMessageDialog(this,"��ѡ�񳵴Σ���ѡ��");
			}
			else
			{
				System.out.println(searchResult.size());
				System.out.println(searchResult.get(selectedRow).getSeating());
				new TicketBookingGUI(user, searchResult.get(selectedRow));
			}
		}
	}
}
