package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.noteit.shared.bo.*;

public class NotebookCellList extends Widget {

	private Notebook selectedNotebook;

	CellList<Notebook> notebookCellList = null;

	public CellList<Notebook> createNotebookCellList() {

		// Create a KeyProvider.
		ProvidesKey<Notebook> notebookKeyProvider = new ProvidesKey<Notebook>() {
			public Object getKey(Notebook item) {
				return (item == null) ? null : item.getTitle();
			}
		};

		// Create a cell to render each value.
		NotebookCell notebookCell = new NotebookCell();

		// Use the cell in a CellList.
		notebookCellList = new CellList<Notebook>(notebookCell,
				notebookKeyProvider);

		// Set the width of the CellList.
		// userCellList.setWidth("230px");

		// Stylen der CellList
		notebookCellList.setStylePrimaryName("CellList");

		// Set a key provider that provides a unique key for each contact. If
		// key is
		// used to identify contacts when fields (such as the name and address)
		// change.
		notebookCellList.setPageSize(30);
		notebookCellList
				.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		notebookCellList.setKeyboardSelectionPolicy(
				KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		// Add a selection model so we can select cells.
		final SingleSelectionModel<Notebook> notebookSelectionModel = new SingleSelectionModel<Notebook>(
				notebookKeyProvider);
		
		notebookCellList.setSelectionModel(notebookSelectionModel);
		
		notebookSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				// contactForm.setContact(selectionModel.getSelectedObject());
				Homepage.setNotesWhenNotebookSelected(notebookSelectionModel.getSelectedObject());
			}
		});

		return notebookCellList;

	}

	public Notebook getSelectedNotebook() {

		return selectedNotebook;

	}
	

}
