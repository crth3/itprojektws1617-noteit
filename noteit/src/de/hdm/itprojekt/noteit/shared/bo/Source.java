package de.hdm.itprojekt.noteit.shared.bo;

/**
 * 
 * @author maikzimmermann
 *
 */
public class Source extends BusinessObjects {

	private static final long serialVersionUID = 1L;
	private int sourceID = 0;

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

}
