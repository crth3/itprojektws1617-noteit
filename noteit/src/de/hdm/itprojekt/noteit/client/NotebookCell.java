package de.hdm.itprojekt.noteit.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.itprojekt.noteit.shared.bo.*;
 
/** 
 * Diese Klasse holt sich das User-Objekt und stellt den selektieren Inhalt eines Objekts
 * in HTML dar.
 * 
 * @author Dahms
 */

public class NotebookCell extends AbstractCell<Notebook>{

	@Override
	public void render(Context context,
			Notebook value, SafeHtmlBuilder sb) {
		
		if (value == null) {
	      return;
	    }
	

		  sb.appendHtmlConstant("<div>");
	      sb.appendEscaped(value.getTitle() + " ");
		  sb.appendHtmlConstant("</div>");
		  sb.appendHtmlConstant("<div style=\"border-bottom: 4px solid #dddddd;\">");
		//  sb.appendHtmlConstant("<email style='font-size:80%; padding-left: 10px;'>");
		//  sb.appendEscaped(value.getEmail());
		//  sb.appendHtmlConstant("</email>");
	      sb.appendHtmlConstant("</div>");


	    }		


	
}



