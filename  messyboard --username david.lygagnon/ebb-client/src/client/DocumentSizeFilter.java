package client;

import javax.swing.text.*;
import java.awt.Toolkit;
 
/**
 * This is to restrict the Allowed size in Text Field.
 * It can be used for any Component that carries some Document.
 */
public class DocumentSizeFilter extends DocumentFilter {
    int maxCharacters;
 
    public DocumentSizeFilter(int maxChars) {
        maxCharacters = maxChars;
    }
 
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
       
        if ((fb.getDocument().getLength() + str.length()) <= maxCharacters)
            super.insertString(fb, offs, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }
    
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
        
        if ((fb.getDocument().getLength() + str.length()- length) <= maxCharacters)
            super.replace(fb, offs, length, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
    }
 
}
