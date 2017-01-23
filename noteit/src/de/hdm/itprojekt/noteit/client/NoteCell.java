package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.Date;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;

import de.hdm.itprojekt.noteit.shared.bo.*;

/**
 * generiert den Inhalt einer Notizzelle mit HTML
 * @author Tobias Dahms
 *
 */

public class NoteCell extends AbstractCell<Note> {
	
	Timestamp ts;
	Timestamp ts1 = new Timestamp(System.currentTimeMillis());
	Date date;
	

	@Override
	public void render(Context context, Note value, SafeHtmlBuilder sb) {
		
		

		if (value == null) {
			return;
		}
		ts = value.getMaturityDate();
		
		sb.appendHtmlConstant("<div>");
		sb.appendHtmlConstant("<div style=\"padding: 5px ;margin: auto ;\">");
		if(value.getUserId() != Homepage.getCurrentUser().getId()&&value.getId() != 0){

			
			sb.appendHtmlConstant("<img src='Images/person_share.png'/ width=\"12\" height=\"12\">");
			
		}else if(value.getTitle() == "" && value.getId() == 0){
			sb.appendHtmlConstant("<img class='addImage' src='Images/button_add.png'/ width=\"25\" height=\"25\">" + "<span class='addImageText'/>Notiz</span>");
			
		}
		sb.appendEscaped(" "+value.getTitle());
		
		if(value.getMaturityDate() != null){
			
			sb.appendHtmlConstant("<br>");
			Date date = new Date(ts.getTime());
			DateTimeFormat sdfmt = DateTimeFormat.getFormat("dd.MM.yyyy");
			ts1.setHours(0);
			ts1.setMinutes(0);
			ts1.setSeconds(0);
			ts1.setNanos(0);
			//Wenn Das fälligkeit datum vor dem heutigen war, wird die schrift rot gesetzt
			if(value.getMaturityDate().before(ts1)){
				sb.appendHtmlConstant("<span class=\"dateBefore" +"\">");
				sb.appendEscaped("Abgelaufen: "+ sdfmt.format(date));
			}else if(value.getMaturityDate().after(ts1)){
				sb.appendHtmlConstant("<span class=\"dffasfs" +"\">");
				sb.appendEscaped("Fällig am: "+ sdfmt.format(date));
			}else{
				sb.appendHtmlConstant("<span class=\"dateToday" +"\">");
				sb.appendEscaped("Heute fällig: "+ sdfmt.format(date));
			}
			

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
