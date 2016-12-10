package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tools.ant.taskdefs.Delete;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class EditNotebook extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	private static Logger rootLogger = Logger.getLogger("");

	static VerticalPanel vpEditNotebook = new VerticalPanel();

	static VerticalPanel vpTitel = new VerticalPanel();
	static VerticalPanel hpNoteSubTitel = new VerticalPanel();
	static VerticalPanel hpNoteText = new VerticalPanel();
	static VerticalPanel hpNoteShare = new VerticalPanel();
	static VerticalPanel hpNotePermission = new VerticalPanel();
	static VerticalPanel hpBackButton = new VerticalPanel();
	static VerticalPanel hpNoteMaturity = new VerticalPanel();

	static Label lblNotebookTitel = new Label("Titel");
	static Label lblNotebookSubTitel = new Label("Subtitel");
	static Label lblNotebookShare = new Label("Teilen mit");
	static Label lblNoteMaturity = new Label("Faelligkeitsdatum");

	static TextBox tbNotebookSubTitel = new TextBox();
	static TextBox tbNotebookTitel = new TextBox();
	static TextBox tbNotebookShare = new TextBox();
	static TextBox tbMaturity = new TextBox();

	static Button btnNotebookSave = new Button("Speichern");

	static RichTextArea content = new RichTextArea();

	// Timestamp maturity = new Timestamp();

	// Date maturity = new Date();

	// modificationdate

	@Override
	protected void onLoad() {

		vpEditNotebook.setWidth("600px");
		/**
		 * Create the Panel, Label and TextBox
		 */

		vpTitel.add(lblNotebookTitel);
		vpTitel.add(tbNotebookTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteSubTitel.add(lblNotebookSubTitel);
		hpNoteSubTitel.add(tbNotebookSubTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteShare.add(lblNotebookShare);
		hpNoteShare.add(tbNotebookShare);

		hpNoteMaturity.add(lblNoteMaturity);
		hpNoteMaturity.add(tbMaturity);

		hpBackButton.add(btnNotebookSave);

		vpEditNotebook.add(vpTitel);
		vpEditNotebook.add(hpNoteSubTitel);
		// vpEditNotebook.add(hpNoteText);
		vpEditNotebook.add(hpNoteMaturity);
		vpEditNotebook.add(hpBackButton);

		vpEditNotebook.add(vpTitel);
		vpEditNotebook.add(hpNoteSubTitel);
		// vpEditNotebook.add(hpNoteText);
		vpEditNotebook.add(hpNoteMaturity);
		vpEditNotebook.add(hpBackButton);

		this.add(vpEditNotebook);

		btnNotebookSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				notesAdmin.createNotebook(tbNotebookTitel.getText(), Homepage.getCurrentUser(), new AsyncCallback<Notebook>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Notebook result) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
	}

	public static void setNotebook(Notebook notebook) {
		tbNotebookTitel.setText(notebook.getTitle());
		tbMaturity.setText(notebook.getText());

	}
}
