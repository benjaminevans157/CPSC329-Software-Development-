package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

import Model.Board;
import Model.Controller;
import Model.OfflineController;
import Model.Piece;
import Model.Player;
import Model.Position;
import Model.Space;
import Variations.Anarchohedgehog;
import Variations.Backwards;
import Variations.ConcreteBlock;
import Variations.CrissCross;
import Variations.Default;
import Variations.FizzyLifting;
import Variations.GameRules;
import Variations.Urhedgehog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author al9226
 */

public class HedgehogGUI extends Application {

	private Image[] images_;// The faces of the roller die
	private ImageView imageView_;
	private Controller controller_;

	private Canvas boardCanvas_;// The canvas containing the board
	private Canvas overlay_;// The canvas for resizing the board
	private Canvas pieceOverlay_;// The canvas for dragging pieces
	private BorderPane root_;// The scene root_
	private GraphicsContext g_;// Graphics context for drawing dragged pieces
	private Button skipTurnButton_; // button for skipping your turn
	private Label playerLabel_;// The display of curr player

	private Board board_;
	private Piece dragged_;// The piece the user is dragging with the mouse
	private int startRow_;// The original position of the dragged piece
	private int startCol_;
	private HashMap<String,GameRules> rules_;// The rule variations and their
	// names
	private Color[] colors_;// The colors of the pieces in order of player

	private int roll_;// The current roll

	private Piece testPiece_;// A piece for testing

	public static void main ( String[] args ) {

		launch(args);

	}

	@Override
	public void start ( Stage stage ) throws Exception {
		initRules();
		dragged_ = null;

		root_ = new BorderPane();
		Scene scene = new Scene(root_);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setHeight(500);
		stage.setWidth(500);
		stage.setTitle("Hedgehogs in a Hurry");

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(5,5,5,5));
		vbox.setSpacing(5);

		boardCanvas_ = new Canvas();
		boardCanvas_.setWidth(500);
		boardCanvas_.setHeight(500);

		overlay_ = new Canvas();
		overlay_.setWidth(boardCanvas_.getWidth());
		overlay_.setHeight(boardCanvas_.getHeight());

		pieceOverlay_ = new Canvas();
		pieceOverlay_.setWidth(boardCanvas_.getWidth());
		pieceOverlay_.setHeight(boardCanvas_.getHeight());

		// the game board
		StackPane stack = new StackPane(boardCanvas_,overlay_,pieceOverlay_);
		stack.setPrefSize(boardCanvas_.getWidth(),boardCanvas_.getHeight());
		stack.setPrefSize(boardCanvas_.getWidth(),boardCanvas_.getHeight());
		stack.setMinSize(0,0);
		g_ = pieceOverlay_.getGraphicsContext2D();// Initializing PieceOverlay G
		// context

		// code for resizing the board
		boardCanvas_.widthProperty()
		    .bind(new When(stack.widthProperty().lessThan(stack.heightProperty()))
		        .then(stack.widthProperty()).otherwise(stack.heightProperty()));
		boardCanvas_.heightProperty().bind(boardCanvas_.widthProperty());
		overlay_.widthProperty().bind(boardCanvas_.widthProperty());
		overlay_.heightProperty().bind(boardCanvas_.heightProperty());
		pieceOverlay_.widthProperty().bind(boardCanvas_.widthProperty());
		pieceOverlay_.heightProperty().bind(boardCanvas_.heightProperty());

		boardCanvas_.widthProperty().addListener(e -> {
			drawBoard(root_);
		});
		boardCanvas_.heightProperty().addListener(e -> {
			drawBoard(root_);
		});

		// background image
		Image image = new Image("file:images/hedgehog.jpg");
		ImageView imageview = new ImageView(image);
		imageview.setPreserveRatio(false);
		imageview.fitWidthProperty().bind(root_.widthProperty());
		imageview.fitHeightProperty().bind(root_.heightProperty());
		root_.getChildren().addAll(imageview);

		// vboxes for each screen
		VBox startVbox = new VBox();
		startVbox.setAlignment(Pos.CENTER);
		startVbox.setSpacing(5);
		root_.setCenter(startVbox);

		VBox createVbox = new VBox();
		createVbox.setAlignment(Pos.CENTER);
		createVbox.setSpacing(5);
		createVbox.setPadding(new Insets(30,0,5,0));

		VBox gameVbox = new VBox();
		playerLabel_ = new Label();

		// opening screen
		Button exit = new Button("Exit");
		exit.setPrefWidth(62.5);
		exit.setOnAction(e -> exit());

		Button join = new Button("Join");
		join.setPrefWidth(125);

		join.setOnAction(e -> {
			// TextInputDialog TIP = new TextInputDialog();
			// TIP.setHeaderText("IP:");
			// TIP.setTitle("Join Game");
			// TIP.showAndWait();
			root_.setRight(gameVbox);
			root_.setCenter(stack);
			// open join screen
			drawBoard(root_);
		});

		// opens the create window
		Button create = new Button("Create");
		create.setPrefWidth(125);
		create.setOnAction(e -> {
			createVbox.getChildren().addAll(exit);
			root_.setCenter(createVbox);
		});

		startVbox.getChildren().addAll(join,create,exit);

		// lobby screen

		// event handlers are needed for each option
		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().addAll("Urehedgehog mode","Anarchohedgehog mode",
		                           "Backwards mode","Concrete Block mode",
		                           "Criss-Cross mode","Fizzy Lifting mode",
		                           "Default mode");

		// player buttons and player array
		Button[] playerArray = new Button[6];

		Button player1 = new Button("Empty");
		playerArray[0] = player1;
		player1.setOnAction(e -> {
			if ( player1.getText().equals("Empty") ) player1.setText("Player 1");
			else if ( player1.getText().equals("Player 1") ) player1.setText("CPU 1");
			else player1.setText("Empty");
		});
		Button player2 = new Button("Empty");
		playerArray[1] = player2;
		player2.setOnAction(e -> {
			if ( player2.getText().equals("Empty") ) player2.setText("Player 2");
			else if ( player2.getText().equals("Player 2") ) player2.setText("CPU 2");
			else player2.setText("Empty");
		});
		Button player3 = new Button("Empty");
		playerArray[2] = player3;
		player3.setOnAction(e -> {
			if ( player3.getText().equals("Empty") ) player3.setText("Player 3");
			else if ( player3.getText().equals("Player 3") ) player3.setText("CPU 3");
			else player3.setText("Empty");
		});
		Button player4 = new Button("Empty");
		playerArray[3] = player4;
		player4.setOnAction(e -> {
			if ( player4.getText().equals("Empty") ) player4.setText("Player 4");
			else if ( player4.getText().equals("Player 4") ) player4.setText("CPU 4");
			else player4.setText("Empty");
		});
		Button player5 = new Button("Empty");
		playerArray[4] = player5;
		player5.setOnAction(e -> {
			if ( player5.getText().equals("Empty") ) player5.setText("Player 5");
			else if ( player5.getText().equals("Player 5") ) player5.setText("CPU 5");
			else player5.setText("Empty");
		});
		Button player6 = new Button("Empty");
		playerArray[5] = player6;
		player6.setOnAction(e -> {
			if ( player6.getText().equals("Empty") ) player6.setText("Player 6");
			else if ( player6.getText().equals("Player 6") ) player6.setText("CPU 6");
			else player6.setText("Empty");
		});

		Region spacer = new Region();
		VBox.setVgrow(spacer,Priority.ALWAYS);

		TextField ipInput = new TextField("Enter IP");
		ipInput.setAlignment(Pos.BASELINE_CENTER);
		ipInput.setVisible(false);

		Button selectOffline = new Button("Play Online");
		selectOffline.setOnAction(e -> {
			if ( selectOffline.getText().equals("Play Offline") ) {
				ipInput.setVisible(false);
				selectOffline.setText("Play Online");
			} else {
				ipInput.setVisible(true);
				selectOffline.setText("Play Offline");
			}
		});

		Button startGame = new Button("Start Game");
		startGame.setPrefWidth(200);
		ArrayList<Player> playerList = new ArrayList<Player>();
		startGame.setOnAction(e -> {
			// Offline Game
			if ( selectOffline.getText().equals("Play Online") ) {
				for ( int i = 0 ; i < 6 ; i++ ) {
					if ( !playerArray[i].getText().equals("Empty") )
					  playerList.add(new Player(colors_[i],
					                            playerArray[i].getText().split(" ")[0]));
				}
				if ( playerList.isEmpty() == true ) {
					displayError("No players selected");
				} else {
					root_.setCenter(stack);
					root_.setRight(gameVbox);

					if ( comboBox.getValue() == null ) {
						controller_ =
						    new OfflineController(this,rules_.get("Default mode"));
					} else {
						controller_ =
						    new OfflineController(this,rules_.get(comboBox.getValue()));
					}
					controller_.startGame(playerList);
					board_ = controller_.getBoard();
					drawBoard(root_);
				}
			}
			// OnlineGame
			if ( selectOffline.getText().equals("Play Online") ) {
				createWaitingRoom();

			}

		});

		createVbox.getChildren().addAll(player1,player2,player3,player4,player5,
		                                player6,comboBox,spacer,ipInput,
		                                selectOffline,startGame);

		// dice holder
		images_ = new Image[6];
		for ( int i = 1 ; i < 7 ; i++ ) {
			images_[i - 1] = new Image("file:images/" + i + ".jpg");
		}
		imageView_ = new ImageView(images_[0]);

		// game screen
		skipTurnButton_ = new Button("Skip Turn");
		skipTurnButton_.setDisable(true);
		skipTurnButton_.setOnAction(e -> {
			if ( controller_.isAvaliableMove() )
			  displayError("You have an avaliable move!");
			else controller_.EndTurn();
		});
		gameVbox.getChildren().add(skipTurnButton_);
		gameVbox.getChildren().add(playerLabel_);

		stage.show();

	}

	/**
	 * Creates a waiting room
	 */
	public void createWaitingRoom () {

	}

	/**
	 * Activates the gui and allows the player to roll.
	 */
	public void startPlayerRoll () {
		// Turns on die rolling
		imageView_.setOnMouseClicked(e -> {
			roll_ = roll();
			skipTurnButton_.setDisable(false);
			controller_.setRoll(roll_);
			startPlayerTurn();
			endPlayerRoll();
		});

	}

	/**
	 * De-activates the die roller
	 */
	public void endPlayerRoll () {
		imageView_.setOnMouseClicked(e -> {

		});

	}

	/**
	 * activates the board gui and allows attempted moves
	 */
	public void startPlayerTurn () {
		// Event handler for dragging pieces
		pieceOverlay_.setOnMouseClicked(e -> {
			if ( e.getButton() == MouseButton.PRIMARY ) {

			} else if ( e.getButton() == MouseButton.SECONDARY ) {
				int row = getRow(pieceOverlay_,e.getY());
				int col = getCol(pieceOverlay_,e.getX());
				displayClickedSpace(controller_.getBoard().getPieces(row,col));
			}
		});
		pieceOverlay_.setOnMousePressed(e -> {
			// Starting the drag
			int row = getRow(pieceOverlay_,e.getY());
			int col = getCol(pieceOverlay_,e.getX());
			// start the drag
			startDrag(row,col);
			// update the display
			hideSpace(row,col);// remove piece from display (board unchanged)
			drawPiece(dragged_,pieceOverlay_,e.getX(),e.getY());// draw piece being
			// dragged
		});

		// Event Handler for Dragging the piece
		pieceOverlay_.setOnMouseDragged(e -> {
			clear(pieceOverlay_);
			drawPiece(dragged_,pieceOverlay_,e.getX(),e.getY());

		});

		// Event Handler for Placing Pieces
		pieceOverlay_.setOnMouseReleased(e -> {
			int row = getRow(pieceOverlay_,e.getY());
			int col = getCol(pieceOverlay_,e.getX());
			unhideSquare(startRow_,startCol_);// restore piece to display
			// (board unchanged)
			clear(boardCanvas_);// clear display of piece being dragged
			clear(pieceOverlay_);
			// end the drag - drop the piece being dragged, if any
			dropPiece(row,col,controller_.getPhase());
			drawBoard(root_);
		});
	}

	/**
	 * ends the player turn and deactivates the board gui
	 */
	public void endPlayerTurn () {
		skipTurnButton_.setDisable(true);
		// deactivating the event handlers
		pieceOverlay_.setOnMousePressed(e -> {});

		pieceOverlay_.setOnMouseDragged(e -> {});

		pieceOverlay_.setOnMouseReleased(e -> {});

	}

	/**
	 * Activates GUI for a beginning Player turn
	 */
	public void startBeginningTurn ( int round, int player ) {
		// Event handler for placing Pieces on the start
		playerLabel_
		    .setText("Turn: " + controller_.getCurrPlayer().getStringColor());
		pieceOverlay_.setOnMousePressed(e -> {
			int row = getRow(pieceOverlay_,e.getY());
			int col = getCol(pieceOverlay_,e.getX());
			if ( controller_.isLegalPlacement(row,col) == true ) {
				controller_.AddNewPiece(row,col);
				drawBoard(root_);
				controller_.endStartupTurn(round,player);
			}
		});
	}

	/**
	 * Deactivates the GUI for a beginning Player turn
	 */
	public void EndBeginningTurn () {
		pieceOverlay_.setOnMousePressed(e -> {

		});
	}

	/**
	 * starts a CPU beginner turn
	 */
	public void StartCPUTurn ( int round, int player ) {
		controller_.beginningCPUTurn(round,player);
		drawBoard(root_);
	}

	/**
	 * Begin a drag operation.
	 * 
	 * @param row
	 *          starting row for piece being dragged
	 * @param col
	 *          starting column for piece being dragged
	 */
	private void startDrag ( int row, int col ) {
		dragged_ = board_.getPiece(row,col);
		if ( dragged_ == null ) {
			return;
		}
		startRow_ = row;
		startCol_ = col;
	}

	/**
	 * End a drag operation.
	 * 
	 * @param row
	 *          row where piece is dropped
	 * @param col
	 *          col where piece is dropped
	 */
	private void dropPiece ( int row, int col, int phase ) {
		// move piece
		controller_.movePiece(startRow_,startCol_,row,col);

		dragged_ = null;
		startRow_ = -1;
		startCol_ = -1;
	}

	/**
	 * Hide a space - draw a blank square on the canvas in the specified position
	 * (board position - row,col).
	 * 
	 * @param row
	 *          row
	 * @param col
	 *          column
	 */
	private void hideSpace ( int row, int col ) {
		if ( controller_.isLegalPosition(row,col) == false ) {
			return;
		}
		GraphicsContext g = boardCanvas_.getGraphicsContext2D();
		Space space = controller_.getSpace(row,col);// The type of space to be unhid
		double spaceHeight = overlay_.getHeight() / board_.getHeight();// The
		// horizontal
		// size of a
		// square
		double spaceWidth = overlay_.getWidth() / board_.getWidth();// The vertical
		// size of a
		// square

		// (x,y) is the upper left corner of the current square
		double x = col * spaceWidth, y = row * spaceHeight;

		if ( space == Space.YELLOW ) {
			g.setFill(space.Color());
			g.fillRect(x,y,spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		} else if ( space == Space.BLACK ) {
			g.setFill(space.Color());
			g.fillRect(x,y,spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		} else if ( space == Space.START ) {
			g.drawImage(new Image("file:images/" + (row + 1) + ".jpg"),0,
			            spaceHeight * row,spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		} else if ( space == Space.END ) {
			g.drawImage(new Image("file:images/" + (row + 1) + ".jpg"),
			            boardCanvas_.getWidth() - spaceWidth,spaceHeight * row,
			            spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		}
	}

	/**
	 * Unhide a square - draw the piece that is in the specified position (board
	 * position - row,col).
	 * 
	 * @param row
	 *          row
	 * @param col
	 *          column
	 */
	private void unhideSquare ( int row, int col ) {
		if ( controller_.isLegalPosition(row,col) == false ) {
			return;
		}
		GraphicsContext g = boardCanvas_.getGraphicsContext2D();
		Space space = controller_.getSpace(row,col);// The type of space to be unhid
		double spaceHeight = getSpaceHeight();
		double spaceWidth = getSpaceWidth();

		g.setStroke(Color.BLACK);
		// (x,y) is the upper left corner of the current square
		double x = col * spaceWidth, y = row * spaceHeight;

		if ( space == Space.YELLOW ) {
			g.setFill(space.Color());
			g.fillRect(x,y,spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		} else if ( space == Space.BLACK ) {
			g.setFill(space.Color());
			g.fillRect(x,y,spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		} else if ( space == Space.START ) {
			g.drawImage(new Image("file:images/" + (row + 1) + ".jpg"),0,
			            spaceHeight * row,spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		} else if ( space == Space.END ) {
			g.drawImage(new Image("file:images/" + (row + 1) + ".jpg"),
			            boardCanvas_.getWidth() - spaceWidth,spaceHeight * row,
			            spaceWidth,spaceHeight);
			g.setFill(Color.BLACK);
			g.strokeRect(x,y,spaceWidth,spaceHeight);
		}

		Piece piece = board_.getPiece(row,col);
		if ( piece != null ) {
			drawPiece(piece,boardCanvas_,((col * spaceWidth) + (spaceWidth / 4)),
			          ((row * spaceHeight) + (spaceHeight / 4)));
		}
	}

	/**
	 * Draw the specified piece on the canvas at the specified position (pixel
	 * coordinates).
	 * 
	 * @param piece
	 *          piece to draw
	 * @param canvas
	 *          drawing canvas
	 * @param x
	 *          x coordinate for center of piece
	 * @param y
	 *          y coordinate for center of piece
	 */
	private void drawPiece ( Piece piece, Canvas canvas, double x, double y ) {
		if ( piece == null ) {
			return;
		}
		double spaceHeight = getSpaceHeight();
		double spaceWidth = getSpaceWidth();
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.setFill(piece.getColor());
		g.fillOval(x,y,spaceWidth / 2,spaceHeight / 2);
	}

	/**
	 * Clear the canvas.
	 * 
	 * @param canvas
	 */
	private void clear ( Canvas canvas ) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
	}

	/**
	 * Handles drawing the spaces on the board with pieces in them As well as
	 * drawing the die
	 * 
	 * @param root_
	 */

	private void drawBoard ( BorderPane root_ ) {

		Position[][] spaces = board_.getSpaces();
		GraphicsContext g = boardCanvas_.getGraphicsContext2D();
		double spaceHeight = getSpaceHeight();
		double spaceWidth = getSpaceWidth();
		g.setFill(Color.BLACK);
		g.fillRect(0,0,boardCanvas_.getWidth(),boardCanvas_.getHeight());

		// Draws the squares of the board
		for ( int row = 0 ; row < board_.getHeight() ; row++ ) {
			for ( int col = 0 ; col < board_.getWidth() ; col++ ) {
				Space space = spaces[row][col].getSpace();
				if ( space == Space.START ) {
					g.drawImage(new Image("file:images/" + (row + 1) + ".jpg"),0,
					            spaceHeight * row,spaceWidth,spaceHeight);
				} else if ( space == Space.END ) {
					g.drawImage(new Image("file:images/" + (row + 1) + ".jpg"),
					            boardCanvas_.getWidth() - spaceWidth,spaceHeight * row,
					            spaceWidth,spaceHeight);
				} else if ( space == Space.YELLOW ) {
					g.setFill(space.Color());
					g.fillRect(col * spaceWidth,row * spaceHeight,spaceWidth,spaceHeight);
					g.setFill(Color.BLACK);
					g.strokeRect(col * spaceWidth,row * spaceHeight,spaceWidth,
					             spaceHeight);
				}
			}
		}

		// Draws the pieces onto the board
		for ( int row = 0 ; row < board_.getHeight() ; row++ ) {
			for ( int col = 0 ; col < board_.getWidth() ; col++ ) {
				Piece temp = board_.getPiece(row,col);
				if ( temp != null ) {
					drawPiece(temp,boardCanvas_,((col * spaceWidth) + (spaceWidth / 4)),
					          ((row * spaceHeight) + (spaceHeight / 4)));
				}
			}
		}

		HBox diceHolder = new HBox();
		BorderPane.setMargin(diceHolder,new Insets(10,10,10,10));
		diceHolder.setSpacing(10);

		diceHolder.getChildren().add(imageView_);
		diceHolder.setAlignment(Pos.BOTTOM_CENTER);
		root_.setBottom(diceHolder);

	}

	/**
	 * Closes the program at the end of a game
	 */
	public void exitGame ( Player player ) {

		Alert alert = new Alert(Alert.AlertType.INFORMATION,
		                        (player.getStringColor() + " " + "Won the game!"));
		Optional<ButtonType> response = alert.showAndWait();
		Platform.exit();
	}

	/**
	 * Get the row corresponding to the y coordinate in the drawing canvas.
	 * 
	 * @param canvas
	 *          drawing canvas
	 * @param y
	 *          y coordinate
	 * @return the row corresponding to the y coordinate
	 */
	private int getRow ( Canvas canvas, double y ) {
		return (int) (y * board_.getHeight() / canvas.getHeight());
	}

	/**
	 * Get the column corresponding to the x coordinate in the drawing canvas.
	 * 
	 * @param canvas
	 *          drawing canvas
	 * @param x
	 *          x coordinate
	 * @return the column corresponding to the x coordinate
	 */
	private int getCol ( Canvas canvas, double x ) {
		return (int) (x * board_.getWidth() / canvas.getWidth());
	}

	/**
	 * Display an error message.
	 * 
	 * @param msg
	 *          error message to display
	 */
	public void displayError ( String msg ) {
		Alert alert = new Alert(Alert.AlertType.ERROR,msg);
		alert.showAndWait();
	}

	public void displayClickedSpace ( Stack<Piece> stack ) {
		Stack<Piece> tempStack = new Stack<Piece>();
		Piece tempPiece = null;
		String pieceStackOrder = "";
		int stackSize = stack.size();
		for ( int j = 0 ; j < stackSize ; j++ ) {
			tempPiece = stack.pop();
			System.out.println(tempPiece + " Removed from stack");
			pieceStackOrder +=
			    tempPiece.getStringColor() + " piece at " + j + " spot, ";
			tempStack.push(tempPiece);
		}
		for ( int i = 0 ; i < stackSize ; i++ ) {
			stack.push(tempStack.pop());
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION,pieceStackOrder);
		alert.setTitle("Piece Spot in Stack");
		alert.showAndWait();
	}

	private int roll () {

		int pos = (int) ((Math.random() * 6));
		imageView_.setImage(images_[pos]);
		return pos + 1;

	}

	private void join () {

		// Dialog config = new Dialog();
		// DialogPane dialogPane;

	}

	private void exit () {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
		                        ("Are you sure you want to quit?"));
		Optional<ButtonType> response = alert.showAndWait();
		if ( response.isPresent() && response.get() == ButtonType.OK ) {
			Platform.exit();
		}
	}

	private double getSpaceHeight () {
		return overlay_.getHeight() / board_.getHeight();
	}

	private double getSpaceWidth () {
		return overlay_.getWidth() / board_.getWidth();
	}

	private void initRules () {
		rules_ = new HashMap<String,GameRules>();
		rules_.put("Urehedgehog mode",new Urhedgehog());
		rules_.put("Anarchohedgehog mode",new Anarchohedgehog());
		rules_.put("Backwards mode",new Backwards());
		rules_.put("Concrete Block mode",new ConcreteBlock());
		rules_.put("Criss-Cross mode",new CrissCross());
		rules_.put("Fizzy Lifting mode",new FizzyLifting());
		rules_.put("Default mode",new Default());

		colors_ = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
		                        Color.PINK, Color.PURPLE };

	}

}
