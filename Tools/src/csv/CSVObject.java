package csv;

import java.io.File;

/**

 * 
 * @author Michael

 */
public interface CSVObject  {
	
	default String toCSV() {
		return toCSV(new StringBuilder()).toString();
	}
	
	/**
	 * Read into some buffer object ("<i>buf</i>") and returns the object for convince.
	 * . 
	 * @param buf
	 * @return
	 */
	<BUF> BUF toCSV(BUF buf);
	
	CSVObjectParser<CSVObject> getReader();
	
}
