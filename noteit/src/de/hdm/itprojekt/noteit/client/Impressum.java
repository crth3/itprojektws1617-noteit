package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

public class Impressum extends VerticalPanel {

	public void run() {
		/**
		 * Überschrift der Seite
		 */
		Label impressumLabel = new Label("Impressum");
		impressumLabel.setStyleName("impressumLabel");
		// setHeadline("Impressum");

		/**
		 * Vertikales Panel für die Inhalte des Impressums
		 */
		VerticalPanel impressumPanel = new VerticalPanel();
		this.add(impressumPanel);

		/**
		 * Nachfolgend werden die Labels für die Anschrift der Hochschule
		 * definiert. Durch die Verwendung der Labels ist er sehr einfach
		 * möglich die Zeilen untereinander stehen zu haben.
		 */
		Label lblheadline = new Label("Impressum");
		impressumPanel.add(lblheadline);
		lblheadline.setStyleName("impressumHeadline");
		Label gesetzLabel = new Label("Angaben gemäß § 5 Telemediengesetz (TMG)");
		gesetzLabel.setStyleName("gesetzLabel");
		impressumPanel.add(gesetzLabel);

		Label hdmLabel = new Label("Hochschule der Medien");
		impressumPanel.add(hdmLabel);

		Label strasseLabel = new Label("Nobelstr. 10");
		impressumPanel.add(strasseLabel);

		Label plzLabel = new Label("70569 Stuttgart");
		impressumPanel.add(plzLabel);

		/**
		 * Hier wird eine Tabelle als Grid definiert. Diese Art der Tabelle
		 * lässt sich nicht flexibel erweitern. Dies ist in diesem Fall jedoch
		 * auch nicht notwendig. Sie benötigt 7 Zeilen und 3 Spalten.
		 */
		Grid impressumGrid = new Grid(7, 3);
		impressumGrid.setStyleName("impressumGrid");

		/**
		 * Hier werden die Inhalte in Labels für den Tabellenkopf definiert.
		 * Außerdem werden zwei Labels für einen Namen und eine E-Mail definiert
		 * um diese in der CSS Datei ansprechen zu können.
		 */
		Widget name = new Label("NAME");
		name.setStyleName("labelName");
		impressumPanel.add(name);

		Widget kontakt = new Label("KONTAKT");
		kontakt.setStyleName("labelKontakt");
		impressumPanel.add(kontakt);

		Widget abteilung = new Label("ABTEILUNG");
		abteilung.setStyleName("labelAbteilung");
		impressumPanel.add(abteilung);

		Label chris = new Label("Christian Reichardt");
		chris.setStyleName("labelChris");
		impressumPanel.add(chris);

		Label eMailDani = new Label("dm077@hdm-stuttgart.de");
		eMailDani.setStyleName("labelDani");
		impressumPanel.add(eMailDani);

		/**
		 * Hier werden der Tabelle die Inhalte per Labels direkt hinzugefügt.
		 */
		impressumGrid.setWidget(0, 0, name);
		impressumGrid.setWidget(0, 1, kontakt);
		impressumGrid.setWidget(0, 2, abteilung);
		impressumGrid.setWidget(1, 0, new Label("Kim Ishola"));
		impressumGrid.setWidget(1, 1, new Label("ki004@hdm-stuttgart.de"));
		impressumGrid.setWidget(1, 2, new Label("GUI"));
		impressumGrid.setWidget(2, 0, new Label("Daniel Meier"));
		impressumGrid.setWidget(2, 1, eMailDani);
		impressumGrid.setWidget(2, 2, new Label("GUI"));
		impressumGrid.setWidget(3, 0, chris);
		impressumGrid.setWidget(3, 1, new Label("cr076@hdm-stuttgart.de"));
		impressumGrid.setWidget(3, 2, new Label("GUI"));
		impressumGrid.setWidget(4, 0, new Label("Tobias Dahms"));
		impressumGrid.setWidget(4, 1, new Label("td028@hdm-stuttgart.de"));
		impressumGrid.setWidget(4, 2, new Label("Applikation"));
		impressumGrid.setWidget(5, 0, new Label("Maik Zimmermann"));
		impressumGrid.setWidget(5, 1, new Label("mz059@hdm-stuttgart.de"));
		impressumGrid.setWidget(5, 2, new Label("Applikation"));
		impressumGrid.setWidget(6, 0, new Label("Christian Roth"));
		impressumGrid.setWidget(6, 1, new Label("cr078@hdm-stuttgart.de"));
		impressumGrid.setWidget(6, 2, new Label("Datenbank + Applikation"));

		/**
		 * Hier wird dem Impressums Panel, welches am Anfang der Seite erstellt
		 * wurde, die Grid Tabelle hinzugefügt.
		 */
		impressumPanel.add(impressumGrid);

	}

}
