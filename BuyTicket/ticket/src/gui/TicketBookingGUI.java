package gui;

import info.Route;
import info.Shuttle;
import info.Ticket;
import info.User;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.CPYDDatabase;

public class TicketBookingGUI extends JFrame implements ActionListener {
	
	private Ticket ticket = new Ticket();
	private JButton buttonConfirm = new JButton("确认");
	private JButton buttonCancel = new JButton("取消");

	public TicketBookingGUI(User user, Shuttle shuttle) {
		this.ticket.setUser(user);
		this.ticket.setShuttle(shuttle);
		this.ticket.setId(shuttle);

		/*
		 * 
		 */
		this.setTitle("车票信息");
		this.setLocation(500, 300);
		this.setSize(300, 160);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1, 0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4, 0, 0));
		panel.add(new JLabel("用户名"));
		panel.add(new JLabel(user.getName()));
		panel.add(new JLabel("学号"));
		panel.add(new JLabel(user.getStuNum()));
		this.add(panel);


		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4, 0, 0));
		panel.add(new JLabel("起点"));
		panel.add(new JLabel(shuttle.getRoute().starting));
		panel.add(new JLabel("终点"));
		panel.add(new JLabel(shuttle.getRoute().ending));
		this.add(panel);

		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("时间 "));
		Date date = shuttle.getDate();
		panel.add(new JLabel("" + (date.getYear() + 1900) + "年 "
				+ date.getMonth() + " 月 " + date.getDate() + " 日 "
				+ date.getHours() + " 时 " + date.getMinutes() + "分 "));
		this.add(panel);

		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		panel.add(buttonConfirm);
		panel.add(buttonCancel);
		buttonConfirm.addActionListener(this);
		buttonCancel.addActionListener(this);
		this.add(panel);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonConfirm) {
			if (handleTicketBook(ticket.getShuttle().getId(), ticket.getUser()
					.getId(),ticket.getId())) {
				JOptionPane.showMessageDialog(this, "订票成功");
			} else {
				System.out.println(ticket.getId());
				JOptionPane.showMessageDialog(this, "订票失败");
			}
		} else if (e.getSource() == buttonCancel) {
			System.out.println(">>>>>>>>>>>>> 取消成功 <<<<<<<<<<<<<<<");
		}

		dispose();
	}

	private boolean handleTicketBook(long shuttleId, long userId,long ticketId) {
		return CPYDDatabase.ticketBook(shuttleId, userId,ticketId);
	}

	public static void main(String[] args) {
		User user = new User();
		user.setId(100001);
		user.setName("张三");
		user.setStuNum(Integer.toString(10001));

		Route route = new Route();
		route.id = 1;
		route.starting = "中大";
		route.ending = "南方学院";
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 9, 1, 10, 30); 
		Date date = calendar.getTime();
		Shuttle shuttle = new Shuttle(50);
		shuttle.setRoute(route);
		shuttle.setDate(date);
		shuttle.setFee(22);
		shuttle.setId(1);

		new TicketBookingGUI(user, shuttle);

	}

}
