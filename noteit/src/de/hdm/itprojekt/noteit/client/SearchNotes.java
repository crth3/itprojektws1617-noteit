package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchNotes extends VerticalPanel {

	public void onLoad() {
		
		HorizontalPanel searchPanel = new HorizontalPanel();
		Label searchNotiz = new Label("Notiz suchen");
		Label suchen = new Label("Suche nach Titel:");
		Label inhalt = new Label("Suche nach Inhalt:");
		Button abbrechen = new Button("abbrechen");
		searchPanel.add(searchNotiz);
		searchPanel.add(suchen);
		searchPanel.add(inhalt);
		searchPanel.add(abbrechen);
		
	}
	
}
