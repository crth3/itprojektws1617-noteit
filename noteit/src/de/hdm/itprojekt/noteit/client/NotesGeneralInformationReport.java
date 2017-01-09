package de.hdm.itprojekt.noteit.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.ReportService;
import de.hdm.itprojekt.noteit.shared.ReportServiceAsync;
import de.hdm.itprojekt.noteit.shared.bo.User;
import de.hdm.itprojekt.noteit.shared.report.HTMLReportWriter;
import de.hdm.itprojekt.noteit.shared.report.NotesGeneralInformation;
import de.hdm.itprojekt.noteit.shared.report.NotesSharingInformation;
import de.hdm.itprojekt.noteit.shared.report.ReportSimple;

public class NotesGeneralInformationReport extends VerticalPanel{
	
	private final static NotesAdministrationAsync notesAdministration = GWT.create(NotesAdministration.class);
	private ReportServiceAsync reportService = null;
	static HorizontalPanel contentPanel = new HorizontalPanel();	
	private HorizontalPanel hp = new HorizontalPanel();
	private VerticalPanel vpSearchNote = new VerticalPanel();
	private VerticalPanel vpSearchNotebook = new VerticalPanel();
	private VerticalPanel vpSearchUser = new VerticalPanel();
	private VerticalPanel vpSearchMaturity = new VerticalPanel();
	private VerticalPanel vpSearchCreationDate = new VerticalPanel();
	private VerticalPanel vpSearchModificationdate = new VerticalPanel();
	private VerticalPanel vpGenerate = new VerticalPanel();
	private HorizontalPanel hpSearch = new HorizontalPanel();
	VerticalPanel vpReport = new VerticalPanel();

	
	private MultiWordSuggestOracle oracle;
	private SuggestBox sb;
	
	private DateBox dbMaturity = new DateBox();
	private DateBox dbCreationDate = new DateBox();
	private DateBox dbModificationDate = new DateBox();
	
	Label lblSearchNoteTitle = new Label("Notiz-Titel");
	Label lblSearchNotebookTitle = new Label("Notizbuch-Titel");
	Label lblUserSearch = new Label("Nutzersuche");
	Label lblMaturity = new Label("Fälligkeitsdatum");
	Label lblCreationDate = new Label("Erstellungsdatum");
	Label lblModificationDate = new Label("Änderungsdatum");
	
	//TODO Label ersetzen
	Label lblButton = new Label("Suchen...");
	

	
	final TextBox tbSearchNote = new TextBox();
	final TextBox tbSearchNotebook = new TextBox();
	private Button btnGenerate = new Button("Generate");
	final User user = new User();
	int userId;
	String sKeywordNote; 
	String sKeywordNotebook; 
	Date maturity;
	Date creationDate;
	Date modificationDate;

		
	public NotesGeneralInformationReport() {
		
		oracle = new MultiWordSuggestOracle();
		reportService = GWT.create(ReportService.class);

		
		notesAdministration.findAllUser(new AsyncCallback<ArrayList<User>>() {
			
			@Override
			public void onSuccess(ArrayList<User> result) {
				System.out.println("result" + result);
				
				for (User user : result) {
					
					String username ="";
					username = user.getId() +" " + user.getFirstName() + " " + user.getLastName()
							+ " " + user.getMail();
					oracle.add(username);
					//Window.alert("Suggestion - userId" + userId);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Failure: "+caught);
			}
		});
		
		


				
		sb = new SuggestBox(oracle);
		
		sb.addSelectionHandler(new SelectionHandler<Suggestion>() {

	        public void onSelection(SelectionEvent<Suggestion> event) {
	        	

	           	//ausgewählten Value recieven        	
	            String selectedProperty =   ((SuggestBox)event.getSource()).getValue(); 
	        		  
	            // Alle Zeichen nach der UserId löschen
	            String sfinalProperty = selectedProperty.split(" ")[0];
	            
	            // String in Integer umwandeln
	            int ifinalProperty = Integer.parseInt(sfinalProperty);
	            
	            // userId zuweisen
	            user.setId(ifinalProperty);
	            userId = ifinalProperty;
	            
	            // UserObjekt befüllen
	            notesAdministration.findUserById(userId, new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(User result) {
						// TODO Auto-generated method stub
						user.setFirstName(result.getFirstName());
						user.setLastName(result.getLastName());
					}
	            	
	            });
	            
	        }
	        
	    });
		
		//-------------------- Value Change Handler's for search box ---------------------------

		tbSearchNote.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				sKeywordNote =  event.getValue();
				
				System.out.println("skeywordnote: "+sKeywordNote);
				
			}
		});
		
		tbSearchNotebook.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				sKeywordNotebook =  event.getValue();
				
				System.out.println("sKeywordNotebook: "+sKeywordNotebook);
				
			}
		});
		
		dbMaturity.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				maturity =  event.getValue();
				
				System.out.println("maturity: "+maturity);
				
			}
		});
		
		dbCreationDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				creationDate =  event.getValue();
				
				System.out.println("creationDate: "+creationDate);
				
			}
		});
		
		dbModificationDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				modificationDate =  event.getValue();
				
				System.out.println("modificationDate: "+modificationDate);
				
			}
		});
		
		//-------------------- set DateBox Format ---------------------------
		dbMaturity.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbCreationDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbModificationDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		
		
		//-------------------- set Panels ---------------------------

		hp.setStyleName("PanelBorder");

		vpSearchNote.add(lblSearchNoteTitle);
		vpSearchNote.add(tbSearchNote);
		
		vpSearchNotebook.add(lblSearchNotebookTitle);
		vpSearchNotebook.add(tbSearchNotebook);
		
		vpSearchUser.add(lblUserSearch);
		vpSearchUser.add(sb);
		
		vpSearchMaturity.add(lblMaturity);
		vpSearchMaturity.add(dbMaturity);
		
		vpSearchCreationDate.add(lblCreationDate);
		vpSearchCreationDate.add(dbCreationDate);
		
		vpSearchModificationdate.add(lblModificationDate);
		vpSearchModificationdate.add(dbModificationDate);
		
		lblButton.setVisible(false);
		vpGenerate.add(lblButton);
		vpGenerate.add(btnGenerate);
		

		add(hpSearch);
		add(vpReport);

				
		hpSearch.add(vpSearchNote);
		hpSearch.add(vpSearchNotebook);
		hpSearch.add(vpSearchUser);
		hpSearch.add(vpSearchUser);
		hpSearch.add(vpSearchMaturity);
		hpSearch.add(vpSearchCreationDate);
		hpSearch.add(vpSearchModificationdate);
		hpSearch.add(vpGenerate);
		
	
	
		
	//RootPanel.get("content").add(contentPanel);
	
	
	btnGenerate.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
						
			reportService.createReportNotesGeneralInformation(user, sKeywordNote, sKeywordNotebook, maturity, creationDate, modificationDate, new AsyncCallback<NotesGeneralInformation>() {

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onSuccess(NotesGeneralInformation notesGeneralInformation) {
					// TODO Auto-generated method stub
					
					vpReport.clear();
					
					// Reportausgabe
					HTMLReportWriter writerreport = new HTMLReportWriter();
					final	ReportSimple report = notesGeneralInformation;
					writerreport.process(report);
					vpReport.add(new HTML(writerreport.getReportText()));
					
					// Suchkriterien zurücksetzen
					 sKeywordNote = null; 
					 sKeywordNotebook = null; 
					 maturity = null;
					 creationDate = null;
					 modificationDate = null;
					
				}

				
				
			});
			
			}
		
	});
	
	
		
	}
	

}
