package de.hdm.itprojekt.noteit.client;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.itprojekt.noteit.shared.bo.*;

public class NotebookCellList extends Widget {
	
	
	private Notebook SelectedNotebook;
		
		
		CellList<Notebook> NotebookCellList = null;
				
		public CellList<Notebook> createNotebookCellList(){
			
			
			// Create a KeyProvider.
			ProvidesKey<Notebook> NotebookKeyProvider = new ProvidesKey<Notebook>() {
				public Object getKey(Notebook item) {
					return (item == null) ? null : item.getTitle();
				}
			};
			
			// Create a cell to render each value.
		   NotebookCell NotebookCell = new NotebookCell();	    

		    // Use the cell in a CellList.
		   NotebookCellList = new CellList<Notebook>(NotebookCell, NotebookKeyProvider);
		    
		    // Set the width of the CellList.
		    //userCellList.setWidth("230px");
		    	    	    
		    //Stylen der CellList
		    NotebookCellList.setStylePrimaryName("CellList");
		    
		    
		    // Set a key provider that provides a unique key for each contact. If key is
		    // used to identify contacts when fields (such as the name and address)
		    // change.
		    NotebookCellList.setPageSize(30);
		    NotebookCellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		    NotebookCellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		    // Add a selection model so we can select cells.
		    final SingleSelectionModel<Notebook> NotebookSelectionModel = new SingleSelectionModel<Notebook>(NotebookKeyProvider);
		    NotebookCellList.setSelectionModel(NotebookSelectionModel);
		    NotebookSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		      public void onSelectionChange(SelectionChangeEvent event) {
//		        contactForm.setContact(selectionModel.getSelectedObject());
		    	Window.alert("Du hast gewählt: " + NotebookSelectionModel.getSelectedObject().getTitle());
		    	SelectedNotebook = NotebookSelectionModel.getSelectedObject();
		      }
		    });
			
			
			return NotebookCellList;
			
		}
		
		public Notebook getSelectedNotebook() {
			
			
			
			return SelectedNotebook;
			
		
		}
		
	}


