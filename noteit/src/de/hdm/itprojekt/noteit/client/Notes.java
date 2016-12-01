package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class Notes extends HorizontalPanel {

	private final NotesAdministrationAsync notesAdmin = GWT
			.create(NotesAdministration.class);

	final CellList<Note> noteCellList = new NoteCellList().createNoteCellList();
	Note note = new Note();

	public Note createNote(String title, String subtitle, String text,
			Timestamp maturity, User u, String source) {

		notesAdmin.createNote(title, subtitle, text, maturity, u, source,
				new AsyncCallback<Note>() {

					@Override
					public void onSuccess(Note result) {
						note = result;
						System.out.println("Note created");
						System.out.println(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error" + caught);

					}
				});

		return note;
	}
}
