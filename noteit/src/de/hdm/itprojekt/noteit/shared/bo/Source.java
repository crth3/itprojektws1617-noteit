package de.hdm.itprojekt.noteit.shared.bo;

/**
 * 
 * @author maikzimmermann
 *
 */
public class Source extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int sourceID = 0;
	private String source;
	private int noteId;

	public Source() {

	}

	/**
	 * @return the sourceID
	 */
	public int getSourceID() {
		return sourceID;
	}

	/**
	 * @param sourceID
	 *            the sourceID to set
	 */
	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
