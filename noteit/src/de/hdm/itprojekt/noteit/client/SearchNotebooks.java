package de.hdm.itprojekt.noteit.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchNotebooks extends VerticalPanel {

	public void onLoad() {

		/**
		 * create the Panel with the label
		 */
		HorizontalPanel headlinePanel = new HorizontalPanel();
		Label headlineNotizbuch = new Label("Notizbuch suchen");
		headlinePanel.add(headlineNotizbuch);

		/**
		 * create the Panel with the label
		 */
		HorizontalPanel searchPanel = new HorizontalPanel();
		Label suchen = new Label("Suche nach Titel:");
		searchPanel.add(suchen);

		/**
		 * create the Oracle, which the words for the SuggestBox
		 */
		MultiWordSuggestOracle searchNotebookOracle = new MultiWordSuggestOracle();
		searchNotebookOracle.add("Test");
		searchNotebookOracle.add("Meier");
		searchNotebookOracle.add("Meler");

		/**
		 * create the SuggestBox, and included to the Panel
		 */
		final SuggestBox suggestBoxsearch = new SuggestBox(
				searchNotebookOracle);
		searchPanel.add(suggestBoxsearch);

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

		this.add(headlinePanel);
		this.add(searchPanel);
		this.add(abbrechPanel);

	}

}
