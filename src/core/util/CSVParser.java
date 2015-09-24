package core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVParser {
	public static String[][] parseCSVFile(String filePath) {
		String text = readFile(filePath);
		return parseCSV(text);
	}

	private static String[][] parseCSV(String text) {
		return null;
	}

	private static String readFile(String filePath) {
		File file = new File(filePath);
		assert(file.exists());
		assert(file.isFile());
		assert(file.canRead());
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert(s != null);
		String out = "";
		while(s.hasNextLine())
			out += s.nextLine();
		return out;
	}
}
