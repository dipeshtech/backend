package com.iris.backend.response;

public class IntentResponse {
	
	private  String Question;
	private String Type;
	private String Answer;
	private Double probability;
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return Question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		Question = question;
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return Answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		Answer = answer;
	}
	/**
	 * @return the probability
	 */
	public Double getProbability() {
		return probability;
	}
	/**
	 * @param probability the probability to set
	 */
	public void setProbability(Double probability) {
		this.probability = probability;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
}
