package turingtest;

public enum MessageType {
	SERVER_GREETING("helloimserver"),
	CLIENT_GREETING("helloserverimclient"),
	SERVER_RESPONSE("myipis-"),
	PLAYER_GUESSS("guess-"),
	TESTER_ANSWER("answer-");
	
	
	private final String message;
	
	private MessageType(String message) {
		this.message = message;
	}
	
	public String toString(){
		return message;
	}
}
