package turingtest.model;

public class PlayerSession {
	private final int maxNumLines = 20;
	private final int maxPointsPerRound = 100;
	
	private float availablePoints;
	private float totalPoints;
	private int linesLeft;
	private int numGuesses;
	private int numCorrectGuesses;
	private TesterType guess;
	
	public PlayerSession(){
		linesLeft = maxNumLines;
		availablePoints = maxPointsPerRound;
		totalPoints = 0;
		numCorrectGuesses = 0;
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
	
	private void reset(){
		linesLeft = maxNumLines;
		availablePoints = maxPointsPerRound;
	}
	
	public void evaluateGuess(TesterType answer){
		if(guess == answer){
			numCorrectGuesses += 1;
			totalPoints += availablePoints;
		}
		reset();	
	}
	
	public int getLinesLeft(){
		return linesLeft;
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
	
	public int getNumCorrectGuesses(){
		return numCorrectGuesses;
	}
}
