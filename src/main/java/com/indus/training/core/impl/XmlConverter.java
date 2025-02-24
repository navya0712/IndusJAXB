package com.indus.training.core.impl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.indus.training.core.entity.Student;
import com.indus.training.core.svc.IConverter;

public class XmlConverter implements IConverter {

	@Override
	public String convertToFormat(Student student) {
		try {
			JAXBContext context = JAXBContext.newInstance(Student.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			marshaller.marshal(student, writer);
			return writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Student convertToJava(String input) {
		try {
			JAXBContext context = JAXBContext.newInstance(Student.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader reader = new StringReader(input);
			return (Student) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

}
