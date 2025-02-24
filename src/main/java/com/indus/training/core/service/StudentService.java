package com.indus.training.core.service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import com.indus.training.core.entity.Student;
import com.indus.training.core.svc.IConverter;

public class StudentService {

	private final IConverter converter;
	private final String directoryPath;

	public StudentService(IConverter converter) {
		this.converter = converter;
		this.directoryPath = loadDirectoryFromConfig();
	}

	private String loadDirectoryFromConfig() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				throw new IOException("Unable to find config.properties");
			}
			properties.load(input);
			return properties.getProperty("output.directory", "default/path");
		} catch (IOException e) {
			e.printStackTrace();
			return "default/path";
		}
	}

	public void saveToFile(Student student) {
		String fileName = "student_" + student.getId() + ".xml";
		String filePath = Paths.get(directoryPath, fileName).toString();

		String output = converter.convertToFormat(student);
		try (FileWriter fileWriter = new FileWriter(new File(filePath))) {
			fileWriter.write(output);
			System.out.println("File saved successfully at: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Student loadFromFile(int studentId) {
		String fileName = "student_" + studentId + ".xml";
		String filePath = Paths.get(directoryPath, fileName).toString();

		try {
			String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
			return converter.convertToJava(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
