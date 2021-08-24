package DominoGame;
//****************************************************************//
//  Jacob Mason                                                   //
//                                                                //
//   This class is used to initialize the data for the player     //
//   as well as further code used during dealing and finding      //
//   tiles.                                                       //
//****************************************************************//

import java.util.ArrayList;

public class Player
{


  private String name;//Used to differentiate between player and computer
  protected ArrayList<DominoGame.Tile>array = new ArrayList<DominoGame.Tile>(21);//Array to hold players hand


  //Naming the different players
  public Player(String n)
  {
    if(n != null)
    {
      name = n;
    }
    else
    {
      name = "Player";
    }
  }

  //Used to get a tile from the players hand
  public DominoGame.Tile getArrayElement(int index){return array.get(index);}

  //removes a tile from the players hand for placement
  public DominoGame.Tile removeTile(int index)
  {
    if(index >= 0 && index<array.size())
    {
      DominoGame.Tile tempTile = getArrayElement(index);
      array.remove(index);

      return tempTile;
    }
    else
    {
      return null;
    }
  }

  //Used to count how many tiles are in the hand
  public int tileCount()
  {
    return array.size();
  }

  //Deals a tile out to the players
  public boolean dealTile(DominoGame.Tile tile)
  {
    if(tile != null)
    {
      array.add(tile);
      return true;
    }
    else
    {
      return false;
    }
  }

  //Used for computer placement, determining which tile in their hand can be placed
  public int findTile(int value)
  {
    for(int i = 0; i < array.size(); i++)
    {
      DominoGame.Tile tile = getArrayElement(i);

      if(value == 0)
      {
        return 0;
      }
      if(value == tile.getLeftVal() || value == tile.getRightVal())
      {
        return i;
      }
    }


    return -1;
  }

  //Clears hand when starting new game, is not working at this time
  //public void clearHand(){array.clear();}
}

