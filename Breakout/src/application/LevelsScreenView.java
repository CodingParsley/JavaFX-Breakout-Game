package application;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import gameComponents.Ball;
import gameComponents.GameBoardModel;
import gameComponents.GameBoardView;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

public class LevelsScreenView extends Application {
	private Stage window;
	private Scene scene;
	private Pane layout;
	private Levels levels;
	private LevelsScreenModel lsm;

	public LevelsScreenView(LevelsScreenModel lsm) {
		this.lsm = lsm;
		levels = new Levels();
		layout = new Pane();
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		scene = new Scene(layout, TheController.getBoardWidth(), TheController.getBoardHeight());
		window.setScene(scene);
		window.show();
	}

	public void drawArrayOfButtons() {
		for (int column = 0; column < lsm.getButtons().length; column++) {

			for (int row = 0; row < lsm.getButtons()[column].length; row++) {

				gameComponents.Button b = lsm.getButtons()[column][row];
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
					GameBoardModel gbm=levels.findLevel(lsm.getSelectedLevelNum());
					GameBoardView gameView=new GameBoardView(new MovePaddleLeft(gbm),
					new MovePaddleRight(gbm));
						try {
							gameView.start(window);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						Timeline timeline = new Timeline(new KeyFrame(Duration.millis(6), ae -> updateScreen(gameView, gbm)));
						timeline.setCycleCount(Animation.INDEFINITE);
						timeline.play();
						window.show();
				});
			}
		}
	}

	// Method for redrawing bat and balls
	public void updateScreen(GameBoardView theView, GameBoardModel theModel) {
		// Temporary solution
		if (window.getScene() == theView.getScene()) {
			theView.drawRectangle(theModel.getBat());
			for(Ball ball: theModel.getBalls()){
			theView.drawRectangle(ball);
			}
			for (gameComponents.Brick brick : theModel.getBricks()) {
				theView.drawRectangle(brick);
			}
			theModel.updateAll();
		} else {
			try {
				theView.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// Connects Data Paddle Movement with view paddle movement
	class MovePaddleLeft implements Runnable {
		GameBoardModel theModel;

		MovePaddleLeft(GameBoardModel theModel) {
			this.theModel = theModel;
		}

		@Override
		public void run() {
			theModel.movePaddleLeft();
		}
	}

	class MovePaddleRight implements Runnable {

		GameBoardModel theModel;

		MovePaddleRight(GameBoardModel theModel) {
			this.theModel = theModel;
		}

		@Override
		public void run() {
			theModel.movePaddleRight();
		}
	}
}
