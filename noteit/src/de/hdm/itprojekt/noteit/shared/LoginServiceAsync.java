package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.itprojekt.noteit.client.LoginInfo;

public interface LoginServiceAsync {
	public void login(String requestUri, AsyncCallback<LoginInfo> async);
}
