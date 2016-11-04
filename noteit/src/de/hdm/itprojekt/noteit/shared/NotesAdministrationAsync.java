package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.itprojekt.noteit.shared.bo.User;

public interface NotesAdministrationAsync {

	void createUser(String email, String firstName, String lastName, AsyncCallback<User> callback);

}
