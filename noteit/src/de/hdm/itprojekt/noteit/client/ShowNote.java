package de.hdm.itprojekt.noteit.client;



import java.sql.Date;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Note;

public class ShowNote {
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	private static Logger rootLogger = Logger.getLogger("");
	
	
	static VerticalPanel vpShowNote = new VerticalPanel();

	static VerticalPanel vpTitel = new VerticalPanel();
	static VerticalPanel hpNoteSubTitel = new VerticalPanel();
	static VerticalPanel hpNoteText = new VerticalPanel();
	static HorizontalPanel hpNoteShare = new HorizontalPanel();
	static HorizontalPanel hpNotePermission = new HorizontalPanel();
	static HorizontalPanel hpAddCloseButtons = new HorizontalPanel();
	static HorizontalPanel hpNoteMaturity = new HorizontalPanel();
	
	
	
	 static Label lblNoteTitel = new Label("Titel");
	 static Label lblNoteSubTitel = new Label("Subtitel");
	 static Label lblNoteShare = new Label("Teilen mit");
	 static Label lblNoteText = new Label("Deine Notiz");
	 Label lblNoteMaturity = new Label("Fälligkeitsdatum");

	
	static TextBox tbNoteSubTitel = new TextBox();
	static TextBox tbNoteTitel = new TextBox();
	static TextBox tbNoteShare = new TextBox();


	Button btnNoteClose = new Button("Abbrechen");
	
	static RichTextArea content = new RichTextArea();
	
	//Timestamp maturity = new Timestamp();
	
	//Date maturity = new Date();
	
	//modificationdate
	

		
	
	public static void showNote(Note note){
		rootLogger.log(Level.SEVERE, "objekt:" + note.getTitle());
		
		tbNoteTitel.setText(note.getTitle());
		tbNoteSubTitel.setText(note.getSubTitle());
		content.setText(note.getText());
		content.setText(note.getText());
		lblNoteTitel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		vpShowNote.add(vpTitel);
		vpShowNote.add(hpNoteSubTitel);
		vpShowNote.add(hpNoteText);
//		vpShowNote.add(hpNoteMaturity);
//			hpNoteMaturity.add(lblNoteMaturity);
//			hpNoteMaturity.add(datePicker);

//		vpShowNote.add(hpAddCloseButtons);
//			hpAddCloseButtons.add(btnNoteClose);
//			hpAddCloseButtons.add(btnAddNewNote);

		//vpShowNote.setSpacing(40);
		
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

		
			
		Homepage.showCurrentNote(vpShowNote);
	
		
		
		
	}
	
}
