
public class Player {
	private double coordinateX;
	private double coordinateY;
	private int playerLength;
	private int playerWidth;
	private int HP;
	private int Attk;
	private int Speed;
	private int JumpSpeed;
	private int FallSpeed;
	private boolean floorcol;
	private boolean wallcol;
	
	public Player (double X, double Y) {
		coordinateX = X;
		coordinateY = Y;
		playerLength = 32;
		playerWidth = 32;
		Attk = 10;
		Speed = 0;
		JumpSpeed = 0;
		FallSpeed = 5;
	}
	public int getHP() {
		return HP;
	}
	public int getFallSpeed() {
		return FallSpeed;
	}
	public void setFallSpeed(int fallSpeed) {
		FallSpeed = fallSpeed;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getAttk() {
		return Attk;
	}
	public void setAttk(int attk) {
		Attk = attk;
	}
	public int getSpeed() {
		return Speed;
	}
	public void setSpeed(int speed) {
		Speed = speed;
	}

	public double getCoordinateX() {
		return coordinateX;
	}
	public void setCoordinateX(double coordinateX) {
		this.coordinateX = coordinateX;
	}
	public double getCoordinateY() {
		return coordinateY;
	}
	public void setCoordinateY(double coordinateY) {
		this.coordinateY = coordinateY;
	}
	
	public boolean isFloorcol() {
		return floorcol;
	}
	public void setFloorcol(boolean floorcol) {
		this.floorcol = floorcol;
	}
	public boolean isWallcol() {
		return wallcol;
	}
	public void setWallcol(boolean wallcol) {
		this.wallcol = wallcol;
	}

	public int getJumpSpeed() {
		return JumpSpeed;
	}
	public void setJumpSpeed(int jumpSpeed) {
		JumpSpeed = jumpSpeed;
	}
public int getPlayerLength() {
		return playerLength;
	}
	public void setPlayerLength(int playerLength) {
		this.playerLength = playerLength;
	}
	public int getPlayerWidth() {
		return playerWidth;
	}
	public void setPlayerWidth(int playerWidth) {
		this.playerWidth = playerWidth;
	}
public String toString() {
	String result = "HealthPoints:" +  HP + "Attack: "+ Attk + "Speed: " + Speed + "Coordinates: " + coordinateX + coordinateY;
	return result;
	}

	
}
