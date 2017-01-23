package de.hdm.itprojekt.noteit.shared.report;

import java.io.Serializable;
import java.util.Vector;

/**
 * Diese Klasse stellt eine Menge einzelner Absätze (
 * <code>ParagraphSimple</code>-Objekte) dar. Diese werden als Unterabschnitte
 * in einem <code>Vector</code> abgelegt verwaltet.
 * 
 * @author Thies
 */
public class ParagraphComposite extends Paragraph implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Speicherort der Unterabschnitte.
   */
  private Vector<ParagraphSimple> subParagraphs = new Vector<ParagraphSimple>();

  /**
   * Einen Unterabschnitt hinzufügen.
   * 
   * @param p der hinzuzufügende Unterabschnitt.
   */
  public void addSubParagraph(ParagraphSimple p) {
    this.subParagraphs.addElement(p);
  }

  /**
   * Einen Unterabschnitt entfernen.
   * 
   * @param p der zu entfernende Unterabschnitt.
   */
  public void removeSubParagraph(ParagraphSimple p) {
    this.subParagraphs.removeElement(p);
  }

  /**
   * Auslesen sämtlicher Unterabschnitte.
   * 
   * @return <code>Vector</code>, der sämtliche Unterabschnitte enthält.
   */
  public Vector<ParagraphSimple> getSubParagraphs() {
    return this.subParagraphs;
  }

  /**
   * Auslesen der Anzahl der Unterabschnitte.
   * 
   * @return Anzahl der Unterabschnitte
   */
  public int getNumParagraphs() {
    return this.subParagraphs.size();
  }

  /**
   * Auslesen eines einzelnen Unterabschnitts.
   * 
   * @param i der Index des gewünschten Unterabschnitts (0 <= i <n), mit n =
   *          Anzahl der Unterabschnitte.
   * 
   * @return der gewünschte Unterabschnitt.
   */
  public ParagraphSimple getParagraphAt(int i) {
    return this.subParagraphs.elementAt(i);
  }

  /**
   * Umwandeln eines <code>ParagraphComposite</code> in einen
   * <code>String</code>.
   */
  public String toString() {
    /*
     * Wir legen einen leeren Buffer an, in den wir sukzessive sämtliche
     * String-Repräsentationen der Unterabschnitte eintragen.
     */
    StringBuffer result = new StringBuffer();

    // Schleife über alle Unterabschnitte
    for (int i = 0; i < this.subParagraphs.size(); i++) {
      ParagraphSimple p = this.subParagraphs.elementAt(i);

      /*
       * den jew. Unterabschnitt in einen String wandeln und an den Buffer hängen.
       */
      result.append(p.toString() + "\n");
    }

    /*
     * Schließlich wird der Buffer in einen String umgewandelt und
     * zurückgegeben.
     */
    return result.toString();
  }
}
