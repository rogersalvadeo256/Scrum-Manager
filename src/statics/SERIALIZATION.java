package statics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Functions for serialization of the objects
 * 
 * @author jefter66
 */
public class SERIALIZATION {

	private static String path = System.getProperty("java.io.tmpdir");

	public SERIALIZATION() throws IOException {
	}

	/*
	 * used to set the kinds of files (or better, class)
	 */
	public static enum FileType {
		SESSION
	}

	/*
	 * for each enum FileType addition the name of the file had to be put in other
	 * case here, this way, the serialization going to work for the class
	 */
	private static String getFileName(FileType type) {
		switch (type) {
		case SESSION:
			return "session.ser";
		default:
			break;
		}
		return null;
	}

	/**
	 * Used on the initialization of the program for check if the file are already
	 * in the tmp folder
	 * 
	 * @param FileType
	 * @return
	 * @author jefter66
	 */
	public static boolean fileExists(FileType type) {
		return new File(path + "/" + getFileName(type)).exists() ? true : false;
	}

	/**
	 * Create a file on the tmp folder
	 * @author jefter66
	 * @author André Furlan
	 */
	public static void serialization(Object object, FileType type) throws IOException {

		FileOutputStream fileOut = new FileOutputStream(path + "/" + getFileName(type));

		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		try {
			out.writeObject(object);
//			System.out.println("work");
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();
		fileOut.close();
	}

	/**
	 * @author jefter66
	 * @author André Furlan
	 */
	public static Object undoSerialization(FileType type) {
		Object o = null;
		try {
			FileInputStream fileIn = new FileInputStream(path + "/" + getFileName(type));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = (Object) in.readObject();
			in.close();
			fileIn.close();
//			System.out.println(" its work men");
			return o;
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("class not found");
			c.printStackTrace();
			return null;
		}
	}

	public static void deleteFileSerialization(FileType type) {
		File f = new File(path + "/" + getFileName(type));
		f.delete();
	}
}













