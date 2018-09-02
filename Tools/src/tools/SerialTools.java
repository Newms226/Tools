package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerialTools {
	
	public static final String DEFAULT_SERIAL_DIRECTORY_STRING = "/Users/Michael/Documents/Serial/";
	public static final File DEFAULT_SERIAL_DIRECTORY_FILE = new File(DEFAULT_SERIAL_DIRECTORY_STRING);
	
	private SerialTools() {}
	
	public static void seralizeObject(Serializable toSeralize, String name) {
		seralizeObject(toSeralize, name, DEFAULT_SERIAL_DIRECTORY_FILE);
	}
	
	
	public static void seralizeObject(Serializable toSeralize, String name, File directory) {
		if (!directory.isDirectory()) {
			directory = FileTools.generateDirectoryFile("Invalid directory passed to seralize");
		}
		seralizeObject(toSeralize, new File(directory + "/" + name + ".txt"));
	}
	
	public static boolean seralizeObject(Serializable toSeralize, File fileToWriteTo) {
		try (FileOutputStream file = new FileOutputStream(fileToWriteTo);
			 ObjectOutputStream output = new ObjectOutputStream(file))
		{
			output.writeObject(toSeralize);
			return true;
//			System.out.println("Seralize successful.");
		} catch (Exception e) {
			System.out.println("Serialize Failed.\n" + e.getMessage() + "\n");
//			e.printStackTrace();
			
		} 
		return false;
	}
	
//	public static void seralizeObject(KeyableByInt toSeralize, File directory) {
//		if (!directory.isDirectory()) {
//			directory = FileTools.generateDirectoryFile("Invalid directory passed to seralize");
//		}
//		
//		try (FileOutputStream file = new FileOutputStream(directory + "/" + toSeralize.getKey() + ".txt");
//			 ObjectOutputStream output = new ObjectOutputStream(file))
//		{
//			output.writeObject(toSeralize);
//			System.out.println("Seralize successful.");
//		} catch (Exception e) {
//			System.out.println("Serialize Failed.\n" + e.getMessage() + "\n");
//			e.printStackTrace();
//		}
//	}
	
	public static Serializable deserializeObject(File textSerialization) {
		if (!FileTools.validateFileIsReadableTextFile(textSerialization)) {
			throw new IllegalArgumentException("Invalid text file.");
		}
		try (FileInputStream stream = new FileInputStream(textSerialization);
			 ObjectInputStream input = new ObjectInputStream(stream)){
			return (Serializable) input.readObject();
		} catch (Exception e) {
			System.out.println("Deserialize Failed.\n" + e.getMessage() + "\n");
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Cound not deseralize.");
	}
	
	
}
