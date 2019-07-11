package messages;
import java.io.Serializable;

import messages.Message.MSG_TYPE;

public class MessageLoginReq extends Message implements Serializable {
	private String account = null;
	private String pwd = null;

	public MessageLoginReq(String account, String pwd) {
		super(MSG_TYPE.MSG_LOGIN_REQ);
		this.account = account;
		this.pwd = pwd;
	}

	public String getAccount() {
		return account;
	}

	public boolean verify(String account, String pwd) {
		if (this.account.equals(account) && this.pwd.equals(pwd))
			return true;

		return false;
	}

	public boolean verify(String pwd) {
		if (pwd.equals(pwd))
			return true;

		return false;
	}
}
