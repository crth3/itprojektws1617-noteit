package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;

import org.apache.tools.ant.taskdefs.Delete;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.*;

public class EditNotebook extends DialogBox {

	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);

	VerticalPanel vpEditNotebookPanel = new VerticalPanel();

	Notebook nb = new Notebook();
	User currentUser = new User();

	/**
	 * Create the Labels, Panels, Radio Buttons, Buttons and TexBox
	 */
	Label lbTitel = new Label("Titel");
	Label lbteilen = new Label("Teilen mit");
	Label lbDarf = new Label("Darf:");
	HorizontalPanel titelPanel = new HorizontalPanel();
	HorizontalPanel teilenPanel = new HorizontalPanel();
	HorizontalPanel hpButtonPanel = new HorizontalPanel();
	HorizontalPanel hpBerechtigungsPanel = new HorizontalPanel();
	RadioButton rbBerechtigungen1 = new RadioButton("myRadioGroup", "anzeigen + bearbeiten");
	RadioButton rbBerechtigungen2 = new RadioButton("myRadioGroup", "anzeigen");
	// RadioButton berechtigungen1 = new RadioButton("myRadioGroup", "foo");
	Button btnHinzufuegenButton = new Button("+");
	Button btnLoeschen = new Button("Löschen");
	Button btnAbbrechen = new Button("Abbrechen");
	Button btnSichern = new Button("Sichern");
	TextBox tbTitelTextBox = new TextBox();
	TextBox tbTeilenTextBox = new TextBox();

	/**
	 * Konstruktor mit Userobjekt als Übergabeparameter
	 * @param user
	 */
	public EditNotebook(User user) {
		this.currentUser = user;
	}

	public void onLoad() {

		nb = Homepage.selectedNotebook;
		nb.getTitle();
		nb.getNotebookID();

		setAutoHideEnabled(true);
		setGlassEnabled(true);
		setText("Notizbuch bearbeiten");
		center();

		/**
		 *	Add the Labels, Panels, Radio Buttons, Buttons and TexBox
		 */
		vpEditNotebookPanel.add(lbTitel);
		vpEditNotebookPanel.add(tbTitelTextBox);
		vpEditNotebookPanel.add(lbteilen);
		vpEditNotebookPanel.add(tbTeilenTextBox);
		vpEditNotebookPanel.add(lbDarf);
		vpEditNotebookPanel.add(hpBerechtigungsPanel);
		hpBerechtigungsPanel.add(rbBerechtigungen1);
		hpBerechtigungsPanel.add(rbBerechtigungen2);
		vpEditNotebookPanel.add(hpButtonPanel);
		hpButtonPanel.add(btnAbbrechen);
		hpButtonPanel.add(btnLoeschen);
		hpButtonPanel.add(btnSichern);

		vpEditNotebookPanel.setSpacing(40);
		setWidget(vpEditNotebookPanel);

		/**
		 * Hinzufügen des Titels zur Textbox
		 */
		tbTitelTextBox.setText(nb.getTitle());

		// ClickHandler für Löschen Button
		btnLoeschen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				notesAdmin.deleteNotebook(nb.getNotebookID(), nb.getUserId(), new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

					}
				});

			}
		});

		// ClickHandler für Abbrechen Button
		btnAbbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				EditNotebook.this.hide();

			}
		});

		// ClickHandler für Sichern Button
		btnSichern.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				notesAdmin.updateNotebook(tbTitelTextBox.getText(), nb.getId(), nb.getUserId(),
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Void result) {
								Homepage.updateNotebookCellList(nb.getUserId());
								EditNotebook.this.hide();
							}
						});

			}
		});

	}

}
