package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.noteit.shared.bo.*;

public class NoteCellList extends Widget {

	private Note SelectedNote;

	CellList<Note> noteCellList = null;

	public CellList<Note> createNoteCellList() {

		// Create a KeyProvider.
		ProvidesKey<Note> noteKeyProvider = new ProvidesKey<Note>() {
			public Object getKey(Note item) {
				return (item == null) ? null : item.getTitle();
			}
		};

		// Create a cell to render each value.
		NoteCell noteCell = new NoteCell();

		// Use the cell in a CellList.
		noteCellList = new CellList<Note>(noteCell, noteKeyProvider);

		// Set the width of the CellList.
		// noteCellList.setWidth("230px");

		// Stylen der CellList
		noteCellList.setStylePrimaryName("CellList");

		// Set a key provider that provides a unique key for each contact. If
		// key is
		// used to identify contacts when fields (such as the name and address)
		// change.
		noteCellList.setPageSize(30);
		noteCellList
				.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		noteCellList.setKeyboardSelectionPolicy(
				KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		// Add a selection model so we can select cells.
		final SingleSelectionModel<Note> noteSelectionModel = new SingleSelectionModel<Note>(
				noteKeyProvider);
		noteCellList.setSelectionModel(noteSelectionModel);
		noteSelectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						// contactForm.setContact(selectionModel.getSelectedObject());
						SelectedNote = noteSelectionModel.getSelectedObject();
						Homepage.setSelectedNote(noteSelectionModel.getSelectedObject());
						ShowNote.showNote(noteSelectionModel.getSelectedObject());

					}
				});

		return noteCellList;

	}

	public Note getSelectedNote() {

		return SelectedNote;

	}

}
