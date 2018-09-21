package statics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SERIALIZATION {
	
	private static String path = "/tmp/scrum/";
	public SERIALIZATION() throws IOException {}
	
	public static enum FileType { 
		SESSION
	}
	
	private static String getFileName (FileType type) {
		switch (type) {
		case SESSION:
			return "session.ser";
		default:
			break;
		}
		return null;
	}
	
	public static void doSerialization(Object object, FileType type) throws IOException {

		if (!(new File("/tmp/scrum").exists()))	new File("/tmp/scrum").mkdir();

		
		FileOutputStream fileOut = new FileOutputStream(path + getFileName(type));
		
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		try {
			out.writeObject(object);
			System.out.println("work");
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();
		fileOut.close();
	}

	public static Object undoSerialization(FileType type) {
		Object o = null;
		try {
			FileInputStream fileIn = new FileInputStream(path + getFileName(type));
			ObjectInputStream in = new ObjectInputStream(fileIn);
			o = (Object) in.readObject();
			in.close();
			fileIn.close();

			System.out.println(" its work men");

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
		File f = new File(path + getFileName(type));
		f.delete();
	}
}
























