package de.hdm.itprojekt.noteit.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchNotes extends VerticalPanel {

	public void onLoad() {

		/**
		 * create the Panel with the label
		 */
		HorizontalPanel headlinePanel = new HorizontalPanel();
		Label headlineNotiz = new Label("Notiz suchen");
		headlinePanel.add(headlineNotiz);

		/**
		 * create the Panel with the label
		 */
		HorizontalPanel searchPanel = new HorizontalPanel();
		Label suchen = new Label("Suche nach Titel:");
		searchPanel.add(suchen);

		/**
		 * create the Oracle, which the words for the SuggestBox
		 */
		MultiWordSuggestOracle searchNoteOracle = new MultiWordSuggestOracle();
		searchNoteOracle.add("Dahms");
		searchNoteOracle.add("Roht");
		searchNoteOracle.add("Reichardt");
		searchNoteOracle.add("Meier");
		searchNoteOracle.add("Zimmerman");
		searchNoteOracle.add("Ishola");

		/**
		 * create the SuggestBox, and included to the Panel
		 */
		final SuggestBox suggestBoxsearch = new SuggestBox(searchNoteOracle);
		searchPanel.add(suggestBoxsearch);

		/**
		 * create the Panel with the label
		 */
		HorizontalPanel searchInhaltPanel = new HorizontalPanel();
		Label inhalt = new Label("Suche nach Inhalt:");
		searchInhaltPanel.add(inhalt);

		/**
		 * create the Oracle, which the words for the SuggestBox
		 */
		MultiWordSuggestOracle searchInhaltNoteOracle = new MultiWordSuggestOracle();
		searchInhaltNoteOracle.add("Tobi");
		searchInhaltNoteOracle.add("Chris");
		searchInhaltNoteOracle.add("Christian");
		searchInhaltNoteOracle.add("Daniel");
		searchInhaltNoteOracle.add("Maik");
		searchInhaltNoteOracle.add("Kim");

		/**
		 * create the SuggestBox, and included to the Panel
		 */
		final SuggestBox suggestBoxsearchInhalt = new SuggestBox(
				searchInhaltNoteOracle);
		searchInhaltPanel.add(suggestBoxsearchInhalt);

		/**
		 * create the Panel with the label
		 */
		HorizontalPanel abbrechPanel = new HorizontalPanel();
		Button abbrechen = new Button("abbrechen");
		abbrechPanel.add(abbrechen);

		// ClickHandler für Abbrechen Button
		abbrechen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			}
		});

		this.add(headlineNotiz);
		this.add(searchPanel);
		this.add(searchInhaltPanel);
		this.add(abbrechPanel);

	}

}
