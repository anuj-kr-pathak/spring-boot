package com.anuj.springboot.thymeleaf.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anuj.springboot.thymeleaf.model.Student;

@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
	
	//dynamic data rendering
	@RequestMapping("/sendData")
	public ModelAndView sendData() {
		ModelAndView mav = new ModelAndView("data");
		mav.addObject("message","Life is harder when you think it is harder");
		return mav;
	}
	
	//send a model from thymeleaf 
	@RequestMapping("/student")
	public ModelAndView getStudent() {
		//constructor argument variable name file should be present in template folder.
		ModelAndView mav = new ModelAndView("student");
		Student student =  new Student();
		student.setName("Practice");
		student.setScore(100);
		mav.addObject("student",student);
		return mav;
	}
	
	
	//for list of students 
	@RequestMapping("/students")
	public ModelAndView getStudents() {
		//constructor argument variable name file should be present in template folder.
		ModelAndView mav = new ModelAndView("studentList");
		Student student =  new Student();
		student.setName("Practice");
		student.setScore(100);
		
		Student student2 =  new Student();
		student2.setName("Hard");
		student2.setScore(1000);
		
		List<Student> students = Arrays.asList(student,student2);
		mav.addObject("students",students);
		return mav;
	}
	
	@RequestMapping("/studentForm")
	public ModelAndView displayStudentForm() {
		//constructor argument variable name file should be present in template folder.
		ModelAndView mav = new ModelAndView("studentForm");
		Student student =  new Student();
		mav.addObject("student",student);
		return mav;
	}
	
	@RequestMapping("/saveStudent")
	public ModelAndView saveStudent(@ModelAttribute Student student) {
		//constructor argument variable name file should be present in template folder.
		ModelAndView mav = new ModelAndView("result");
		System.out.println(student.getName());
		System.out.println(student.getScore());
		return mav;
	}
	
}
