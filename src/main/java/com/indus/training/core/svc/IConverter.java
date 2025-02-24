package com.indus.training.core.svc;

import com.indus.training.core.entity.Student;

public interface IConverter {
	String convertToFormat(Student student);

	Student convertToJava(String input);
}
