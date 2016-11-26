package de.hdm.itprojekt.noteit.client;

import java.util.ArrayList;

import org.apache.bcel.generic.NEWARRAY;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.hdm.itprojekt.noteit.shared.NotesAdministration;
import de.hdm.itprojekt.noteit.shared.NotesAdministrationAsync;
import de.hdm.itprojekt.noteit.shared.bo.Notebook;

public class Notebooks extends HorizontalPanel{
	
	private final NotesAdministrationAsync notesAdmin = GWT
			.create(NotesAdministration.class);
	
	final CellList<Notebook> NotebookCellList = new NotebookCellList().createNotebookCellList();

	public CellList<Notebook> getAllNotebooks(int userID) {
		notesAdmin.getAllNotebooksByUserID(userID, new AsyncCallback<ArrayList<Notebook>>() {
			
			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				System.out.println("result" + result);
				
				NotebookCellList.setRowData(result);
			
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("Error" + caught);
			}
		});
		
		return NotebookCellList;
		
	}

	public CellList<Notebook> getAllNotebooksByKeyword(int userID, String keyword){
		notesAdmin.findNotebooksByKeyword(userID, keyword, new AsyncCallback<ArrayList<Notebook>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("Error" + caught);
			}

			@Override
			public void onSuccess(ArrayList<Notebook> result) {
				// TODO Auto-generated method stub
				System.out.println("result" + result);
			
				NotebookCellList.setRowData(result);
				//NotebookCellList.setValueUpdater(valueUpdater);(result);

			}
		});
		
		return NotebookCellList;
	}
	
}
