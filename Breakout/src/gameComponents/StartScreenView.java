package gameComponents;
import application.TheController;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartScreenView extends Application{
	private Button playButton;
	public StartScreenView(){
		playButton = new Button("Play");
		playButton.setPrefWidth(50);
		playButton.setPrefHeight(50);
		playButton.setTranslateX((TheController.getBoardWidth()/2)-playButton.getPrefWidth()/2);
		playButton.setTranslateY((TheController.getBoardHeight()/2)-playButton.getPrefHeight()/2-40);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane layout = new Pane();
		layout.getChildren().add(playButton);
		primaryStage.setScene(new Scene(layout,TheController.getBoardWidth(),TheController.getBoardHeight()));
		primaryStage.show();
	}
	public Button getPlay() {
		return playButton;
	}

}
