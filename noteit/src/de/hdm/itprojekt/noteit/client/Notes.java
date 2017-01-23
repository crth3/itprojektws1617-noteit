package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HorizontalPanel;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class Notes extends HorizontalPanel {

	private final NotesAdministrationAsync notesAdmin = ClientsideSettings.getAdministrationService();

	final CellList<Note> noteCellList = new NoteCellList().createNoteCellList();
	Note note = new Note();

}
