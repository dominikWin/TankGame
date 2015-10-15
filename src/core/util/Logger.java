package core.util;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.Date;

/**
 * A class for logging data.
 * @author Dominik Winecki

 *
 */
public class Logger {
	public static final int ERROR = 1, FINE = 0, INFO = 2;
	private static FileWriter fileWriter;

	private static final String format = "[<timestamp>] [<type>] <id> <message>\n";

	private static final String formatMultiline = "[<timestamp>] [<type>] <id> >>\n<message>\n<<END>>\n";

	private static final boolean LIVE_PRINT = true, FILE_PRINT = true;

	/**
	 * Closes the logger.
	 */
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

	/**
	 * @return a string with the process id number and user.
	 */
	private static String getID() {
		return ManagementFactory.getRuntimeMXBean().getName();
	}

	/**
	 * @return a string containing a timestamp.
	 */
	private static String getTimestamp() {
		return new Timestamp(new Date().getTime()).toString();
	}

	/**
	 * @param type
	 * @return a string value for the log type.
	 */
	private static String getTypeString(int type) {
		if (type == FINE)
			return "fine";
		if (type == ERROR)
			return "error";
		if (type == INFO)
			return "info";
		return "type value error";
	}

	/**
	 * Logs the given message.
	 * @param message
	 */
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

	/**
	 * Logs the given message with a specified log type.
	 * @param message
	 * @param type
	 */
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

	/**
	 * A method for writing the log message out.
	 * @param out
	 */
	private static void output(String out) {
		if (LIVE_PRINT) {
			System.out.print(out);
		}
		if (FILE_PRINT) {
			writeToFile(out);
		}
	}

	/**
	 * @param message
	 * @return a string like message with all lines beginning with a tab.
	 */
	private static String tabLines(String message) {
		String out = '\t' + message;
		out = out.replace("\n", "\n\t");
		return out;
	}

	/**
	 * Writes a message with a given type to the log.
	 * @param message
	 * @param type
	 */
	private static void write(String message, int type) {
		String out = format;
		out = out.replaceAll("<timestamp>", getTimestamp());
		out = out.replaceAll("<type>", getTypeString(type));
		out = out.replaceAll("<id>", getID());
		out = out.replaceAll("<message>", message);
		output(out);
	}

	/**
	 * Writes a message with multiple lines and a given type to the log.
	 * @param message
	 * @param type
	 */
	private static void writeMultiline(String message, int type) {
		String out = formatMultiline;
		out = out.replaceAll("<timestamp>", getTimestamp());
		out = out.replaceAll("<type>", getTypeString(type));
		out = out.replaceAll("<id>", getID());
		message = tabLines(message);
		out = out.replaceAll("<message>", message);
		output(out);
	}

	/**
	 * Writes message out to the log file.
	 * @param out
	 */
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
