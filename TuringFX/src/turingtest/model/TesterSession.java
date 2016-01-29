package turingtest.model;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

public class TesterSession {
	private ChatterBot bot;
	private ChatterBotSession botSession;
	private boolean isBot = false;
	
	public TesterSession() throws Exception{
		bot = new ChatterBotFactory().create(ChatterBotType.CLEVERBOT);
//		startBotSession();
	}
	
	public void startBotSession(){
		botSession = bot.createSession();
	}
	
	public boolean isBot(){
		return isBot;
	}
	
	public void setIsBot(boolean isBot){
		this.isBot = isBot;
	}
	
	public String getBotResponse(String message){
		try {
			return botSession.think(message);
		} catch (Exception e) {
			return "";
		}
	}
	
	public TesterType getTesterType(){
		if(isBot){
			return TesterType.COMPUTER;
		}
		return TesterType.HUMAN;
	}
}
