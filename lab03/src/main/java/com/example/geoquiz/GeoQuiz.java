package com.example.geoquiz;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SpringBootApplication
public class GeoQuiz extends Application {

	private static RestTemplate restTemplate;
	
	
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
		try {
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/com/example/geoquiz/gui/mainWindow.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(GeoQuiz.class);
		builder.headless(false).web(WebApplicationType.NONE);
		ConfigurableApplicationContext context = builder.run(args);

		restTemplate = context.getBean(RestTemplate.class);
		
		launch(args);
		
	}
	
	@SuppressWarnings("exports")
	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}


	public static void setRestTemplate(@SuppressWarnings("exports") RestTemplate restTemplate) {
		GeoQuiz.restTemplate = restTemplate;
	}


	@SuppressWarnings("exports")
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
}
