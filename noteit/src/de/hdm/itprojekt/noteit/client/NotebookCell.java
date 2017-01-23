package de.hdm.itprojekt.noteit.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojekt.noteit.shared.bo.*;

/**
 * Diese Klasse holt sich das User-Objekt und stellt den selektieren Inhalt
 * eines Objekts in HTML dar.
 * 
 * @author Dahms
 */

public class NotebookCell extends AbstractCell<Notebook> {

	@Override
	public void render(Context context, Notebook value, SafeHtmlBuilder sb) {

		if (value == null) {
			return;
		}

		sb.appendHtmlConstant("<div>");
		sb.appendHtmlConstant("<div style=\"margin: 5px ;margin: auto ;\">");

		if (value.getUserId() != Homepage.getCurrentUser().getId() && value.getId() != 0 && value.getId() != -1) {

			sb.appendHtmlConstant("<img src='Images/person_share.png'/ width=\"10\" height=\"10\">");
		} else if (value.getTitle() == "" && value.getId() == 0) {
			sb.appendHtmlConstant("<img class='addImage' src='Images/button_add.png'/ width=\"28\" height=\"28\">"
					+ "<span class='addImageText'/>Notizbuch</span>");
		}

		sb.appendEscaped(" " + value.getTitle());

		sb.appendHtmlConstant("</div>");

		sb.appendHtmlConstant("</div>");

	}

}
