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
	
	private DateBox dbFromMaturity = new DateBox();
	private DateBox dbFromCreationDate = new DateBox();
	private DateBox dbFromModificationDate = new DateBox();
	private DateBox dbToMaturity = new DateBox();
	private DateBox dbToCreationDate = new DateBox();
	private DateBox dbToModificationDate = new DateBox();
	
	
	Label lblSearchNoteTitle = new Label("Notiz-Titel");
	Label lblSearchNotebookTitle = new Label("Notizbuch-Titel");
	Label lblUserSearch = new Label("Nutzersuche");
	Label lblMaturity = new Label("Fälligkeitsdatum");
	Label lblCreationDate = new Label("Erstellungsdatum");
	Label lblModificationDate = new Label("Änderungsdatum");
	Label lblFrom= new Label("von ");
	Label lblTo = new Label("bis ");
	
	//TODO Label ersetzen
	Label lblButton = new Label("Suchen...");
	

	
	final TextBox tbSearchNote = new TextBox();
	final TextBox tbSearchNotebook = new TextBox();
	private Button btnGenerate = new Button("Erstellen");
	private Button btnReset = new Button("Leeren");
	final User user = new User();
	int userId;
	String sKeywordNote; 
	String sKeywordNotebook; 
	Date fromMaturity;
	Date fromCreationDate;
	Date fromModificationDate;
	Date toMaturity;
	Date toCreationDate;
	Date toModificationDate;

		
	public NotesGeneralInformationReport() {
		
		oracle = new MultiWordSuggestOracle();
		reportService =ClientsideSettings.getReportService();

		
		notesAdministration.findAllUser(new AsyncCallback<ArrayList<User>>() {
			
			@Override
			public void onSuccess(ArrayList<User> result) {
				System.out.println("result" + result);
				
				for (User user : result) {
					
					String username ="";
					username = 
//					user.getId() +" " + 
					user.getFirstName() + " " + user.getLastName()
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
	            	        		  
	            // Split, damit Emailadresse übrig bleibt
	            String sfinalProperty = selectedProperty.split(" ")[2];
	            
	            String mail = sfinalProperty;
	            
	            notesAdministration.findUserByMail(mail, new AsyncCallback<User>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(User result) {
						// TODO Auto-generated method stub
						user.setId(result.getId());
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
		
		dbFromMaturity.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				fromMaturity =  event.getValue();
				
				System.out.println("fromMaturity: "+fromMaturity);
				
			}
		});
		
		dbToMaturity.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				toMaturity =  event.getValue();
				
				System.out.println("toMaturity: "+toMaturity);
				
			}
		});
		
		dbFromCreationDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				fromCreationDate =  event.getValue();
				
				System.out.println("fromCreationDate: "+fromCreationDate);
				
			}
		});
		
		dbToCreationDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				toCreationDate =  event.getValue();
				
				System.out.println("toCreationDate: "+toCreationDate);
				
			}
		});
		
		dbFromModificationDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				fromModificationDate =  event.getValue();
				
				System.out.println("fromModificationDate: "+fromModificationDate);
				
			}
		});
		
		dbToModificationDate.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				toModificationDate =  event.getValue();
				
				System.out.println("toModificationDate: "+toModificationDate);
				
			}
		});
		
		//-------------------- set DateBox Format ---------------------------
		dbFromMaturity.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbFromCreationDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbFromModificationDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbToMaturity.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbToCreationDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		dbToModificationDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getShortDateFormat()));
		
		
		//-------------------- set Placeholder ---------------------------
		dbFromMaturity.getElement().setPropertyString("placeholder", "von");
		dbFromCreationDate.getElement().setPropertyString("placeholder", "von");
		dbFromModificationDate.getElement().setPropertyString("placeholder", "von");
		dbToMaturity.getElement().setPropertyString("placeholder", "bis");
		dbToCreationDate.getElement().setPropertyString("placeholder", "bis");
		dbToModificationDate.getElement().setPropertyString("placeholder", "bis");
		
		
		
		//-------------------- set Panels ---------------------------

		hp.setStyleName("PanelBorder");

		vpSearchNote.add(lblSearchNoteTitle);
		vpSearchNote.add(tbSearchNote);
		
		vpSearchNotebook.add(lblSearchNotebookTitle);
		vpSearchNotebook.add(tbSearchNotebook);
		
		vpSearchUser.add(lblUserSearch);
		vpSearchUser.add(sb);
		
		vpSearchMaturity.add(lblMaturity);
		vpSearchMaturity.add(dbFromMaturity);
		vpSearchMaturity.add(dbToMaturity);
		
		vpSearchCreationDate.add(lblCreationDate);
		vpSearchCreationDate.add(dbFromCreationDate);
		vpSearchCreationDate.add(dbToCreationDate);
		
		vpSearchModificationdate.add(lblModificationDate);
		vpSearchModificationdate.add(dbFromModificationDate);
		vpSearchModificationdate.add(dbToModificationDate);
		
		lblButton.setVisible(false);
		vpGenerate.add(lblButton);
		vpGenerate.add(btnGenerate);
		vpGenerate.add(btnReset);
		

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

	btnReset.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				vpReport.clear();
				hpSearch.clear();
				NotesGeneralInformationReport notesGeneralInformationReport = new NotesGeneralInformationReport();
				hpSearch.add(notesGeneralInformationReport);
				
				// TODO Auto-generated method stub
				
			}
			
		});
	btnGenerate.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
						
			reportService.createReportNotesGeneralInformation(user, sKeywordNote, sKeywordNotebook, fromMaturity, toMaturity, fromCreationDate, toCreationDate, fromModificationDate, toModificationDate, new AsyncCallback<NotesGeneralInformation>() {

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
					 	
				}

				
				
			});
			
			}
		
	});
	
	
		
	}
	

}
