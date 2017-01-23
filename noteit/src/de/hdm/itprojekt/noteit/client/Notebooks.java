package de.hdm.itprojekt.noteit.client;


import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class Notebooks extends HorizontalPanel {

	private final NotesAdministrationAsync notesAdmin = ClientsideSettings.getAdministrationService();

	final CellList<Notebook> notebookCellList = new NotebookCellList()
			.createNotebookCellList();
	Notebook notebook = new Notebook();

	public Notebook createNotebooks(String title, String subtitle, final User creator) {

		notesAdmin.createNotebook(title, creator,
				new AsyncCallback<Notebook>() {

					@Override
					public void onSuccess(Notebook result) {
						notebook = result;
						System.out.println("Notebook created");
						System.out.println(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error" + caught);

					}
				});

		return notebook;
	}
	
}
