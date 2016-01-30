package turingtest.model;

public enum TesterType {
	HUMAN("HUMAN"), COMPUTER("COMPUTER");
	
	private final String text;
	
	TesterType(String text){
		this.text = text;
	}
	
	public String toString(){
		return text;
	}
	
	public static TesterType getType(String text){
		for(TesterType t : TesterType.values()){
			if(t.text.equals(text)){
				return t;
			}
		}
		return null;
	}
}
