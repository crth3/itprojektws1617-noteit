package de.hdm.itprojekt.noteit.shared.report;
import java.io.Serializable;

/**
 * Diese Klasse stellt einzelne Absätze dar. Der Absatzinhalt wird als String
 * gespeichert. Der Anwender sollte in diesem Strinig keinerlei
 * Formatierungssymbole einfügen, da diese in der Regel zielformatspezifisch
 * sind.
 * 
 * @author Thies
 */
public class ParagraphSimple extends Paragraph implements Serializable {

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;

  /**
   * Inhalt des Absatzes.
   */
  private String text = "";

  /**
   * <p>
   * Serialisierbare Klassen, die mittels GWT-RPC transportiert werden sollen,
   * müssen einen No-Argument-Konstruktor besitzen. Ist kein Konstruktor
   * explizit angegeben, so existiert ini Java-Klassen implizit der
   * Default-Konstruktor, der dem No-Argument-Konstruktor entspricht.
   * </p>
   * <p>
   * Besitzt eine Klasse mind. einen explizit implementierten Konstruktor, so
   * gelten nur diese explizit implementierten Konstruktoren. Der
   * Default-Konstruktor gilt dann nicht. Wenn wir in einer solchen Situation
   * aber dennoch einen No-Argument-Konstruktor benötigen, müssen wir diesen wie
   * in diesem Beispiel explizit implementieren.
   * </p>
   * 
   * @see #ParagraphSimple(String)
   */
  public ParagraphSimple() {
  }

  /**
   * Dieser Konstruktor ermöglicht es, bereits bei Instantiierung von
   * <code>ParagraphSimple</code>-Objekten deren Inhalt angeben zu können.
   * 
   * @param value der Inhalt des Absatzes
   * @see #ParagraphSimple()
   */
  public ParagraphSimple(String value) {
    this.text = value;
  }

  /**
   * Auslesen des Inhalts.
   * 
   * @return Inhalt als String
   */
  public String getText() {
    return this.text;
  }

  /**
   * überschreiben des Inhalts.
   * 
   * @param text der neue Inhalt des Absatzes.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Umwandeln des <code>ParagraphSimple</code>-Objekts in einen String.
   */
  public String toString() {
    return this.text;
  }
}
