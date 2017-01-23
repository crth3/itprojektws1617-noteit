package de.hdm.itprojekt.noteit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * 
 * @Entry point classes define <code>onModuleLoad()</code>. s
 */
public class NotesReport implements EntryPoint {

	HorizontalPanel headlinePanel = new HorizontalPanel();
	HorizontalPanel headerPanel = new HorizontalPanel();
	HorizontalPanel contentPanel = new HorizontalPanel();
	HorizontalPanel navPanel = new HorizontalPanel();
	VerticalPanel vpLeft = new VerticalPanel();
	VerticalPanel vpRight = new VerticalPanel();
	VerticalPanel vpBasisPanel = new VerticalPanel();
	Button bGeneral = new Button("Allgemeine Informationen zu Notizen");
	Button bSharing = new Button("Informationen zu Notizen und Berechtigungen eines Nutzers");
	Label headlineLabel = new Label("NoteIt Report");

	@Override
	/**
	 * Jedes GWT Widget wird mit der onModuleLoad- Methode aufgerufen. Sie gibt
	 * an, was geschehen soll, wenn eine Widget-Instanz zur Anzeige gebracht
	 * wird.
	 */
	public void onModuleLoad() {

		/*-------------Styles -------------*/

		headerPanel.setStylePrimaryName("headerPanel");
		headlineLabel.setStylePrimaryName("lbheadlineNoteit");
		headlinePanel.setStylePrimaryName("headlinePanel");
		contentPanel.setStylePrimaryName("contentPanel");
		navPanel.setStylePrimaryName("navPanel");

		/*-----------------Width-------------*/

		navPanel.setWidth("100%");
		vpBasisPanel.setWidth("100%");
		headlinePanel.setWidth("100%");
		headerPanel.setWidth("100%");
		contentPanel.setWidth("100%");
		bGeneral.setWidth("100%");
		bSharing.setWidth("100%");

		/*-------------Widgets-------------*/

		headlinePanel.add(headlineLabel);
		headerPanel.add(headlinePanel);
		vpBasisPanel.add(headerPanel);
		vpBasisPanel.add(navPanel);
		vpBasisPanel.add(contentPanel);
		navPanel.add(bGeneral);
		navPanel.add(bSharing);

		RootPanel.get("Reporthead").add(vpBasisPanel);

		/**
		 * Button-SharingINformation, der die benötigte View zur Verfügung
		 * stellt
		 */
		bSharing.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				contentPanel.clear();
				NotesSharingInformationReport notesSharingInformation = new NotesSharingInformationReport();
				contentPanel.add(notesSharingInformation);

			}

		});

		/**
		 * Button-GeneralInformation, der die benötigte View zur Verfügung
		 * stellt
		 */
		bGeneral.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				contentPanel.clear();
				NotesGeneralInformationReport notesGeneralInformation = new NotesGeneralInformationReport();
				contentPanel.add(notesGeneralInformation);
			}

		});

	}
}
