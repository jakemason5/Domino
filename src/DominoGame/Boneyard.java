//****************************************************************//
//  Jacob Mason                                                   //
//                                                                //
//   This class is used to initialize the boneyard and to handle  //
//   the code for shuffling the boneyard and dealing cards out.   //
//****************************************************************//
package DominoGame;

public class Boneyard
{

  protected Tile []boneyardTiles = new Tile[28];//Initializing the boneyard

  int currIndex;//Current index in the boneyard

  //Initializing the values in the boneyard
  public Boneyard()
  {
    int count = 0;

    for(int leftVal = Tile.minValue; leftVal <= Tile.maxValue; leftVal++)
    {
      for(int rightVal = leftVal; rightVal <= Tile.maxValue; rightVal++)
      {
        boneyardTiles[count++] = new Tile(leftVal, rightVal);
      }
    }


    currIndex = 0;
  }

  //Shuffling the values in the boneyard before dealing
  public void shuffle()
  {
    for(int i = 0; i < boneyardTiles.length; i++)
    {
      Tile temp = boneyardTiles[i];
      int index2Swap = (int)(Math.random()*27);
      boneyardTiles[i] = boneyardTiles[index2Swap];
      boneyardTiles[index2Swap] = temp;
      currIndex = 0;
    }

  }


  //Draws a tile out of the boneyard for use by player or board
  public Tile drawTile()
  {
    if(currIndex < boneyardTiles.length)
    {
      return boneyardTiles[currIndex++];
    }
    else
    {
      return null;
    }
  }
}
