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
		sb.appendHtmlConstant("<div style=\"padding: 5px ;margin: auto ;\">");
		if(value.getUserId() != Homepage.getCurrentUser().getId()&&value.getId() != 0){

			
			sb.appendHtmlConstant("<img src='Images/person_share.png'/ width=\"10\" height=\"10\">");
			
		}else if(value.getTitle() == "" && value.getId() == 0){
			sb.appendHtmlConstant("<img src='Images/button_add.png'/ width=\"25\" height=\"25\">");
		}
		sb.appendEscaped(" "+value.getTitle());
		sb.appendHtmlConstant("</div>");
		//sb.appendHtmlConstant("<div style=\"border-bottom: 4px solid #dddddd;\">");
		// sb.appendHtmlConstant("<email style='font-size:80%; padding-left:
		// 10px;'>");
		// sb.appendEscaped(value.getEmail());
		// sb.appendHtmlConstant("</email>");
		sb.appendHtmlConstant("</div>");

	}

}
