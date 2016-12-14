package de.hdm.itprojekt.noteit.client;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.AsyncBoxView;

import com.google.appengine.api.search.query.QueryParser.text_return;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;

public class ShowNote extends VerticalPanel {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	private static Logger rootLogger = Logger.getLogger("");

	static VerticalPanel vpShowNote = new VerticalPanel();
	static HorizontalPanel hpHeader = new HorizontalPanel();
	static VerticalPanel vpTitel = new VerticalPanel();
	static VerticalPanel hpNoteSubTitel = new VerticalPanel();
	static VerticalPanel hpNoteText = new VerticalPanel();
	static VerticalPanel hpNoteShare = new VerticalPanel();
	static VerticalPanel hpNotePermission = new VerticalPanel();
	static VerticalPanel hpBackButton = new VerticalPanel();
	static VerticalPanel hpNoteMaturity = new VerticalPanel();

	static Label lblHeaderTitel = new Label();
	static Label lblNoteTitel = new Label("Titel");
	static Label lblNoteSubTitel = new Label("Subtitel");
	static Label lblNoteShare = new Label("Teilen mit");
	static Label lblNoteText = new Label("Deine Notiz");
	static Label lblNoteMaturity = new Label("Faelligkeitsdatum");

	static TextBox tbNoteSubTitel = new TextBox();
	static TextBox tbNoteTitel = new TextBox();
	static TextBox tbNoteShare = new TextBox();
	static TextBox tbMaturity = new TextBox();

	static Button btnSaveNote = new Button("Speichern");

	static RichTextArea content = new RichTextArea();
	
	static Note currentNote = new Note();
	

	// Timestamp maturity = new Timestamp();

	// Date maturity = new Date();

	// modificationdate

	@Override
	protected void onLoad() {
		
		
		hpHeader.setStyleName("headerDetailView");
		lblHeaderTitel.setStyleName("lblHeaderTitel");
		vpShowNote.setStyleName("showDetailContent");
		
		
		hpHeader.add(lblHeaderTitel);
		vpShowNote.setWidth("600px");
		/**
		 * Create the Panel, Label and TextBox
		 */

		vpTitel.add(lblNoteTitel);
		vpTitel.add(tbNoteTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteSubTitel.add(lblNoteSubTitel);
		hpNoteSubTitel.add(tbNoteSubTitel);

		/**
		 * Create the Panel, Label and TextBox
		 */

		hpNoteShare.add(lblNoteShare);
		hpNoteShare.add(tbNoteShare);

		hpNoteText.add(lblNoteText);
		hpNoteText.add(content);

		hpNoteMaturity.add(lblNoteMaturity);
		hpNoteMaturity.add(tbMaturity);

		hpBackButton.add(btnSaveNote);

		vpShowNote.add(vpTitel);
		vpShowNote.add(hpNoteSubTitel);
		vpShowNote.add(hpNoteText);
		vpShowNote.add(hpNoteMaturity);
		vpShowNote.add(hpBackButton);

		vpShowNote.add(vpTitel);
		vpShowNote.add(hpNoteSubTitel);
		vpShowNote.add(hpNoteText);
		vpShowNote.add(hpNoteMaturity);
		vpShowNote.add(hpBackButton);
		
		this.add(hpHeader);
		this.add(vpShowNote);
		

	}
	
	

	public static void showNote(Note note) {
		 currentNote = note;
		rootLogger.log(Level.SEVERE, "objekt: " + note.getTitle());
		lblHeaderTitel.setText(note.getTitle());
		tbNoteTitel.setText(note.getTitle());
		tbNoteSubTitel.setText(note.getSubTitle());
		content.setText(note.getText());
		content.setText(note.getText());
		lblNoteTitel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		tbMaturity.setText(note.getMaturityDate().toString());
		
		btnSaveNote.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
						
				notesAdmin.createNote(tbNoteTitel.getText(), tbNoteSubTitel.getText(), content.getText(), null, Homepage.getCurrentUser(), null, currentNote.getNotebookId(), new AsyncCallback<Note>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Note result) {
						// TODO Auto-generated method stub
						
					}
				});
		
			}
		});

	}
	
	public static void setNote(Note newNote){
		currentNote = newNote;
	}
	

}
