package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchNotebooks extends VerticalPanel {
	
	public void onLoad() {
		
		HorizontalPanel searchPanel = new HorizontalPanel();
		Label searchNotizbuch = new Label("Notizbuch suchen");
		Label suchen = new Label("Suche nach Titel:");
		Button abbrechen = new Button("abbrechen");
		searchPanel.add(searchNotizbuch);
		searchPanel.add(suchen);
		searchPanel.add(abbrechen);
		
		
	}
	
}
