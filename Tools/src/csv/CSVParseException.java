package csv;

import java.io.IOException;

public class CSVParseException extends IOException {
	public static final CSVObject FAILURE_TO_PARSE = new CSVObject() {
		
		@Override
		public <BUF> BUF toCSV(BUF buf) {
			throw CSVParseException
		}
		
		@Override
		public CSVObjectParser<CSVObject> getReader() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public CSVParseException(CSVObjectParser parser, String str) {
		super("Failed to parse \"" + str + "\" with parsers: " + parser.getParsingSchemes().toString());
		
	}
}
