package com.example.geoquiz.gui;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.geoquiz.GeoQuiz;
import com.example.geoquiz.data.FirstQuote;
import com.example.geoquiz.data.SecondQuote;
import com.example.geoquiz.data.ThirdQuote;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainWindowController {

	@FXML
	private Button checkBtn1;

	@FXML
	private Button checkBtn2;

	@FXML
	private Button checkBtn3;

	@FXML
	private TextField q1UserAnsTxtField;

	@FXML
	private TextField q2UserAnsTxtField;

	@FXML
	private TextField q3UserAnsTxtField;

	@FXML
	private Button languageBtn;

	@FXML
	private Label question1_1Label;

	@FXML
	private Label question1AnswerLabel;

	@FXML
	private Label question1CorrectAnswerLabel;

	@FXML
	private TextField q1CorrectAnsTxtField;

	@FXML
	private TextField q2CorrectAnsTxtField;

	@FXML
	private TextField q3CorrectAnsTxtField;

	@FXML
	private ComboBox<String> question1ComboBox;

	@FXML
	private Label question2AnswerLabel;

	@FXML
	private Label question2CorrectAnswerLabel;

	@FXML
	private Label question3AnswerLabel;

	@FXML
	private Label question3CorrectAnswerLabel;

	@FXML
	private Label question2_1Label;

	@FXML
	private ComboBox<String> question2FirstComboBox;

	@FXML
	private TextField q2TextField;

	@FXML
	private Label question2_2Label;

	@FXML
	private Label question2_3Label;

	@FXML
	private TextField q3TextField;

	@FXML
	private Label question3Label;

	private String language = "pl";
	private String country = "PL";
	private String[] countryWikiCodes = { "Q30", "Q155", "Q414", "Q1033", "Q241" };
	// USA Q30
	// Brazil Q155
	// Argentina Q414
	private String[] countryIsoCodes = { "PL", "GB", "DE", "FI", "DK", "RO" };
	private ResourceBundle rb1;
	private String q1ReplyString;
	private String q2ReplyString;
	private String q3ReplyString;

	public void initialize() {
		setUpAllTexts();
	}

	public void checkBtn1ActionHandler(@SuppressWarnings("exports") ActionEvent event) {
		q1ReplyString = sendFirstQuery("https://wft-geo-db.p.rapidapi.com/v1/geo/countries/"
				+ countryIsoCodes[question1ComboBox.getSelectionModel().getSelectedIndex()]);
		
		if(q1UserAnsTxtField.getText().equals(q1ReplyString)) {
			checkBtn1.setStyle("-fx-background-color: #0e0;");
		}
		else {
			checkBtn1.setStyle("-fx-background-color: #e00;");
		}
		
		q1CorrectAnsTxtField.setText(q1ReplyString);
		
	}

	public void checkBtn2ActionHandler(@SuppressWarnings("exports") ActionEvent event) {
		
		q2ReplyString = sendSecondQuery("https://wft-geo-db.p.rapidapi.com/v1/geo/cities?limit=1&countryIds="
				+ countryWikiCodes[question2FirstComboBox.getSelectionModel().getSelectedIndex()] + "&minPopulation="
				+ q2TextField.getText().toString());
		
		if(q2UserAnsTxtField.getText().equals(q2ReplyString)) {
			checkBtn2.setStyle("-fx-background-color: #0e0;");
		}
		else {
			checkBtn2.setStyle("-fx-background-color: #e00;");
		}
		
		if (language.equals("pl")) {
			q2CorrectAnsTxtField.setText(formatSecondQuestionOutputPL(q2ReplyString));
		} else {
			q2CorrectAnsTxtField.setText(formatSecondQuestionOutputEN(q2ReplyString));
		}

	}

	public void checkBtn3ActionHandler(@SuppressWarnings("exports") ActionEvent event) {

		q3ReplyString = sendThirdQuery("https://wft-geo-db.p.rapidapi.com/v1/geo/countries?limit=1&namePrefix="
				+ q3TextField.getText().toString());
		
		if(q3UserAnsTxtField.getText().equals(q3ReplyString)) {
			checkBtn3.setStyle("-fx-background-color: #0e0;");
		}
		else {
			checkBtn3.setStyle("-fx-background-color: #e00;");
		}

		q3CorrectAnsTxtField.setText(formatThirdQuestionOutput(q3ReplyString));
	}

	public void switchLanguageBtnActionHandler(@SuppressWarnings("exports") ActionEvent event) {
		if (language.equals("pl")) {
			language = "en";
			country = "EN";
		} else {
			language = "pl";
			country = "PL";
		}

		setUpAllTexts();

	}

	private String formatSecondQuestionOutputPL(String reply) {
		MessageFormat messageFormat = new MessageFormat("");
		messageFormat.setLocale(rb1.getLocale());
		messageFormat.applyPattern(rb1.getString("secondQuestionOutput"));
		Object[] arguments = { reply };
		String output = messageFormat.format(arguments);
		return output;
	}

	private String formatSecondQuestionOutputEN(String reply) {
		MessageFormat messageFormat = new MessageFormat("");
		messageFormat.setLocale(rb1.getLocale());
		double[] limits = { 0, 1, 2 };
		String[] values = { rb1.getString("anyCity"), rb1.getString("oneCity"), rb1.getString("moreCities") };
		ChoiceFormat choiceFormat = new ChoiceFormat(limits, values);
		Format[] formats = { choiceFormat, NumberFormat.getInstance() };
		messageFormat.applyPattern(rb1.getString("secondQuestionOutput"));
		messageFormat.setFormats(formats);
		Object[] arguments = { Integer.parseInt(reply), Integer.parseInt(reply) };
		String output = messageFormat.format(arguments);
		return output;
	}

	private String formatThirdQuestionOutput(String reply) {
		MessageFormat messageFormat = new MessageFormat("");
		messageFormat.setLocale(rb1.getLocale());
		Object[] arguments = { reply };
		messageFormat.applyPattern(rb1.getString("thirdQuestionOutput"));
		String output = messageFormat.format(arguments);
		System.out.println(output);
		return output;
	}

	private String sendThirdQuery(String url) {

		HttpEntity<String> request = prepareQueryHeaders();

		// make an HTTP GET request with headers
		ResponseEntity<ThirdQuote> response = GeoQuiz.getRestTemplate().exchange(url, HttpMethod.GET, request,
				ThirdQuote.class);
		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful.");
			System.out.println(response.getBody());
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}

		ThirdQuote quote = response.getBody();

		return quote.toString();
	}

	private String sendSecondQuery(String url) {

		HttpEntity<String> request = prepareQueryHeaders();

		// make an HTTP GET request with headers
		ResponseEntity<SecondQuote> response = GeoQuiz.getRestTemplate().exchange(url, HttpMethod.GET, request,
				SecondQuote.class);
		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful.");
			System.out.println(response.getBody());
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}

		SecondQuote quote = response.getBody();

		return String.valueOf(quote.getMetadata().getTotalCount());
	}

	private String sendFirstQuery(String url) {

		HttpEntity<String> request = prepareQueryHeaders();

		// make an HTTP GET request with headers
		ResponseEntity<FirstQuote> response = GeoQuiz.getRestTemplate().exchange(url, HttpMethod.GET, request,
				FirstQuote.class);

		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("Request Successful.");
			System.out.println(response.getBody());
		} else {
			System.out.println("Request Failed");
			System.out.println(response.getStatusCode());
		}

		FirstQuote quote = response.getBody();
		return quote.getData().toString();
	}

	private HttpEntity<String> prepareQueryHeaders() {
		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-rapidapi-key", "e267e18274mshd07ba90b4acc53bp1d0dcbjsn72eb0161378d");
		headers.add("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		// build the request
		HttpEntity<String> request = new HttpEntity<String>(headers);
		return request;
	}

	private void setUpAllTexts() {
		Locale l = new Locale(language, country);

		rb1 = ResourceBundle.getBundle("MyBundle", l);
		String[] splitedCountryQ1Strings = rb1.getString("countriesQ1").split(",");
		String[] splitedCountryQ2Strings = rb1.getString("countriesQ2").split(",");
		
		q1UserAnsTxtField.setPromptText(rb1.getString("q1PromptText"));
		q2TextField.setPromptText(rb1.getString("q2PromptText"));
		q3TextField.setPromptText(rb1.getString("q3PromptText"));

		question1ComboBox.getItems().setAll(splitedCountryQ1Strings);
		question2FirstComboBox.getItems().setAll(splitedCountryQ2Strings);
		
		question1ComboBox.getSelectionModel().select(0);
		question2FirstComboBox.getSelectionModel().select(0);

		question2_1Label.setText(rb1.getString("question2_1"));
		question2_2Label.setText(rb1.getString("question2_2"));
		question2_3Label.setText(rb1.getString("question2_3"));

		question1_1Label.setText(rb1.getString("question1"));
		question1AnswerLabel.setText(rb1.getString("myanswerlabel"));
		question1CorrectAnswerLabel.setText(rb1.getString("correctAnslabel"));

		question2AnswerLabel.setText(rb1.getString("myanswerlabel"));
		question2CorrectAnswerLabel.setText(rb1.getString("correctAnslabel"));

		question3Label.setText(rb1.getString("question3"));
		question3AnswerLabel.setText(rb1.getString("myanswerlabel"));
		question3CorrectAnswerLabel.setText(rb1.getString("correctAnslabel"));

		checkBtn1.setText(rb1.getString("check"));
		checkBtn2.setText(rb1.getString("check"));
		checkBtn3.setText(rb1.getString("check"));

		if (!q2CorrectAnsTxtField.getText().equals("") && language.equals("pl")) {
			q2CorrectAnsTxtField.setText(formatSecondQuestionOutputPL(q2ReplyString));
		} else if (!q2CorrectAnsTxtField.getText().equals("") && language.equals("en")) {
			q2CorrectAnsTxtField.setText(formatSecondQuestionOutputEN(q2ReplyString));
		}

		if (!q3CorrectAnsTxtField.getText().equals("")) {
			q3CorrectAnsTxtField.setText(formatThirdQuestionOutput(q3ReplyString));
		}

	}

}
