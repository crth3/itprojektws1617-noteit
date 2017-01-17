package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;
import de.hdm.itprojekt.noteit.shared.bo.User;

public class UrlView extends DialogBox {
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	Label label = new Label("Verfuegbare Notebooks:");
	static Label lblUrl = new Label("Url");
	static Label lblNoteTitel = new Label("Titel");
	static Label lblNoteDate = new Label();
	static Label lblNoteSubTitel = new Label("Subtitel");
	static Label lblNoteText = new Label("Deine Notiz");
	static Label lblNoteMaturity = new Label("Fälligkeitsdatum");

	TextArea url = new TextArea();
	TextBox titel = new TextBox();
	TextBox subtitel = new TextBox();
	ListBox lbNotebook = new ListBox();

	ArrayList<Integer> idList = new ArrayList<Integer>();

	public UrlView(User currentUser) {
		// Set the dialog box's caption.
		setText("Notiz erstellen");
		url.setPixelSize(400, 20);
		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		// DialogBox is a SimplePanel, so you have to set its widget
		// property to whatever you want its contents to be.
		Button btnSave = new Button("Speichern");
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				notesAdmin.createNote(titel.getText(), "", url.getText(),
						null, Homepage.getCurrentUser(), null, getNotebookId(lbNotebook.getSelectedIndex()),
						new AsyncCallback<Note>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Note result) {
								NoteitCellBrowser.getNoteList(result);
								Noteit.deleteStorage();
								

							}
						});
				UrlView.this.hide();
			}
		});
		notesAdmin.getAllNotebooksByUserID(Homepage.getCurrentUser().getId(), new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				idList.clear();
				int i = 0;
				for (Notebook notebook : result) {
					if (notebook.getUserId() == Homepage.getCurrentUser().getId()) {

						lbNotebook.addItem(notebook.getTitle());
						// Window.alert("Item Count " +
						// lbNotebook.getItemCount() + notebook.getId());
						idList.add(i, notebook.getId());
						i++;
					}

				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		url.setText(Noteit.getValue_URL());

		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("500");
		panel.setWidth("500");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		panel.add(lblNoteTitel);
		panel.add(titel);
		panel.add(label);
		panel.add(lbNotebook);
		panel.add(lblUrl);
		panel.add(url);
		panel.add(btnSave);

		setWidget(panel);

	}

	private Integer getNotebookId(int lbID) {
		return idList.get(lbID);
		//Window.alert("Notebook ID " + (idList.get(lbID)));
	}

}
