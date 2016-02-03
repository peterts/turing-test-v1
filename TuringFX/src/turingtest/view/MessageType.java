package turingtest.view;

public enum MessageType {
	GUESS("guess-"),
	ANSWER("answer-"),
	NEW_ROUND("startnewround"),
	END_GAME("gamewasended"),
	READY("readytostart");
	
	private final String text;
	
	private MessageType(String text) {
		this.text = text;
	}
	
	public String toString(){
		return text;
	}
}
