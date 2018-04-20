package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	private static final String exceptionsFile = new String("Exceptions.txt");

	public static void saveException(Exception exception) {
		File file;
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
		try {
			file = new File(Logger.exceptionsFile);
			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(
					exception.getClass().getName().toString() + " " + exception.getMessage() + System.lineSeparator());
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}