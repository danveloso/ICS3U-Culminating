import java.lang.reflect.Array;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import javafx.scene.paint.Color;

public class Movement {
//finding my spawnpoint
public void SpawnPoint(int[] spawn, Color color, Image src) {
    int width = (int)src.getWidth();
    int height = (int)src.getHeight();
    PixelReader reader = src.getPixelReader();
    for(int y = 0; y < height; y++) {
    	for(int x = 0; x < width; x++) {
    		Color ScannedColor = reader.getColor(x,y);
    		if(color.equals(ScannedColor)) {
    			spawn[0] = x;
    			spawn[1] = y;
    			System.out.println(spawn[0]);
    			System.out.println(spawn[1]);
    			break;
    		}
    	}
    }
}
	//Gravity for my player
public boolean Gravity(Player player,Color color, Image src) {
	boolean ground = false;
	double X = player.getCoordinateX();
	double Y = player.getCoordinateY();
    PixelReader reader = src.getPixelReader();
    if(!ground) {
    	player.setFallSpeed(player.getFallSpeed() + (player.getFallSpeed()/2));
    }
    Color ScannedColor = reader.getColor((int)X + player.getPlayerLength(),(int)Y + player.getPlayerWidth() + 1);
	if(ScannedColor.equals(color)) {
		ground = true;
	}
	ScannedColor = reader.getColor((int)X + (player.getPlayerLength()/2),(int)Y + player.getPlayerWidth() + 1);
	if(ScannedColor.equals(color)) {
		ground = true;
	}
	ScannedColor = reader.getColor((int)X,(int)Y + player.getPlayerWidth() + 1);
	if(ScannedColor.equals(color)) {
		ground = true;
	}
	return ground;
	}
//Checking if my player is colliding with given color (works for every color (ground, spike, etc)
public boolean Collision(Player player,Color color, Image src, double X, double Y) {
	boolean ground = false;
    PixelReader reader = src.getPixelReader();
    Color ScannedColor = reader.getColor((int)X,(int)Y);
    if(ScannedColor.equals(color)) {
    	ground = true;
    }
    
	return ground;
	}


}
