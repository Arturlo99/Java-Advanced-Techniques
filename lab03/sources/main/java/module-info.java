module lab03_JAVA {
	exports com.example.geoquiz.gui;
	exports com.example.geoquiz.data;
	exports com.example.geoquiz;

	opens com.example.geoquiz to spring.core;
	opens com.example.geoquiz.gui to javafx.fxml;
	
	requires com.fasterxml.jackson.annotation;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.core;
	requires spring.web;
}