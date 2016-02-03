package turingtest.model;

public class PlayerSession {
	private final int maxNumLines = 10;
	private final int maxPointsPerRound = 100;
	
	private float availablePoints;
	private float prevTotalPoints;
	private float totalPoints;
	private int linesLeft;
	private int numGuesses;
	private TesterType guess;
	private TesterType answer;
	
	public PlayerSession(){
		linesLeft = maxNumLines;
		availablePoints = maxPointsPerRound;
		prevTotalPoints = 0;
		totalPoints = 0;
		numGuesses = 0;
	}
	
	//Points do not start decreasing before after the first line
	public boolean useLine(){
		if(linesLeft < maxNumLines){
			availablePoints -= maxPointsPerRound/maxNumLines;
		}
		linesLeft -= 1;
		return linesLeft > 0;
	}
	
	public void registerGuess(TesterType guess){
		this.guess = guess;
		numGuesses += 1;
	}
	
	public void reset(){
		linesLeft = maxNumLines;
		availablePoints = maxPointsPerRound;
	}
	
	public void evaluateGuess(TesterType answer){
		this.answer = answer;
		if(guess == answer){
			prevTotalPoints = totalPoints;
			totalPoints += availablePoints;
		}
	}
	
	public int getLinesLeft(){
		return linesLeft;
	}
	
	public int getMaxNumLines(){
		return maxNumLines;
	}
	
	public float getPrevTotalPoints(){
		return prevTotalPoints;
	}
	
	public float getTotalPoints(){
		return totalPoints;
	}
	
	public float getAvailablePoints(){
		return availablePoints;
	}
	
	public int getNumGuesses(){
		return numGuesses;
	}
	
	public TesterType getGuess(){
		return guess;
	}
	
	public TesterType getAnswer(){
		return answer;
	}
}
