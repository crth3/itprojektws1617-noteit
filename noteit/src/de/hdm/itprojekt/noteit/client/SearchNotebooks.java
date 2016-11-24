package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchNotebooks extends VerticalPanel {
	
	public void onLoad() {
		
		HorizontalPanel searchPanel = new HorizontalPanel();
		Label searchNotizbuch = new Label("Notizbuch suchen");
		Label suchen = new Label("Suche nach Titel:");
		searchPanel.add(searchNotizbuch);
		searchPanel.add(suchen);
		
		/**
		 * create the Oracle, which the words for the SuggestBox
		 */
		MultiWordSuggestOracle searchNotebookOracle = new MultiWordSuggestOracle();
		searchNotebookOracle.add("Test");
		searchNotebookOracle.add("Meier");
		searchNotebookOracle.add("Meler");
		
		/**
		 * create the SuggestBox, and included to the  Panel
		 */
		final SuggestBox suggestBoxsearch = new SuggestBox(searchNotebookOracle);
		searchPanel.add(suggestBoxsearch);
		
		Button abbrechen = new Button("abbrechen");
		searchPanel.add(abbrechen);
		
		this.add(searchPanel);
		
		
	}
	
}
