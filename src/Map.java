import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Map {
	
	int[][] map;
	
	public Map(String fileName) {
		map = readFile(fileName);
	}
	
	private int[][] readFile(String fileName) {
		File file = new File(fileName);
		assert(file.exists());
		assert(file.canRead());
		 BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			System.err.println("File issue");
			e.printStackTrace();
		}
		assert(bufferedReader != null);
		String line, text = "";
		try {
			while((line = bufferedReader.readLine()) != null)
				text += line + "\n";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseCSV(text);
	}

	private int[][] parseCSV(String text) {
		text = text.trim();
		String[] lines = text.split("\n");
		assert(lines.length > 0);
		assert(lines[0].length() > 0);
		assert(lines[0].split(",").length > 0);
		int height = lines[0].split(",").length;
		int width = lines.length;
		int[][] data = new int[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				data[y][x] = Integer.parseInt(lines[y].split(",")[x]);
		return data;
	}

	public void update(double time) {
		
	}
	
	public void render() {
		
	}
	
	public String toString() {
		return Arrays.deepToString(map);
	}
}
