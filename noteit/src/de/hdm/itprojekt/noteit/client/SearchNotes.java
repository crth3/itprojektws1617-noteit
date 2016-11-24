package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchNotes extends VerticalPanel {

	public void onLoad() {
		
		HorizontalPanel searchPanel = new HorizontalPanel();
		Label searchNotiz = new Label("Notiz suchen");
		Label suchen = new Label("Suche nach Titel:");
		Label inhalt = new Label("Suche nach Inhalt:");
		searchPanel.add(searchNotiz);
		searchPanel.add(suchen);
		searchPanel.add(inhalt);
		
		/**
		 * create the Oracle, which the words for the SuggestBox
		 */
		MultiWordSuggestOracle searchNoteOracle = new MultiWordSuggestOracle();
		searchNoteOracle.add("Test");
		searchNoteOracle.add("Meier");
		searchNoteOracle.add("Meler");
		
		/**
		 * create the SuggestBox, and included to the Panel
		 */
		final SuggestBox suggestBoxsearch = new SuggestBox(searchNoteOracle);
		searchPanel.add(suggestBoxsearch);
		
		Button abbrechen = new Button("abbrechen");
		searchPanel.add(abbrechen);
		
		this.add(searchPanel);
		
	}
	
}
