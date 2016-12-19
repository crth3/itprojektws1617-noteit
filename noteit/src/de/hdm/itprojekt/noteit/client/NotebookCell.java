package de.hdm.itprojekt.noteit.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
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
		
		if(value.getUserId() != Homepage.getCurrentUser().getId() && value.getId() != 0){
				
			sb.appendHtmlConstant("<img src='Images/person_share.png'/ width=\"10\" height=\"10\">");
		}else if(value.getTitle() == "" && value.getId() == 0){
			sb.appendHtmlConstant("<img src='Images/button_add.png'/ width=\"30\" height=\"30\">");
		}
		
		sb.appendEscaped(" "+ value.getTitle());
		
		sb.appendHtmlConstant("</div>");
		
//		sb.appendHtmlConstant("<div style=\"border-bottom: 4px solid #dddddd;\">");
		

		
		// sb.appendHtmlConstant("<email style='font-size:80%; padding-left:
		// 10px;'>");
		// sb.appendEscaped(value.getEmail());
		// sb.appendHtmlConstant("</email>");
		sb.appendHtmlConstant("</div>");

	}

}
