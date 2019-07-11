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
	private JButton buttonConfirm = new JButton("ȷ��");
	private JButton buttonCancel = new JButton("ȡ��");

	public TicketBookingGUI(User user, Shuttle shuttle) {
		this.ticket.setUser(user);
		this.ticket.setShuttle(shuttle);
		this.ticket.setId(shuttle);

		/*
		 * 
		 */
		this.setTitle("��Ʊ��Ϣ");
		this.setLocation(500, 300);
		this.setSize(300, 160);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1, 0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4, 0, 0));
		panel.add(new JLabel("�û���"));
		panel.add(new JLabel(user.getName()));
		panel.add(new JLabel("ѧ��"));
		panel.add(new JLabel(user.getStuNum()));
		this.add(panel);


		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4, 0, 0));
		panel.add(new JLabel("���"));
		panel.add(new JLabel(shuttle.getRoute().starting));
		panel.add(new JLabel("�յ�"));
		panel.add(new JLabel(shuttle.getRoute().ending));
		this.add(panel);

		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel("ʱ�� "));
		Date date = shuttle.getDate();
		panel.add(new JLabel("" + (date.getYear() + 1900) + "�� "
				+ date.getMonth() + " �� " + date.getDate() + " �� "
				+ date.getHours() + " ʱ " + date.getMinutes() + "�� "));
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
				JOptionPane.showMessageDialog(this, "��Ʊ�ɹ�");
			} else {
				System.out.println(ticket.getId());
				JOptionPane.showMessageDialog(this, "��Ʊʧ��");
			}
		} else if (e.getSource() == buttonCancel) {
			System.out.println(">>>>>>>>>>>>> ȡ���ɹ� <<<<<<<<<<<<<<<");
		}

		dispose();
	}

	private boolean handleTicketBook(long shuttleId, long userId,long ticketId) {
		return CPYDDatabase.ticketBook(shuttleId, userId,ticketId);
	}

	public static void main(String[] args) {
		User user = new User();
		user.setId(100001);
		user.setName("����");
		user.setStuNum(Integer.toString(10001));

		Route route = new Route();
		route.id = 1;
		route.starting = "�д�";
		route.ending = "�Ϸ�ѧԺ";
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
