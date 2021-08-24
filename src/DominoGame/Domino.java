package DominoGame;

//****************************************************************//
//  Jacob Mason                                                   //
//                                                                //
//   This class is used to connect to most of the classes and     //
//   contains most of the methods for drawing and placing tiles   //
//   And handling computer player decisions                       //
//****************************************************************//

import java.util.ArrayList;


public class Domino
{

  public DominoGame.Boneyard set; //Used to access the Boneyard Class
  public ArrayList<Tile> board; //List used to show which tiles are played
  public DominoGame.Player player;//Used for the player
  public DominoGame.Player computer;//Used to initialize the computer
  public int boneyardSize;//Used to measure how many tiles are left in the boneyard
  protected int win;//Used to determine who won


  //Initializing the different aspects of the program
  public Domino()
  {
    set = new Boneyard();
    board = new ArrayList<Tile>();

    player = new DominoGame.Player("Player");

    computer = new DominoGame.Player("Computer");
  }

  //Initializing the boneyard and removing any possible things in the arrays
  public void initGame()
  {
    set.shuffle();
    board.clear();
    //player.clearHand();
    //computer.clearHand();
  }

  //Deals out the tiles to the players and initializes boneyardSize
  public void deal()
  {
    while(true)
    {
      player.dealTile(set.drawTile());
      computer.dealTile(set.drawTile());

      if(player.tileCount() == 7 && computer.tileCount() == 7)
      {
        boneyardSize = 13;
        return;
      }
    }
  }

  //Used for a player to draw a tile
  public void drawTile()
  {
    player.dealTile(set.drawTile());
    if(boneyardSize != 0)
    {
      boneyardSize--;
    }
  }

  //Used for the computer to draw a tile
  public void comDrawTile()
  {
    computer.dealTile(set.drawTile());
    if(boneyardSize != 0)
    {
      boneyardSize--;
    }
      playComputer();
  }

  //Used to determine if tile can be placed at beginning of board
  public boolean beginTile(Tile t)
  {
    if(board.size() == 0)
    {
      board.add(t);
      return true;
    }

    Tile firstTile = board.get(0);

    if(t.getRightVal() == firstTile.getLeftVal())
    {
      board.add(0, t);
      return true;
    }//testing matching

    if(t.getLeftVal() == firstTile.getLeftVal())
    {
      t.flip();
      board.add(0, t);
      return true;
    }//testing matching

    else if(t.getLeftVal() == 0)
    {
      t.flip();
      board.add(0,t);
      return true;
    }//testing if zero

    else if(t.getRightVal() == 0)
    {
      board.add(0,t);
      return true;
    }//testing if zero

    else if(firstTile.getLeftVal() == 0)
    {
      board.add(0,t);
      return true;
    }//testing if zero

    else
    {
      return false;
    }
  }


  //Used to determine if a tile can be placed at the end of the board
  public boolean endTile(Tile t)
  {
    Tile lastTile = board.get(board.size()-1);
    if(t.getLeftVal() == lastTile.getRightVal() || lastTile.getRightVal() == 0)
    {
      board.add(t);
      return true;
    }//testing matching

    else if(t.getRightVal() == lastTile.getRightVal() )
    {
       t.flip();
       board.add(t);
       return true;
    }//testing matching

    else if(t.getLeftVal() == 0)
    {
      board.add(t);
      return true;
    }//testing for zero

    else if(t.getRightVal() == 0)
    {
      t.flip();
      board.add(t);
      return true;
    }//testing for zero

    else if(lastTile.getRightVal() == 0)
    {
      board.add(t);
      return true;
    }//testing for zero\

    else
    {
      return false;
    }//No possibility found
  }

  //Used for the computer to play
  public boolean playComputer()
  {
    Tile firstTile = board.get(0);
    Tile lastTile = board.get(board.size()-1);
    int computerTileChoice = computer.findTile(firstTile.getLeftVal());//Testing the values of the ends

    //Tile matches on left side
    if(computerTileChoice != -1)
    {
      Tile insert = computer.getArrayElement(computerTileChoice);
      beginTile(insert);
      computer.removeTile(computerTileChoice);
    }
    else
    {
      computerTileChoice = computer.findTile(lastTile.getRightVal());//Testing the values of the ends

      //Tile matches on right side
      if(computerTileChoice != -1)
      {
        Tile insert = computer.getArrayElement(computerTileChoice);
        endTile(insert);
        computer.removeTile(computerTileChoice);
      }

      //No matches found, testing to see if computer can draw a tile
      else
      {
        if(boneyardSize != 0)
        {
          comDrawTile();
        }
        else
        {
          return false;
        }//Nothing was found, computer loses
      }
    }
    return true;
  }

  //Initializes at the beginning of the game. Deals out tiles and places down first tile.
  public void beginGame()
  {
    deal();
    beginTile(set.drawTile());
  }

  //String to Display the results in a label
  public String dispResults()
  {
    String displayResults = new String();

    if(win == 1)
    {
      displayResults = displayResults + "You Won!";
    }

    else if(win == 0)
    {
      displayResults = displayResults + "Computer Won!";
    }

    return displayResults;
  }

  //String used to display the board
  public String BoardString()
  {
    String stringBoard = new String();

    for(int i = 0; i < board.size(); i++)
    {
      Tile aTile = board.get(i);
      stringBoard=stringBoard+aTile.toString();
    }
    return stringBoard;
  }
}
