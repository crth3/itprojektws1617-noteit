package de.hdm.itprojekt.noteit.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.itprojekt.noteit.shared.bo.*;

/**
 * 
 * @author maikzimmermann
 *
 */

@RemoteServiceRelativePath("notesadministration")
public interface NotesAdministration extends RemoteService{
	
	public User createUser(String email,String firstName,String lastName)
		      throws IllegalArgumentException;

}
