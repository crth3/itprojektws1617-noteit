package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojekt.noteit.shared.bo.*;

/**
 * 
 * 
 * 
 * 
 */

public class NoteCell extends AbstractCell<Note> {
	
	Timestamp ts;
	

	@Override
	public void render(Context context, Note value, SafeHtmlBuilder sb) {
		
		

		if (value == null) {
			return;
		}
		ts = value.getMaturityDate();
		
		sb.appendHtmlConstant("<div>");
		sb.appendHtmlConstant("<div style=\"padding: 5px ;margin: auto ;\">");
		if(value.getUserId() != Homepage.getCurrentUser().getId()&&value.getId() != 0){

			
			sb.appendHtmlConstant("<img src='Images/person_share.png'/ width=\"10\" height=\"10\">");
			
		}else if(value.getTitle() == "" && value.getId() == 0){
			sb.appendHtmlConstant("<img src='Images/button_add.png'/ width=\"25\" height=\"25\">");
			
		}
		sb.appendEscaped(" "+value.getTitle());
		
		if(value.getMaturityDate() != null){
			
	        
			sb.appendHtmlConstant("<br>");
			Date date = new Date(ts.getTime());
			DateTimeFormat sdfmt = DateTimeFormat.getFormat("dd.MM.yyyy");
			sb.appendHtmlConstant("<span class=\"dffasfs" +"\">");
			sb.appendEscaped("Fällig am: "+ sdfmt.format(date));
			sb.appendHtmlConstant("</span>");
		}
		sb.appendHtmlConstant("</div>");
		//sb.appendHtmlConstant("<div style=\"border-bottom: 4px solid #dddddd;\">");
		// sb.appendHtmlConstant("<email style='font-size:80%; padding-left:
		// 10px;'>");
		// sb.appendEscaped(value.getEmail());
		// sb.appendHtmlConstant("</email>");
		sb.appendHtmlConstant("</div>");

	}

}
