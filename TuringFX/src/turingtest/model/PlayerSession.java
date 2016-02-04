package turingtest.model;

public class PlayerSession {
	public final static int MAX_NUM_LINES = 10;
	public final static int MAX_POINTS_PER_ROUND = 100;
	
	private float availablePoints;
	private float prevTotalPoints;
	private float totalPoints;
	private int linesLeft;
	private int numGuesses;
	private TesterType guess;
	private TesterType answer;
	
	public PlayerSession(){
		linesLeft = MAX_NUM_LINES;
		availablePoints = MAX_POINTS_PER_ROUND;
		prevTotalPoints = 0;
		totalPoints = 0;
		numGuesses = 0;
	}
	
	//Points do not start decreasing before after the first line
	public boolean useLine(){
		if(linesLeft < MAX_NUM_LINES){
			availablePoints -= MAX_POINTS_PER_ROUND/MAX_NUM_LINES;
		}
		linesLeft -= 1;
		return linesLeft > 0;
	}
	
	public void registerGuess(TesterType guess){
		this.guess = guess;
		numGuesses += 1;
	}
	
	public void reset(){
		linesLeft = MAX_NUM_LINES;
		availablePoints = MAX_POINTS_PER_ROUND;
	}
	
	public void evaluateGuess(TesterType answer){
		this.answer = answer;
		prevTotalPoints = totalPoints;
		if(guess == answer){
			totalPoints += availablePoints;
		}
	}
	
	public int getLinesLeft(){
		return linesLeft;
	}
	
	public float getPrevTotalPoints(){
		return prevTotalPoints;
	}
	
	public float getTotalPoints(){
		return totalPoints;
	}
	
	public float getMaxPossiblePoints(){
		return MAX_POINTS_PER_ROUND * numGuesses;
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
