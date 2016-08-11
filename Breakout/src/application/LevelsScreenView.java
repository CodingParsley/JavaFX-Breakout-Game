package application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;

import gameComponenets.Variables;

public class LevelsScreenView extends Application {
	private Stage window;
	private Scene scene;
	private Pane layout;
	private LevelsScreenModel lsm;

	public LevelsScreenView(LevelsScreenModel lsm) {
		this.lsm = lsm;
		layout = new Pane();
	}

	public void drawArrayOfButtons() {
		for (int column = 0; column < lsm.getButtons().length; column++) {

			for (int row = 0; row < lsm.getButtons()[column].length; row++) {

				gameComponenets.Button b = lsm.getButtons()[column][row];
				Rectangle graphicalRect = new Rectangle();
				layout.getChildren().add(graphicalRect);
				// If statements are here for making the rectangle graphical

				graphicalRect.setId(Integer.toString(b.getId()));
				graphicalRect.setWidth(b.getWidth());
				graphicalRect.setHeight(b.getHeight());
				graphicalRect.setTranslateX(b.getTopLeftCoordinate().getX());
				graphicalRect.setTranslateY(b.getTopLeftCoordinate().getY());
				graphicalRect.setOnMouseClicked(e -> {
					b.getDoesSomething().run();
				});
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		scene = new Scene(layout, Variables.getWIDTH(), Variables.getHEIGHT());
		window.setScene(scene);
		window.show();
	}
}
