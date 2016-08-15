package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameRunner extends Application{
	TheController tc;

	@Override
	public void start(Stage primaryStage) throws Exception {
		tc = new TheController(500,700);
		tc.start(primaryStage);
	}
	public static void main(String[] args){
		launch(args);
	}
}