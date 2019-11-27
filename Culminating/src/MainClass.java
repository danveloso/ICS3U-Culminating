/*Daniel Veloso (Mr. R)
 * ISC3U-01 
 * Culminating assignment
 * 
 * platformer game where you can make your own map on paint choosing where and how to place everything and the game will still work. it consists of 4 colors, ground, spike,
 * spawn, and endpoint. Each is placed from (0,0) onwards by an x value of 1 to make a "legend" for my program.
 * 2019/01/21
 */
import java.io.File;
import java.lang.reflect.Array;
import java.util.TimerTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
public class MainClass extends Application {
	private Group root,root1,sceneObjects,root2;
	private Scene scene,MainMenu,endScene;
	Movement movement = new Movement();
	private int SceneY,SceneX,srcwidth,srcheight,deathcounter;
	int[] spawncoords;
	private Player player;
	private Image src,eggleft,eggright,eggstill,endScreen;
	private ImageView egg;
	private boolean death;
	private File image;
	private Color floor,spike,spawn,endpoint;
	private Label counter,totalDeaths;
	private Button Play, Exit;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//big mess of adding values and setting variables and initiating buttons, honestly didn't have time to organize this mess sorry
		sceneObjects = new Group();
		root1 = new Group();
		spawncoords = new int[2]; 
		deathcounter = 0;
		int MenuX = 500; 
		int MenuY = 600; 
		root2 = new Group();
		MainMenu = new Scene(root1, MenuX, MenuY);
		endScene = new Scene(root2, MenuX, MenuY);
		root = new Group();
		eggstill = new Image("egg.png");
		eggleft = new Image("eggleft.png");
		eggright = new Image("eggright.png");
		Play = new Button("Play");
		Exit = new Button("Exit");
		Play.setPrefSize(250, 100);
		Exit.setPrefSize(250, 100);
		VBox vb = new VBox();
		vb.setPadding(new Insets(MenuX/4));
		root1.getChildren().add(vb);
		vb.getChildren().addAll(Play,Exit);	
		Play.setOnAction(e -> primaryStage.setScene(scene)); 
		
		primaryStage.setTitle("Anxious Egg Game");
		primaryStage.setScene(MainMenu);
		primaryStage.setX(200);
		primaryStage.show();
		
		
	    //finding my spawn point in the map and opening the chosen map
		image = new FileChooser().showOpenDialog(primaryStage);
		src = new Image(image.toURI().toString());
		endScreen = new Image("WinScreen.png");
	    srcwidth = (int)src.getWidth();
	    srcheight = (int)src.getHeight();
	    PixelReader reader = src.getPixelReader();
	    floor = reader.getColor(0, 0);
	    spike = reader.getColor(1, 0);
	    spawn = reader.getColor(2, 0); 
	    endpoint = reader.getColor(3, 0);
		movement.SpawnPoint(spawncoords, spawn, src);
		
		
		ImageView srcView = new ImageView(src);
		ImageView endscreen = new ImageView(endScreen);
		player = new Player(spawncoords[0],spawncoords[1]);
		egg = new ImageView(eggstill); 
		SceneX = srcwidth;
		SceneY = srcheight;
		totalDeaths = new Label();
		totalDeaths.setFont(new Font("Franklin Gothic",25));
		scene = new Scene(root, SceneX,SceneY);
		root.getChildren().add(sceneObjects);
		root2.getChildren().addAll(endscreen,totalDeaths);
		sceneObjects.getChildren().addAll(srcView,egg);
		primaryStage.setResizable(false);
		counter = new Label();
		
		counter.setFont(new Font("Franklin Gothic",15));
		counter.setTextFill(Color.color( Math.random(), Math.random(), Math.random()));
		root.getChildren().add(counter);
		
		
		//Movement of player
		scene.setOnKeyPressed(d -> {
		    if(d.getCode() == KeyCode.A || d.getCode() == KeyCode.LEFT) {		        
		        player.setSpeed(-10);
		    }
		    if(d.getCode() == KeyCode.D || d.getCode() == KeyCode.RIGHT) {
		        player.setSpeed(10);
		    }
		    if(d.getCode() == KeyCode.W || d.getCode() == KeyCode.SPACE || d.getCode() == KeyCode.UP) {
		    	if(movement.Gravity(player,floor, src)) {
		    		player.setJumpSpeed(-15);
		    		
		    	}
		    	
		    		
		    }
		});
		
		scene.setOnKeyReleased(d -> {
		    if(d.getCode() == KeyCode.A || d.getCode() == KeyCode.LEFT) {

		        player.setSpeed(0);
		    }
		    if(d.getCode() == KeyCode.D || d.getCode() == KeyCode.RIGHT) {
		        player.setSpeed(0);
		    }
		    if(d.getCode() == KeyCode.W || d.getCode() == KeyCode.SPACE || d.getCode() == KeyCode.UP) {
		        player.setJumpSpeed(0);
		    }
		});
		
		

		
		//timer
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				upDate(primaryStage);
			}
		};
		timer.start();
		

	}
	
	public void upDate(Stage primaryStage)
	{
		
	
		counter.setText("Death Counter: " + deathcounter);
		//previous player coords
		
		double old_y = player.getCoordinateY();
		double old_x = player.getCoordinateX();
		
		//checking if I hit edges of the window
		if(old_y + (player.getPlayerWidth() + player.getPlayerWidth()/2) > SceneY) {
			old_x = spawncoords[0];
			old_y = spawncoords[1] - player.getPlayerWidth();
		}			
		
		if(old_x < 0) {
				old_x = 0 + 3;
				System.out.println("egg");
		}else if(old_x + player.getPlayerWidth() > SceneX) {
				old_x = (SceneX-player.getPlayerLength())-3;
		}
		
		
		
		//Doing Color Pixel checks for: Spikes and Endpoint
		else {
			if(movement.Collision(player,spike, src,player.getCoordinateX()+ player.getPlayerLength(), player.getCoordinateY() + player.getPlayerWidth()-6) || 
			movement.Collision(player,spike, src,player.getCoordinateX()+(player.getPlayerLength()/2), player.getCoordinateY() + player.getPlayerWidth()-6) ||
			 movement.Collision(player,spike, src,player.getCoordinateX(), player.getCoordinateY() + player.getPlayerWidth()-6)  ||
			 movement.Collision(player,spike, src,player.getCoordinateX(), player.getCoordinateY()) ||
			 movement.Collision(player,spike, src,player.getCoordinateX() + player.getPlayerLength(), player.getCoordinateY())) {
				old_x = spawncoords[0];
				old_y = spawncoords[1] - player.getPlayerWidth();
				death = true;
				}
			if(movement.Collision(player,endpoint, src,player.getCoordinateX()+ player.getPlayerLength(), player.getCoordinateY() + player.getPlayerWidth()-6) || 
					 movement.Collision(player,endpoint, src,player.getCoordinateX(), player.getCoordinateY() + player.getPlayerWidth()-6)  ||
					 movement.Collision(player,endpoint, src,player.getCoordinateX(), player.getCoordinateY()) ||
					 movement.Collision(player,endpoint, src,player.getCoordinateX() + player.getPlayerLength(), player.getCoordinateY())) {
						player.setSpeed(0);
						primaryStage.setScene(endScene);
						totalDeaths.setText("Total Deaths: " + deathcounter);
						totalDeaths.setTranslateX(100);
						totalDeaths.setTranslateY(250);
						}
			//Collision with colors on each side
			
			//X for bottom right
			if(movement.Collision(player,floor, src,player.getCoordinateX()+ player.getPlayerLength(), player.getCoordinateY() + player.getPlayerWidth()-6)) {
				player.setCoordinateX(old_x-3);
				player.setSpeed(0);
				old_x = player.getCoordinateX();
				}
			
			//Y for bottom right
			if(movement.Collision(player,floor, src,player.getCoordinateX() + player.getPlayerLength(), player.getCoordinateY() + player.getPlayerLength()*0.9)) {
				player.setCoordinateY(old_y - 3);
				player.setJumpSpeed(0);
				old_y = player.getCoordinateY();
				}
			//X for bottom left
			if(movement.Collision(player,floor, src,player.getCoordinateX(), player.getCoordinateY() + player.getPlayerWidth()-6)) {
				player.setCoordinateX(old_x+(3));
				player.setSpeed(0);
				old_x = player.getCoordinateX();
				}
			
			//Y for bottom left
			if(movement.Collision(player,floor, src,player.getCoordinateX(), player.getCoordinateY() + player.getPlayerLength()*0.9)) {
				player.setCoordinateY(old_y - 3);
				player.setJumpSpeed(0);
				old_y = player.getCoordinateY();;
				}
			//Y for top left
			if(movement.Collision(player,floor, src,player.getCoordinateX(), player.getCoordinateY())) {
				player.setCoordinateY(old_y + 3);
				player.setJumpSpeed(0);
				old_y = player.getCoordinateY();
			}
			//Y for top right
			if(movement.Collision(player,floor, src,player.getCoordinateX() + player.getPlayerLength(), player.getCoordinateY())) {
				player.setCoordinateY(old_y + 3);
				player.setJumpSpeed(0);
				old_y = player.getCoordinateY();
			}
			if(!movement.Gravity(player, floor, src)) {
				player.setFallSpeed(5);
			}
			if(movement.Gravity(player, floor, src)) {
				player.setFallSpeed(0);
			}

				
			}
			//adding deaths every time you die
			if(death) {
				deathcounter++;
				counter.setTextFill(Color.color( Math.random(), Math.random(), Math.random()));

				death = false;
			}
			//sprite of my player (really ugly.. I can't draw haha)
			if(player.getSpeed() < 0) {
				egg.setImage(eggleft);
			}
			if(player.getSpeed() > 0) {
				egg.setImage(eggright);
				
			}
			if(player.getSpeed() == 0) {
				egg.setImage(eggstill);
				
			}
			//updating the coordinates of my player
			egg.setX(player.getCoordinateX());
			egg.setY(player.getCoordinateY());
			player.setCoordinateY(old_y + player.getFallSpeed() + player.getJumpSpeed());
			player.setCoordinateX(old_x+ player.getSpeed());
			counter.toFront();
		}
	}
