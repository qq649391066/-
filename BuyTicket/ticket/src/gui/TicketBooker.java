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
	//主函数
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
	
	//路线 -起点、终点、日期，使用下拉菜单
	private static final String arrayPlace[] = {"中大","南方学院"};
	private static final String arrayYear[] = {"2016","2017","2018","2019","2020"
		};
	private static final String arrayMonth[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	private static final String arrayDay[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	
	//组件
	private JTable resultTable = null;
	private DefaultTableModel tableModelDefault;
	private JLabel jl1 = new JLabel("路线");
	private JComboBox<String> jc_start =new JComboBox<String>(arrayPlace);
	private JComboBox<String> jc_end =new JComboBox<String>(arrayPlace);
	private JComboBox<String> jc_year =new JComboBox<String>(arrayYear);
	private JComboBox<String> jc_month =new JComboBox<String>(arrayMonth);
	private JComboBox<String> jc_day =new JComboBox<String>(arrayDay);
	private JButton jbSearch = new JButton("查询");
	private JButton jbBook = new JButton("预订");
	
	// 构造方法
	@SuppressWarnings("deprecation")
	public TicketBooker(User user) 
	{	
		this.setBounds(400,100,400,600);// 设置x,y,width,height，一般要设置
		this.setTitle("车票预订");// 设置标题
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.user= user;
		/*
		 * 上层面板
		 */
		JPanel upper_jp = new JPanel();
		upper_jp.setBounds(0, 0, 400, 100);
		
		//upper_jp1布局
		upper_jp.setLayout(new FlowLayout());
		upper_jp.add(jl1);
		jc_start.setToolTipText("起点");    		//鼠标放在组件上会有相应提示
		upper_jp.add(jc_start);
		jc_end.setToolTipText("终点");
		upper_jp.add(jc_end);
		jc_year.setToolTipText("年");
		upper_jp.add(jc_year);
		jc_month.setToolTipText("月");
		upper_jp.add(jc_month);
		jc_day.setToolTipText("日");
		upper_jp.add(jc_day);
		//设置当前日期
				Calendar ca = Calendar.getInstance();
				Date date = ca.getTime();
    			jc_year.setSelectedIndex(date.getYear() + 1900 - Integer.parseInt(arrayYear[0]));
				jc_month.setSelectedIndex(date.getMonth());
				jc_day.setSelectedIndex(date.getDate() - 1);
		upper_jp.add(jbSearch);
		ToolTipManager.sharedInstance().setInitialDelay(200);   //提示出现时间设置，0.2秒
		this.add(upper_jp);
		/* 
		 * 中层面板
		 */
		JPanel middle_jp = new JPanel();
		middle_jp.setBounds(0, 100, 400, 400);
		//middle_jp 布局
		middle_jp.setLayout(new BorderLayout());
		//初始化resultTable
		createResultTable();
		JScrollPane sc = new JScrollPane(resultTable);
		middle_jp.add(sc);
		this.add(middle_jp);
		/*
		 * 下层面板
		 */
		JPanel bottom_jp = new JPanel();
		bottom_jp.setBounds(0, 500, 400, 70);
		//bottom_jp布局
		bottom_jp.setLayout(new BorderLayout());
		bottom_jp.add(jbBook,BorderLayout.CENTER);
		this.add(bottom_jp);
		
		jbSearch.addActionListener(this);
		jbBook.addActionListener(this);
		
		this.setVisible(true);// 设置界面可见，默认false
	}
	private void createResultTable()
	{
		if(resultTable != null)
			return;
		Object[][] data = {};
		//JTable：依次为时间，票价，现有车票数，选中任何一行后即可单击【预订】按钮
		String[] name = {"时间","票价","剩余票数"};
		 tableModelDefault = new DefaultTableModel(data,name);
		 resultTable = new JTable(tableModelDefault);
		 //设置为单选
		 resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	private void updateResultTable (Vector <Shuttle> searchResult)
	{
		if(resultTable == null || tableModelDefault == null)
		return;
		//清空查询结果
		tableModelDefault.setRowCount(0);
		for(int i = 1; i <= searchResult.size(); i++)
		{
			Shuttle shuttle = searchResult.get(i-1);
			//增加第 i 行			
			String time = "" + shuttle.getDate().getHours() + "时" + shuttle.getDate().getMinutes() + "分";
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
		//查询是否有班车
		//这里固定车次，每天都有相同的时刻的车次
		//中大->南方学院：10:30
		//南方学院->中大：12:30
		//清空上次查询记录
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
			//resultTable 与 searchResult 的内容是按顺序一一对应的
			int selectedRow = resultTable.getSelectedRow();
			//检查是否选中某个班车
			if(selectedRow < 0)
			{
				JOptionPane.showMessageDialog(this,"请选择车次（单选）");
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
