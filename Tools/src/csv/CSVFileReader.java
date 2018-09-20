package csv;

public interface CSVFileReader {
	static enum VALUE_SEPERATION {
		CSV,
		LSV
	}
	
	int getLocationOfCollection();
	
	int getStartIndex();
	
	int getEndIndex();
	
	CSVFileReader parallel();
	
	VALUE_SEPERATION getValueSeperationEnum();
}
