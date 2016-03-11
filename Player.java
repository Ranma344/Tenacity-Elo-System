
public class Player {
	private String Name;
	private int Elo = 1600, Attendance = 0, MVP = 0, Win = 0, Lose = 0;
	
	
	public Player(String n, int e, int a, int m, int w, int l){
		Name = n;
		Elo = e;
		Attendance = a;
		MVP = m;
		Win = w;
		Lose = l;
	}
	public Player(String n){
		Name = n;
	}
	
	public Player() {
		
	}
	/***********************************************/
	public void setName(String n){
		Name = n;
	}
	
	public void setElo(int e){
		Elo = e;
	}
	
	public void setAttendance(int a){
		Attendance = a;
	}
	
	public void setMVP(int m){
		MVP = m;
	}
	
	public void setWins(int w){
		Win = w;
	}
	
	public void setLose(int l){
		Lose = l;
	}
	
	/************************************************/
	public void addAttendance(int e){
		Attendance += e;
	}
	public void addElo(int e){
		Elo += e;
	}
	public void addMVP(int e){
		MVP += e;
	}
	public void addWins(int e){
		Win += e;
	}
	public void addLose(int e){
		Lose += e;
	}
	/************************************************/
	public void calculateElo(int oE, String win){ 
		double tRatingP; //holds the players transformed Elo Rating for calculation
		double tRatingO; //holds the opponents transformed Elo Rating for calculation
		double expectedP; //Players expected score
		double pointP = 0;
		int eloValue = 32; //default elo value
		
		if (Elo > 2400){
			eloValue = 24;
		}
		
		switch(win){
			case "WIN":  pointP = 1;
						 break;
			case "DRAW": pointP = 0.5;
						 break;
			case "LOSE": pointP = 0;
					     break;
		}
		
		tRatingP = 10^(Elo/400);
		tRatingO = 10^(oE/400);
		
		expectedP = tRatingP/(tRatingP+tRatingO);
		
		this.setElo((int)Math.round(Elo + (eloValue*(pointP-expectedP))));
		
		
	}
	/************************************************/
	
	public String getName(){
		return Name;
	}
	
	public int getElo(){
		return Elo;
	}
	
	public int getAttendance(){
		return Attendance;	
	}
	
	public int getMVP(){
		return MVP;
	}
	
	public int getWin(){
		return Win;
	}
	
	public int getLose(){
		return Lose;
		
	}
	
	
}
