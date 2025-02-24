package com.indus.training.core.svc;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.indus.training.core.entity.Student;
import com.indus.training.core.service.StudentService;
import org.springframework.context.ApplicationContext;
import junit.framework.TestCase;

public class TestStudentService extends TestCase {

	StudentService studentService = null;

	protected void setUp() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		studentService = (StudentService) context.getBean("studentService");
	}

	protected void tearDown() throws Exception {
		studentService = null;
	}

	public void testSaveToFile() {
		Student stuObj = new Student();
		stuObj.setId(102);
		stuObj.setFirstName("Navya");
		stuObj.setLastName("Bade");

		// Act
		studentService.saveToFile(stuObj);

		// Assert
		Student savedStudent = studentService.loadFromFile(stuObj.getId());
		assertNotNull("Saved student should not be null", savedStudent);
		assertEquals("First name should match", stuObj.getFirstName(), savedStudent.getFirstName());
		assertEquals("Last name should match", stuObj.getLastName(), savedStudent.getLastName());

	}

	public void testLoadFromFile() {
		// Arrange
		int studentId = 103;
		
		Student expectedStudent = new Student();
		expectedStudent.setId(studentId);
		expectedStudent.setFirstName("Navya");
		expectedStudent.setLastName("Bade");
		studentService.saveToFile(expectedStudent);

		// Act
		Student loadedStudent = studentService.loadFromFile(studentId);

		// Assert
		assertNotNull("Loaded student should not be null", loadedStudent);
		assertEquals("First name should match", expectedStudent.getFirstName(), loadedStudent.getFirstName());
		assertEquals("Last name should match", expectedStudent.getLastName(), loadedStudent.getLastName());

	}

}
