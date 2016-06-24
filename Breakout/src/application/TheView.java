package application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.event.EventHandler;

public class TheView extends Application {
	private Stage window;
	private Scene scene;
	private Runnable onTypePaddleMoveLeft;
	private Runnable onTypePaddleMoveRight;
	private int width;
	private int height;
	Rectangle bat = new Rectangle();

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		Pane layout = new Pane();
		layout.getChildren().add(bat);
		scene = new Scene(layout, width, height);
		scene.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (k.getCode() == KeyCode.A) {
					onTypePaddleMoveLeft.run();
				}
				else if (k.getCode() == KeyCode.D) {
					onTypePaddleMoveRight.run();
				}
			}
		});
		window.setScene(scene);
		window.show();
	}

	public void MoveBat(application.Rectangle r) {
		bat.setWidth(r.getWidth());
		bat.setHeight(r.getHeight());
		bat.setTranslateX(r.getTopLeft().getX());
		bat.setTranslateY(r.getTopLeft().getY());
	}

	public TheView(Runnable onTypePaddleMoveLeft, Runnable onTypePaddleMoveRight,int width,int height) {
		this.onTypePaddleMoveLeft = onTypePaddleMoveLeft;
		this.onTypePaddleMoveRight = onTypePaddleMoveRight;
		this.width = width;
		this.height = height;
	}
}
