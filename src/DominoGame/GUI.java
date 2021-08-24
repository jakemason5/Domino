package DominoGame;

//****************************************************************//
//  Jacob Mason                                                   //
//                                                                //
//   This class is used to initialize User Interface and deal     //
//   with all of the different actions that the buttons can cause //
//   and handling the ending.                                     //
//****************************************************************//

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.util.*;


public class GUI extends Application implements EventHandler<ActionEvent>
{

  private int playerTilePlacement = -1;//Used to determine if player is placing left or right
  private int playerHandChoice = -1;//Used to determine which tile in their hand the player is placing
  private boolean successfullMove = false;//Used to determine if the attempted move was successful

  private Domino game;//Creating new instance of Domino game

  private ArrayList <RadioButton> playerHand;//Array to hold player hand buttons for placement

  private Label board;//Used to display the board
  private RadioButton placeLeft = new RadioButton("Place Left");//Button for placing on left
  private RadioButton placeRight = new RadioButton("Place Right");//Button for placing on right
  private Button placeTile = new Button("Place Tile");//Button to place tile
  private Button endGame = new Button("End Game");//Button for when player cannot place a tile
  private Button startNewGame = new Button("Start New Game");//Button to start new game(Under Construction)
  private Button buttonDraw = new Button("Draw Tile");//Button used to draw a new tile
  private Label turn = new Label("It is your turn");//Initialized telling the player to go
  private Label errorWarning = new Label("I could not get the new game button to work, please just close out and " +
          "restart the game");//Used to explain new game button isn't working

  ToggleGroup playerHandGroup;//RadioButton toggle group for buttons to determine which tile to place
  FlowPane paneHand = new FlowPane(Orientation.HORIZONTAL);//Used to hold the toggle group above

  ToggleGroup group = new ToggleGroup();//Toggle group for placing right or left


  //Handles building the UI
  @Override
  public void start(Stage primaryStage) throws Exception
  {
    //Initializes start of game
    game = new Domino();
    game.initGame();
    game.beginGame();

    //Pane to hold the board.
    Pane paneBoard = new FlowPane(Orientation.HORIZONTAL);
    paneBoard.getChildren().add(board = new Label(game.BoardString()));

    //Holding the hand of the player
    playerHandGroup = new ToggleGroup();
    playerHand = new ArrayList<RadioButton>();
    for (int i= 0; i < game.player.array.size(); i++)
    {
      RadioButton anotherButton= new RadioButton();
      playerHand.add(anotherButton);
      paneHand.getChildren().add(playerHand.get(i));
      playerHand.get(i).setText(game.player.getArrayElement(i).toString());
      playerHand.get(i).setToggleGroup(playerHandGroup);
      playerHand.get(i).setOnAction(this);//addActionListener(this);
    }


    //Used ot house the buttons
    FlowPane paneButtons = new FlowPane(Orientation.HORIZONTAL);

    //Adding buttons and initializing their actions
    paneButtons.getChildren().addAll(placeLeft, placeRight, placeTile, buttonDraw, startNewGame, endGame);
    placeLeft.setOnAction(this);
    placeLeft.setToggleGroup(group);
    placeRight.setOnAction(this);
    placeRight.setToggleGroup(group);
    buttonDraw.setOnAction(this);
    endGame.setOnAction(this);
    startNewGame.setOnAction(this);
    startNewGame.setVisible(false);
    placeTile.setOnAction(this);

    errorWarning.setVisible(false);

    //Lets player know about turn and who wins
    HBox paneInformation = new HBox();
    paneInformation.getChildren().add(turn);

    //VBox to display all of the panes together.
    VBox display = new VBox();
    display.setSpacing(15);
    display.getChildren().add(paneBoard);
    display.getChildren().add(paneHand);
    display.getChildren().add(paneButtons);
    display.getChildren().add(paneInformation);
    display.getChildren().add(errorWarning);

    //Creating a scene
    Scene s = new Scene(display, 500, 250, Color.WHITE);

    //Setting the stage
    primaryStage.setScene(s);
    primaryStage.setTitle("Domino Game");
    primaryStage.show();

  }

  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();

    //End Game Button Pressed
    if(source == endGame)
    {
      game.win = 0;
      turn.setText(game.dispResults());
      startNewGame.setVisible(true);
      endGame.setVisible(false);
      placeTile.setVisible(false);
      buttonDraw.setVisible(false);
    }


    //Testing each of the buttons for the players hand
    for (int i=0; i< game.player.tileCount(); i++)
    {
      if (source == playerHand.get(i))
      {
        playerHandChoice=i;
      }
    }

    //Place Left button selected
    if(source==placeLeft)
    {
      playerTilePlacement=1; //1 is left (beg)
    }

    //Place Right button selected
    if (source==placeRight)
    {
      playerTilePlacement=2; //2 is right (end)
    }

    //Place tile button pressed
    if (source==placeTile)
    {

      //Placed on left
      if (playerTilePlacement==1)
      {
        successfullMove = game.beginTile(game.player.getArrayElement(playerHandChoice));
      }
      //Placed on the right
      else
      {
        successfullMove = game.endTile(game.player.getArrayElement(playerHandChoice));
      }

      //Move is allowed
      if(successfullMove)
      {
        game.player.removeTile(playerHandChoice);

        Tile firstTile = game.board.get(0);
        Tile lastTile = game.board.get(game.board.size()-1);

        playerHand.get(playerHandChoice).setVisible(false);
        playerHand.remove(playerHandChoice);
        board.setText(game.BoardString());

        //If the player runs out of tiles
        if(game.player.tileCount() == 0)
        {
          game.win = 1;
          turn.setText(game.dispResults());
          startNewGame.setVisible(true);
          endGame.setVisible(false);
          placeTile.setVisible(false);
          buttonDraw.setVisible(false);
        }


        //If the player still has tiles the computer moves.
        else if (game.playComputer())
        {
          board.setText(game.BoardString());
          turn.setText("Computer placed a tile.");
        }
        else
        {
          turn.setText(game.dispResults());
          startNewGame.setVisible(true);
          endGame.setVisible(false);
          placeTile.setVisible(false);
          buttonDraw.setVisible(false);
        }

      }
    }

    //Start New Game Button Pressed
    if (source==startNewGame)
    {
      errorWarning.setVisible(true);
    }

    //Draw a tile button pressed
    if(source == buttonDraw)
    {
      game.drawTile();
      paneHand.getChildren().clear();
      for (int i= 0; i < game.player.array.size(); i++)
      {
        RadioButton anotherButton= new RadioButton();
        playerHand.add(anotherButton);
        paneHand.getChildren().add(playerHand.get(i));
        playerHand.get(i).setText(game.player.getArrayElement(i).toString());
        playerHand.get(i).setToggleGroup(playerHandGroup);
        playerHand.get(i).setOnAction(this);//addActionListener(this);*/
      }
    }
  }
}