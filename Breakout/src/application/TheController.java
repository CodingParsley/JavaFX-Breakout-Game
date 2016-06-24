package application;
import javafx.application.Application;
import javafx.stage.Stage;

public class TheController extends Application{
	BoardModel bm = new BoardModel();
	TheView view;
	@Override
	public void start(Stage primaryStage) throws Exception {
		Runnable onPaddleLeft = new MovePaddleLeft();
        Runnable onPaddleRight = new MovePaddleRight();
        view = new TheView(onPaddleLeft,onPaddleRight, bm.getWidth(), bm.getHeight());
		view.start(primaryStage);
	}

	public static void main(String[] args){
		launch(args);
	}

	class MovePaddleLeft implements Runnable{
		@Override
		public void run() {
			bm.movePaddleLeft();
			view.MoveBat(bm.bat);
		}
	}

	class MovePaddleRight implements Runnable{
		@Override
		public void run() {
			bm.movePaddleRight();
			view.MoveBat(bm.bat);
		}
	}
}
