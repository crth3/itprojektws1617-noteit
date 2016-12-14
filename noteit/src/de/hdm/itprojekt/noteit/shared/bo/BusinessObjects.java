package de.hdm.itprojekt.noteit.shared.bo;

import java.io.Serializable;
import java.sql.Date;


public class BusinessObjects implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id = 0;

	protected Date creationDate = new Date(System.currentTimeMillis());

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id 
	 * the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		/*
		 * Abfragen, ob ein Objekt ungleich NULL ist und ob ein Objekt gecastet
		 * werden kann, sind immer wichtig!
		 */
		if (o != null && o instanceof BusinessObjects) {
			BusinessObjects bo = (BusinessObjects) o;
			try {
				if (bo.getId() == this.id)
					return true;
			} catch (IllegalArgumentException e) {

				return false;
			}
		}

		return false;
	}

	/**
	 * Aktuelles datum bekommen
	 * 
	 * @return
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Aktuelles Datum setzen
	 * 
	 * @param creationDate
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
