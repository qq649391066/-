package server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import messages.Message;
import messages.MessageExample;

public class severAdmin extends JFrame implements ActionListener {

	public static void main(String[] args) {
		new severAdmin();
	}

	// ����Ԫ��
	private JTextField fieldAcount = new JTextField(20);
	private JPasswordField fieldPwd = new JPasswordField(20);
	private JButton buttonEnable = new JButton("����������");
	private JButton buttonDisable = new JButton("�رշ�����");

	// ���������Ϣ
	private ServerSocket serverSocket = null;
	private int serverPort = 54321;
	// ���񿪹�
	private boolean enableService = false;

	public severAdmin() {
		this.setTitle("Server");
		this.setLocation(300, 200);
		this.setSize(300, 120);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disableService();
				System.exit(0);
			}
		});

		// ����ҳ�沼��
		this.setLayout(new GridLayout(3, 2, 0, 0));

		this.add(new JLabel("�˺�"));
		this.add(fieldAcount);
		this.add(new JLabel("����"));
		this.add(fieldPwd);

		buttonDisable.setEnabled(false);
		this.add(buttonEnable);
		this.add(buttonDisable);

		buttonEnable.addActionListener(this);
		buttonDisable.addActionListener(this);

		this.setVisible(true);

		// ����������TCP�˿ںͷ���
		enableService();

		// �û�����رշ���ť,ϵͳ�˳�,�ر�serverSocket
		disableService();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonEnable) {
			String account = fieldAcount.getText();
			String password = new String(fieldPwd.getPassword());
			// �ж��û����������Ƿ���ȷ
			// ��¼�ɹ�������:�û��˺Ŵ���,������ȷ,����ԱȨ��
			if (handleLogin(account, password)) {

				//��ֹ��¼��ť,ʹ���˳���ť
				buttonEnable.setEnabled(false);
				buttonDisable.setEnabled(true);

				// ����ServerSocket
				enableService = true;
			} else {
				JOptionPane.showMessageDialog(this, "Invalid User");
				fieldPwd.setText("");
			}
		} else if (e.getSource() == buttonDisable) {
			// ϵͳ�˳�����,ϵͳ�˳�
			enableService = false;
			disableService();
			System.exit(0);
		}
	}

	private boolean handleLogin(String account, String password) {
		if (account.equals("admin") && password.equals("admin"))
			return true;
		return false;
	}

	public void enableService() {
		// �ȴ���������
		while (!enableService) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
			}
		}
		// ���ŷ���˿�
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// ʹ��while��������client�˶������,������û��ʹ���߳�,ͬʱֻ����һ���ͻ������ӷ�����
		while (enableService) {
			// �����˿�����,�ȴ�����
			try {
				// ͨ���˿ڽ�������
				Socket socket = serverSocket.accept();
				// �յ��ͻ�������,�����û�����
				System.out.println("Request received");
				handleClient(socket);
				
				// �ͻ����˳�
				closeClient(socket);
				socket = null;
			} catch (SocketTimeoutException e) {
				// do nothing
			} catch (IOException e) {
				e.printStackTrace();
			} finally { //ֹͣ����
			}
		}
	}

	private void disableService() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			serverSocket = null;
		}
	}

	private void handleClient(Socket socket) {
		OutputStream os = null;
		ObjectOutputStream oos = null;
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			// ��������������
			os = socket.getOutputStream();
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		//�������Կͻ��˵���Ϣ����
		while (enableService) {
			// �Ӷ��������������ж�ȡ���ݶ���
			Message msgReceived = null;
			try {
				msgReceived = (Message) ois.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (IOException e) { // Socket ����,ֹͣ�Դ�Socket�Ĵ���
				System.out.println(e.getMessage());
				return;
			} 
			
			// ����Ϣ���ʹ���
			switch (msgReceived.msgType) {
			case MSG_EXAMPLE:
				handleMsgExample((MessageExample) msgReceived, oos);
				break;

			default:
				System.out.println("Uknown message received: "
						+ msgReceived.msgType);
			}
		}
	}

	private void closeClient(Socket socket) {
		if (socket != null) {
			try {
				socket.shutdownOutput();
				socket.shutdownInput();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean sendMessage(Message msg, ObjectOutputStream oos) {
		try {
			oos.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void handleMsgExample(MessageExample msgExample,
			ObjectOutputStream oos) {
		String data = msgExample.getData();
		System.out.println(data);
		msgExample.setData("Example Message Received: " + data);
		sendMessage(msgExample, oos);
	}
}