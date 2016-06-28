package application;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TheController extends Application{
	BoardModel bm;
	TheView view;
	Timeline timeline;
	Stage window;
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		bm = new BoardModel(500,500,30,01,40,5,5);
		Runnable onPaddleLeft = new MovePaddleLeft();
        Runnable onPaddleRight = new MovePaddleRight();
        view = new TheView(onPaddleLeft,onPaddleRight, bm.getWidth(), bm.getHeight());
		view.start(primaryStage);
		for(application.Rectangle b: bm.getBricks()){
			view.drawRectangle(b);
		}
		window.setOnCloseRequest(e -> {
			Runtime.getRuntime().exit(0);
		});
		timeline = new Timeline(new KeyFrame(
		        Duration.millis(2.3), // 240FPS
		        ae -> updateScreen()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		}
	class MovePaddleLeft implements Runnable{
		@Override
		public void run() {
			bm.movePaddleLeft();
			view.drawRectangle(bm.getBat());
		}
	}

	class MovePaddleRight implements Runnable{
		@Override
		public void run() {
			bm.movePaddleRight();
			view.drawRectangle(bm.getBat());
		}
	}
		public void updateScreen() {
			view.drawRectangle(bm.getBall());
			view.drawRectangle(bm.getBat());
			for(application.Rectangle b: bm.getBricks()){
				view.drawRectangle(b);
			}
			bm.update();
		}

	public static void main(String[] args){
		launch(args);
	}

}
