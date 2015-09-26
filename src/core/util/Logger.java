package core.util;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.Date;

public class Logger {
	private static final String format = "[<timestamp>] [<type>] <id> <message>\n";
	private static final String formatMultiline = "[<timestamp>] [<type>] <id> >>\n<message>\n<<END>>\n";

	private static final boolean LIVE_PRINT = true, FILE_PRINT = true;

	public static final int ERROR = 1, FINE = 0, INFO = 2;

	private static FileWriter fileWriter;

	public static void close() {
		log("Log closing");
		try {
			fileWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getID() {
		return ManagementFactory.getRuntimeMXBean().getName();
	}

	private static String getTimestamp() {
		return new Timestamp(new Date().getTime()).toString();
	}

	private static String getTypeString(int type) {
		if (type == 0)
			return "fine";
		if (type == 1)
			return "error";
		if (type == 2)
			return "info";
		return "type value error";
	}

	public static void log(String message) {
		assert message != null;
		message = message.trim();
		assert message.length() != 0;
		if (!message.contains("\n")) {
			write(message, INFO);
		} else {
			writeMultiline(message, INFO);
		}
	}

	public static void log(String message, int type) {
		assert type >= 0 && type <= 2;
		message = message.trim();
		assert message != null;
		message.trim();
		assert message.length() != 0;
		if (message.contains("\n")) {
			write(message, type);
		} else {
			writeMultiline(message, type);
		}
	}

	private static void output(String out) {
		if (LIVE_PRINT) {
			System.out.print(out);
		}
		if (FILE_PRINT) {
			writeToFile(out);
		}
	}

	private static String tabLines(String message) {
		String out = '\t' + message;
		out = out.replace("\n", "\n\t");
		return out;
	}

	private static void write(String message, int type) {
		String out = format;
		out = out.replaceAll("<timestamp>", getTimestamp());
		out = out.replaceAll("<type>", getTypeString(type));
		out = out.replaceAll("<id>", getID());
		out = out.replaceAll("<message>", message);
		output(out);
	}

	private static void writeMultiline(String message, int type) {
		String out = formatMultiline;
		out = out.replaceAll("<timestamp>", getTimestamp());
		out = out.replaceAll("<type>", getTypeString(type));
		out = out.replaceAll("<id>", getID());
		message = tabLines(message);
		out = out.replaceAll("<message>", message);
		output(out);
	}

	private static void writeToFile(String out) {
		if (fileWriter == null) {
			try {
				fileWriter = new FileWriter("out.log");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fileWriter.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
