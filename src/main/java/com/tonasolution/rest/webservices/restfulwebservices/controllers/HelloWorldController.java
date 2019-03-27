package com.tonasolution.rest.webservices.restfulwebservices.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tonasolution.rest.webservices.restfulwebservices.entity.HelloWorldBean;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorlderInternationalized() {
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path = "/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	
}
