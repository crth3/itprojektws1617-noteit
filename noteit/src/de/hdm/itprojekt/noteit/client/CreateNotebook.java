package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class CreateNotebook extends DialogBox {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	User user = new User();

	HorizontalPanel vpAddNewNotebookPanel = new HorizontalPanel();

	Label lblTitleAddNotebook = new Label("Name des Notizbuches");
	
	TextBox tbAddNewNotebook = new TextBox();
	
	Button btnAddNewNotebook = new Button("Notizbuch erstellen");
	Button btnNotebookClose = new Button("abbrechen");

	/**
	 * Konstruktor mit Userobjekt als Übergabeparameter
	 * 
	 * @param user
	 */
	public CreateNotebook(User user) {
		this.user = user;
	}

	public void onLoad() {

		setAutoHideEnabled(true);
		setGlassEnabled(true);
		setText("Neuen Notizbuch erstellen");
		setGlassEnabled(true);
		center();

		vpAddNewNotebookPanel.add(lblTitleAddNotebook);
		vpAddNewNotebookPanel.add(tbAddNewNotebook);
		vpAddNewNotebookPanel.add(btnAddNewNotebook);
		vpAddNewNotebookPanel.add(btnNotebookClose);

		vpAddNewNotebookPanel.setSpacing(40);
		setWidget(vpAddNewNotebookPanel);

		/**
		 * Create new Notebook
		 */
		btnAddNewNotebook.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				createNewNotebook(tbAddNewNotebook.getText(), user);
			}
		});

		/**
		 * Create new Notebook
		 */
		tbAddNewNotebook.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				createNewNotebook(tbAddNewNotebook.getText(), user);
			}
		});
		
		/**
		 * close DialogeBox
		 */
		btnNotebookClose.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				CreateNotebook.this.hide();
			}
		});

	}

	/**
	 * Create new Notebook RPC
	 * Update notebook celllist
	 * close Dialoge Box
	 */
	public void createNewNotebook(String notebookTitle, final User user) {
		notesAdmin.createNotebook(notebookTitle, user, new AsyncCallback<Notebook>() {

			@Override
			public void onSuccess(Notebook result) {
				CreateNotebook.this.hide();
				Homepage.updateNotebookCellList(user.getId());
				CreateNotebook.this.hide();
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	// HorizontalPanel titelPanel = new HorizontalPanel();
	// Label titel = new Label("Titel");
	// TextBox titelTextBox = new TextBox();
	// titelPanel.add(titel);
	// titelPanel.add(titelTextBox);
	//
	// HorizontalPanel teilenPanel = new HorizontalPanel();
	// Label teilen = new Label("Teilen mit");
	// TextBox teilenTextBox = new TextBox();
	// Button hinzufuegenButton = new Button("+");
	// teilenPanel.add(teilen);
	// teilenPanel.add(teilenTextBox);
	// teilenPanel.add(hinzufuegenButton);
	//
	// HorizontalPanel berechtigungsPanel = new HorizontalPanel();
	// Label darf = new Label("darf:");
	// RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup",
	// "anzeigen + bearbeiten");
	// RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup",
	// "anzeigen");
	// // RadioButton berechtigungen1 = new RadioButton("myRadioGroup",
	// "foo");
	// berechtigungsPanel.add(rbBerechtigungen1);
	// berechtigungsPanel.add(rbBerechtigungen2);
	//
	// HorizontalPanel buttonPanel = new HorizontalPanel();
	// Button loeschen = new Button("Löschen");
	// Button abbrechen = new Button("Abbrechen");
	// Button sichern = new Button("Sichern");
	// buttonPanel.add(loeschen);
	// buttonPanel.add(abbrechen);
	// buttonPanel.add(sichern);
	//
	// this.add(titelPanel);
	// this.add(teilenPanel);
	// this.add(berechtigungsPanel);
	// this.add(buttonPanel);

}
