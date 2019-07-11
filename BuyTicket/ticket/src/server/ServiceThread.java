package server;
import info.Shuttle;
import info.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import messages.Message;
import messages.MessageExample;
import messages.MessageLoginAck;
import messages.MessageLoginReq;
import messages.MessageShuttleQuery;
import messages.MessageShuttleQueryAck;
import messages.MessageTicketBook;
import messages.MessageTicketBookAck;
import database.CPYDDatabase;
public class ServiceThread extends Thread {
	private Socket socket = null;
	private ObjectOutputStream oos = null;

	public ServiceThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		handleClient();
		closeClient();
	}

	private void handleClient() {
		OutputStream os = null;
		InputStream is = null;
		ObjectInputStream ois = null;

		try {
			// 创建对象数据流
			os = socket.getOutputStream();
			is = socket.getInputStream();
			oos = new ObjectOutputStream(os);
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		//处理来自客户端的消息对象
		while (true) {
			// 从对象输入数据流中读取数据对象
			Message msgReceived = null;
			try {
				msgReceived = (Message) ois.readObject();
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (IOException e) { // 出错,停止对此Socket的处理
				System.out.println(e.getMessage());
				return;
			}

			// 按消息类型处理
			switch (msgReceived.msgType) {
			case MSG_EXAMPLE:
				handleMsgExample((MessageExample) msgReceived, oos);
				break;
			case MSG_LOGIN_REQ:
				handleLoginRequest((MessageLoginReq) msgReceived, oos);
				break;
			case MSG_SHUTTLE_QUERY:
				handleShuttleQuery((MessageShuttleQuery) msgReceived, oos);
				break;
			case MSG_TICKET_BOOK_REQ:
				handleTicketBook((MessageTicketBook) msgReceived, oos);
				break;
			default:
				System.out.println("Uknown message received: "
						+ msgReceived.msgType);
			}
		}
	}

	private void closeClient() {
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

	private void handleLoginRequest(MessageLoginReq msgLogin,
			ObjectOutputStream oos) {
		boolean verifyPassed = false;
		User user = CPYDDatabase.userQquery(msgLogin.getAccount());

		MessageLoginAck msgLoginAck = new MessageLoginAck(msgLogin.getAccount());
		// 登陆成功的条件:用户账号存在,并且不是管理员,密码正确
		if (!(msgLogin.getAccount().equals("admin"))
				&& user.getName().equals(msgLogin.getAccount())
				&& msgLogin.verify(user.getPwd())) {
			verifyPassed = true;
			msgLoginAck.setFailReason("用户信息正确!");
		} else {
			msgLoginAck.setFailReason("用户信息不正确!");
		}
		System.out.println("Account: " + msgLogin.getAccount()
				+ " Verification " + verifyPassed);

		msgLoginAck.setLoginResult(verifyPassed);
		msgLoginAck.setUser(user);

		sendMessage(msgLoginAck, oos);
	}

	private void handleShuttleQuery(MessageShuttleQuery msgShuttleQuery,
			ObjectOutputStream oos) {
		Vector<Shuttle> shuttles = CPYDDatabase.shuttleQquery(
				msgShuttleQuery.getStarting(), msgShuttleQuery.getEnding(),
				msgShuttleQuery.getYear(), msgShuttleQuery.getMonth(),
				msgShuttleQuery.getDate());

		MessageShuttleQueryAck msgAck = new MessageShuttleQueryAck();

		msgAck.setShuttles(shuttles);

		sendMessage(msgAck, oos);
	}

	private void handleTicketBook(MessageTicketBook msgTicketBook,
			ObjectOutputStream oos) {
		MessageTicketBookAck msgAck = new MessageTicketBookAck(
				msgTicketBook.getShuttleId(), msgTicketBook.getUserId(),msgTicketBook.getId());
		if (CPYDDatabase.ticketBook(msgTicketBook.getShuttleId(),
				msgTicketBook.getUserId(),msgTicketBook.getId())) {
			msgAck.setSuccess(true);
		}

		sendMessage(msgAck, oos);
	}
}

