package de.hdm.itprojekt.noteit.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojekt.noteit.shared.bo.*;

/**
 * 
 * 
 * 
 * 
 */

public class NoteCell extends AbstractCell<Note> {

	@Override
	public void render(Context context, Note value, SafeHtmlBuilder sb) {

		if (value == null) {
			return;
		}

		sb.appendHtmlConstant("<div>");
		if(value.getUserId() != Homepage.getCurrentUser().getId()){
			
			sb.appendHtmlConstant("<img src='Images/person_share.png'/ width=\"10\" height=\"10\">");
		}
		sb.appendEscaped(" "+value.getTitle() + " " + value.getUserId() +" " + Homepage.getCurrentUser().getId());
		sb.appendHtmlConstant("</div>");
		//sb.appendHtmlConstant("<div style=\"border-bottom: 4px solid #dddddd;\">");
		// sb.appendHtmlConstant("<email style='font-size:80%; padding-left:
		// 10px;'>");
		// sb.appendEscaped(value.getEmail());
		// sb.appendHtmlConstant("</email>");
		sb.appendHtmlConstant("</div>");

	}

}
