package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;

public class UrlView extends DialogBox { 
	private final static NotesAdministrationAsync notesAdmin = GWT.create(NotesAdministration.class);
	
    Label label = new Label("Verfügbare Notebooks:");
	TextBox url = new TextBox();
	ListBox lbNotebook = new ListBox();
	
	public UrlView() {
    // Set the dialog box's caption.
    setText("Bitte wählen Sie das Ziel-Notizbuch");

    // Enable animation.
    setAnimationEnabled(true);

    // Enable glass background.
    setGlassEnabled(true);

    // DialogBox is a SimplePanel, so you have to set its widget 
    // property to whatever you want its contents to be.
    Button ok = new Button("Speichern");
    ok.addClickHandler(new ClickHandler() {
       public void onClick(ClickEvent event) {
    	   UrlView.this.hide();
       }
    });


	notesAdmin.getAllNotebooksByUserID(Homepage.getCurrentUser().getId(), new AsyncCallback<ArrayList<Notebook>>() {
		
		@Override
		public void onSuccess(ArrayList<Notebook> result) {
			for (Notebook notebook : result) {
				
				lbNotebook.addItem(notebook.getTitle());
				}
			
			lbNotebook.removeItem(0);
				}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}});

	
	
	url.setText(Noteit.getValue_URL());
	
    VerticalPanel panel = new VerticalPanel();
    panel.setHeight("100");
    panel.setWidth("300");
    panel.setSpacing(10);
    panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    panel.add(label);
    panel.add(url);
    panel.add(lbNotebook);
    panel.add(ok);

    setWidget(panel);





 


}    

	
	

}
