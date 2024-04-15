
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Group;

import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartPage extends Application{
	
	private static final int NUMROWS = 10, NUMCOLS = 15;
	// pixel dimensions for grid cells in the display
	private static final int CELLDIM = 50;
	private Canvas canvas_; // drawing canvas
	private Simulation sim_ ;
	private Group k;

	private ImageView imageView;
	private World world ; 
	private Image image ; 



	private int windSpeed = 20;
	private int tempF = 102;
	private int tempC = 39;
	
	public static void main (String[] args) {
		launch(args); 
	}

	@Override
	public void start(Stage stage) throws Exception {
		

		 


		stage.setTitle("Fire!");

		BorderPane root = new BorderPane(); 




		root.setStyle("-fx-background-color: WHEAT");

		root.setPadding(new Insets(40,40,40,40));
		
		//file menu
		Menu file = new Menu("File");
    MenuItem newFile = new MenuItem("New");
    MenuItem saveFile = new MenuItem("Save");
    Menu loadFile = new Menu("Load");

    file.getItems().add(newFile);
    file.getItems().add(loadFile);
    file.getItems().add(saveFile);
    
    //file menubar 
    MenuBar fileMb = new MenuBar();
    fileMb.getMenus().add(file);
    
    HBox fileBox = new HBox();
		fileBox.getChildren().addAll(fileMb);
    
	
		
		//menu
    Menu view = new Menu("View");
    MenuItem stat = new MenuItem("Statistics");

    

    //view menubar
    view.getItems().add(stat);
    MenuBar viewMb = new MenuBar();
		viewMb.getMenus().add(view);
		
    //edit menubar
    Menu edit = new Menu("Edit");
    MenuItem lanEditor = new MenuItem("Initiate Landscape Editor");
    MenuItem statEditor = new MenuItem("Edit Statistics");
    edit.getItems().addAll(lanEditor, statEditor);
    MenuBar editMb = new MenuBar();
    editMb.getMenus().add(edit);
    
    //simulation menu
    Menu simMenu = new Menu("Simulation");
    MenuBar simMb = new MenuBar();
    
    //menu box
		HBox menuBox = new HBox();
		menuBox.getChildren().addAll(fileMb, editMb, viewMb, simMb);
		
		//menubox position
		menuBox.setAlignment(Pos.TOP_CENTER);
		root.setTop(menuBox);
    
    //simulation menu
      MenuItem pause = new MenuItem("Pause Simulation");
      
      //pause menu
      pause.setOnAction(p -> {
        Menu pauseMenu = new Menu("Pause Simulation");
      	MenuItem setPar = new MenuItem("Set Simulation Parameters");
        HBox pauseBox = new HBox();

      //parameters menu	
      	setPar.setOnAction(k -> {
      		Label rowLabel = new Label("Width");
      		TextField userRow = new TextField();
      		Label colLabel = new Label("Height");
      		TextField userCol = new TextField();
      		Button done = new Button("Done");
      		
      		//done menu
      		done.setOnAction(q -> {
      			String userStringRow = userRow.getText();
      			String userStringCol = userCol.getText();
      			int rows =  Integer.parseInt(userStringRow);
      			int cols =  Integer.parseInt(userStringCol);	
      			//need to change this
      			world.setNumCols(cols);
      			world.setNumRows(rows);
      			root.setTop(pauseBox);
      			drawWorld(sim_, canvas_);
      		});//done menu end
      		
      		Button back = new Button("Back");
      		//back button action
      		back.setOnAction(b -> {
      			root.setTop(pauseBox);
      			drawWorld(sim_, canvas_);
      		});
      		
      		//adding parBox
      		HBox parBox = new HBox();
      		parBox.getChildren().addAll(rowLabel, userRow, colLabel, userCol, done, back);
      		parBox.setAlignment(Pos.TOP_CENTER);
      		root.setTop(parBox);
      		
      	});	//end of parameters menu
      	
      	//simulation menu continued
        MenuItem speed = new MenuItem("Step Simulation");
        MenuItem step = new MenuItem("Set Simulation to Earlier Time Step");
        
        //step slider
        step.setOnAction(o ->{
        		Label stepLabel = new Label("Earlier Time Step");
        		Slider slider = new Slider(0, 1, 2);
        		slider.setMajorTickUnit(1);
        		slider.setMax(5);
        		slider.setShowTickMarks(true);
        		slider.setShowTickLabels(true);
        		Button cancel = new Button("Cancel");
        		Button done = new Button("Done");
        		
        		//done action
        		done.setOnAction(j -> {
        			root.setTop(pauseBox);
        			drawWorld(sim_, canvas_);
        		});
        		HBox stepBox = new HBox();
        		stepBox.getChildren().addAll(stepLabel, slider, done, cancel);
        		root.setTop(stepBox);
        		
        		//cancel button action
        		cancel.setOnAction(t ->{
        			root.setTop(pauseBox);
        			drawWorld(sim_, canvas_);
        		});
        
        });//step slider end
        
        //pause continued
        MenuItem unPause = new MenuItem("Unpause Simulation");
        //unpause action
        unPause.setOnAction(u ->{
        	root.setTop(menuBox);
        	drawWorld(sim_, canvas_);
        });
        
        //pause continue
        MenuBar pauseBar = new MenuBar();
        pauseMenu.getItems().add(setPar);
        pauseMenu.getItems().add(speed);
        pauseMenu.getItems().add(step);
        pauseMenu.getItems().add(unPause);
        pauseBar.getMenus().add(pauseMenu);
        //HBox pauseBox = new HBox();
    		pauseBox.getChildren().addAll(pauseBar);
    		
    		//adding pause box to stage
    		pauseBox.setAlignment(Pos.TOP_LEFT);
    		root.setTop(pauseBox);
      });//end of pause menu
     
      //sim menu continued
      MenuItem speed = new MenuItem("Adjust Simulation Speed");
      MenuItem stop = new MenuItem("Stop Simulation");
     
      //speed action
      speed.setOnAction(r -> {
      	Label speedLabel = new Label("Adjust Simulation Speed");
      	Slider speedSlider = new Slider();
      	speedSlider.setMajorTickUnit(1);
      	speedSlider.setMax(5);
      	speedSlider.setShowTickMarks(true);
      	speedSlider.setShowTickLabels(true);
      	Button cancel = new Button("Cancel");
    		Button done = new Button("Done");
    		
    		//done action
    		done.setOnAction(j -> {
    			root.setTop(menuBox);
    			drawWorld(sim_, canvas_);
    		});
    		HBox stepBox = new HBox();
    		stepBox.getChildren().addAll(speedLabel, speedSlider, done, cancel);
    		root.setTop(stepBox);
    		
    		//cancel button action
    		cancel.setOnAction(t ->{
    			root.setTop(menuBox);
    			drawWorld(sim_, canvas_);
    		});
      });
      //stop action
      stop.setOnAction(l ->{
      	System.exit(0);
      });
      
      //adding simMenu items
      simMenu.getItems().add(pause);
      simMenu.getItems().add(speed);
      simMenu.getItems().add(stop);
      simMb.getMenus().add(simMenu);
  		
  		
  		
   +
    
   
    	//lan editor menu

    lanEditor.setOnAction(r ->{
    	Menu editMenu = new Menu("Edit Simulation");
    	HBox editBox = new HBox();
    	Button back = new Button("Back");
    	Button fire = new Button("Place Fire");
    	Button tree = new Button("Place Tree");
    	Button empty = new Button("Place Empty");
    	Button rock = new Button("Place Rock");
    	fire.setOnAction(f ->{
    		//add fire code here
    		HBox treeBox = new HBox();
    		treeBox.getChildren().add(back);
    		root.setTop(treeBox);
    		canvas_.setOnMousePressed(e -> {

    			int x = (int) e.getX() / 50;
    			int y = (int) e.getY() / 50;


    			
    			 image = null;
    			try {
    				image = new Image(new FileInputStream("red.jpeg"));
    			} catch ( FileNotFoundException e1 ) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}  
    			
    			double width = canvas_.getWidth(); 
    			double height = canvas_.getHeight();
    	      
    	      //Setting the image view 
    	      imageView = new ImageView(image); 
    	      
    	      //Setting the position of the image 
    	      imageView.setX((x * (CELLDIM))+ 40); 
    	      imageView.setY((y* (CELLDIM))+67); 
    	      //setting the fit height and width of the image view 
    	      imageView.setFitHeight( width/ world.getNumCol()); 
    	      imageView.setFitWidth(height/world.getNumRows());
    	      
    	      //Setting the preserve ratio of the image view 
    	      imageView.setPreserveRatio(false);  
    	      
    	      //Creating a Group object  
    	      k = new Group(imageView); 
    	      
    	     root.getChildren().addAll(k);

    		});
    		
    	
    	drawWorld(sim_, canvas_);	
    	

    	});
    	
    	
    	tree.setOnAction(t -> {
    		//add tree code here
    		HBox treeBox = new HBox();
    		treeBox.getChildren().add(back);
    		canvas_.setOnMousePressed(e -> {

    			int x = (int) e.getX() / 50;
    			int y = (int) e.getY() / 50;

    			System.out.println(x);
    			System.out.println(y);
    			
    			 image = null;
    			try {
    				image = new Image(new FileInputStream("green.jpeg"));
    			} catch ( FileNotFoundException e1 ) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}  
    			
    			double width = canvas_.getWidth(); 
    			double height = canvas_.getHeight();
    	      
    	      //Setting the image view 
    	      imageView = new ImageView(image); 
    	      
    	      //Setting the position of the image 
    	      imageView.setX((x * (CELLDIM))+ 40); 
    	      imageView.setY((y* (CELLDIM))+67); 
    	      //setting the fit height and width of the image view 
    	      imageView.setFitHeight( width/ world.getNumCol()); 
    	      imageView.setFitWidth(height/world.getNumRows());
    	      
    	      //Setting the preserve ratio of the image view 
    	      imageView.setPreserveRatio(false);  
    	      
    	      //Creating a Group object  
    	      k = new Group(imageView); 
    	      
    	     root.getChildren().addAll(k);

    		});
    		root.setTop(treeBox);
    		
    		
    		
    		drawWorld(sim_, canvas_);
    	});
    	empty.setOnAction(m -> {
    		//add empyt code here
    		HBox treeBox = new HBox();
    		treeBox.getChildren().add(back);
    		canvas_.setOnMousePressed(e -> {

    			int x = (int) e.getX() / 50;
    			int y = (int) e.getY() / 50;

    			
    			 image = null;
    			try {
    				image = new Image(new FileInputStream("yellow.jpeg"));
    			} catch ( FileNotFoundException e1 ) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}  
    			
    			double width = canvas_.getWidth(); 
    			double height = canvas_.getHeight();
    	      
    	      //Setting the image view 
    	      imageView = new ImageView(image); 
    	      
    	      //Setting the position of the image 
    	      imageView.setX((x * (CELLDIM))+ 40); 
    	      imageView.setY((y* (CELLDIM))+67); 
    	      //setting the fit height and width of the image view 
    	      imageView.setFitHeight( width/ world.getNumCol()); 
    	      imageView.setFitWidth(height/world.getNumRows());
    	      
    	      //Setting the preserve ratio of the image view 
    	      imageView.setPreserveRatio(false);  
    	      
    	      //Creating a Group object  
    	      k = new Group(imageView); 
    	      
    	     root.getChildren().addAll(k);			

    		});
    		
    		root.setTop(treeBox);
    		drawWorld(sim_, canvas_);
    	});
    	rock.setOnAction(p -> {
    		HBox treeBox = new HBox();
    		treeBox.getChildren().add(back);
    		
    		canvas_.setOnMousePressed(e -> {

    			int x = (int) e.getX() / 50;
    			int y = (int) e.getY() / 50;

    			
    			 image = null;
    			try {
    				image = new Image(new FileInputStream("grey.jpeg"));
    			} catch ( FileNotFoundException e1 ) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}  
    			
    			double width = canvas_.getWidth(); 
    			double height = canvas_.getHeight();
    	      
    	      //Setting the image view 
    	      imageView = new ImageView(image); 
    	      
    	      //Setting the position of the image 
    	      imageView.setX((x * (CELLDIM))+ 40); 
    	      imageView.setY((y* (CELLDIM))+67); 
    	      //setting the fit height and width of the image view 
    	      imageView.setFitHeight( width/ world.getNumCol()); 
    	      imageView.setFitWidth(height/world.getNumRows());
    	      
    	      //Setting the preserve ratio of the image view 
    	      imageView.setPreserveRatio(false);  
    	      
    	      //Creating a Group object  
    	      k = new Group(imageView); 
    	      
    	     root.getChildren().addAll(k);
    	    
    	     

    			

    		});
    		
    		
    		root.setTop(treeBox);
    		drawWorld(sim_, canvas_);
    	});
    	back.setOnAction(d -> {
    	
    		image = null; 
 	     imageView=null; 
 	     canvas_.setOnMousePressed(null);
    		
    	
    		root.setTop(editBox);
    		drawWorld(sim_, canvas_);
    	});
    	Button back1 = new Button("Back");
    	back1.setOnAction(d -> {
    		//canvas_.setOnMouseClicked(null);
    		root.setTop(menuBox);
    		drawWorld(sim_, canvas_);
    	});
    	MenuBar lanEditorMb = new MenuBar();
    	lanEditorMb.getMenus().addAll(editMenu);
    	//adding editbox to stage
    	editBox.getChildren().addAll(lanEditorMb, fire, tree, empty, rock,  back1);
    	root.setTop(editBox);
    	drawWorld(sim_, canvas_);
    });//edit menu end
    
    //File new
    newFile.setOnAction(e -> {
    	root.setTop(menuBox);
    	drawWorld(sim_, canvas_);
    	System.out.println("New File");
    	});
    
    //File load
		loadFile.setOnAction(i -> {
			world.previousWorld(NUMCOLS,NUMROWS);
			System.out.println("Loading file");
		});
		
		stat.setOnAction(g -> {
			Label statLabel = new Label("Statistics");
			Label windLabel = new Label("Wind Speed: " + windSpeed);
			
			int temperatureF = 102;
			int temperatureC = 39;
			Label temFLabel = new Label("Temperature in Fahrenheit: " + temperatureF);
			Label temCLabel = new Label("Temperature in Celsius: " + temperatureC);
			Label area = new Label("Area: " + world.getNumCol() + " by " + world.getNumRows() );
			Label space1 = new Label("      ");
			Label space2 = new Label("      ");
			Label space3 = new Label("      ");
			
			Button back = new Button("Back");
			back.setOnAction(o -> {
				root.setTop(menuBox);
				drawWorld(sim_, canvas_);
			});
			
			//world = sim_.getWorld(); 
			HBox statBox = new HBox();
			statBox.getChildren().addAll(windLabel, space1, temFLabel, space2, temCLabel, space3, area, back);
			root.setTop(statBox);
			drawWorld(sim_, canvas_);
		});
		

		statEditor.setOnAction(i ->{
			Button changeWind = new Button("Change wind Speed");
			Button changeTempF = new Button("Change Temperature (F)");
			Button changeTempC = new Button("Change Temperature (C)");
			Label spacer1 = new Label("     ");
			Label spacer2 = new Label("     ");
			Label spacer3 = new Label("     ");
			Label spacer4 = new Label("     ");
			HBox statEditorBox = new HBox();

			Button back1 = new Button("Back");
			back1.setOnAction(o -> {
				root.setTop(menuBox);
				drawWorld(sim_, canvas_);
			});
			changeWind.setOnAction(w -> {
				Label windLabel= new Label("Wind Speed: ");
				TextField windText = new TextField();
				Button done = new Button("Done");
				Button back = new Button("Back");
				HBox windBox = new HBox();
				windBox.getChildren().addAll(windLabel, windText, done, back);
				windBox.setAlignment(Pos.TOP_CENTER);
				root.setTop(windBox);
				drawWorld(sim_, canvas_);
				done.setOnAction(y -> {
					int newwindSpeed = Integer.parseInt(windText.getText());
					windSpeed =  newwindSpeed;
					System.out.println("Wind Speed: " + newwindSpeed);
					root.setTop(statEditorBox);
					drawWorld(sim_, canvas_);
				});
				
				back.setOnAction(o -> {
					root.setTop(statEditorBox);
					drawWorld(sim_, canvas_);
				});
			});
			changeTempF.setOnAction(f -> {
				Label tempFLabel = new Label("Temperature (F): ");
				TextField tempText = new TextField();
				Button done = new Button("Done");
				Button back = new Button("Back");
				HBox tempCBox = new HBox();
				tempCBox.getChildren().addAll(tempFLabel, tempText, done);
				tempCBox.setAlignment(Pos.TOP_CENTER);
				root.setTop(tempCBox);
				drawWorld(sim_, canvas_);
				done.setOnAction(y -> {
					int newtempF = Integer.parseInt(tempText.getText());
					tempF =  newtempF;
					System.out.println("Temperature (F) : " + tempF);
					tempC = ((tempF - 32) *(5/9));
					System.out.println("Temperature (C) : " + tempC);
					root.setTop(statEditorBox);
					drawWorld(sim_, canvas_);
				});
				back.setOnAction(o -> {
					root.setTop(statEditorBox);
					drawWorld(sim_, canvas_);
				});
			});
			changeTempC.setOnAction(f -> {
				Label tempCLabel = new Label("Temperature (C): ");
				TextField tempText = new TextField();
				Button done = new Button("Done");
				Button back = new Button("Back");
				HBox tempCBox = new HBox();
				tempCBox.getChildren().addAll(tempCLabel, tempText, done);
				tempCBox.setAlignment(Pos.TOP_CENTER);
				root.setTop(tempCBox);
				drawWorld(sim_, canvas_);
				done.setOnAction(y -> {
					int newtempC = Integer.parseInt(tempText.getText());
					tempC =  newtempC;
					System.out.println("Temperature (C) : " + tempC);
					tempF = ((tempC*(9/5)) + 32);
					System.out.println("Temperature (F) : " + tempF);
					root.setTop(statEditorBox);
					drawWorld(sim_, canvas_);
				});
				back.setOnAction(o -> {
					root.setTop(statEditorBox);
					drawWorld(sim_, canvas_);
				});
			});
			statEditorBox.getChildren().addAll(changeWind, spacer1, changeTempF, spacer3, changeTempC, spacer4, back1);
			statEditorBox.setAlignment(Pos.TOP_LEFT);
			root.setTop(statEditorBox);
			drawWorld(sim_, canvas_);
			});

	


		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		
		
	
		
    canvas_ = new Canvas(NUMCOLS * CELLDIM,NUMROWS * CELLDIM);
    root.setCenter(canvas_);
    
		
		sim_ = new Simulation(NUMROWS,NUMCOLS);
		
		drawWorld(sim_,canvas_); 
		
		
		stage.show();
		// TODO Auto-generated method stub
		
	}
	
	public void drawWorld(Simulation sim,Canvas canvas ) {
		GraphicsContext g = canvas.getGraphicsContext2D(); 
		double width = canvas.getWidth(); 
		double height = canvas.getHeight();
		
	 world = sim.getWorld(); 
		g.clearRect(0, 0, width, height);
		
		g.setStroke(Color.SIENNA);
		for(int row = 0 ; row <= world.getNumRows();row++) {
			g.strokeLine(0, row * height/world.getNumRows(), width, 
							row * height /world.getNumRows());
		}
		
		for(int col = 0 ; col <= world.getNumCol();col++) {
			g.strokeLine(col * width / world.getNumCol(), 0, col * width / world.getNumCol(), 
							height);
		}
		
		for (int row = 0; row<= world.getNumRows(); row++) {
			
		}
		
		for (int col = 0; col<= world.getNumCol(); col++) {
			
		}
		
	
	}
	

}
