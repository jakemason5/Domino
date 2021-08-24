package DominoGame;

//****************************************************************//
//  Jacob Mason                                                   //
//                                                                //
//   This class is used to initialize the tiles and do any        //
//   work with them that is necessary for use in other classes.   //
//   Such as fliping the tile                                     //
//****************************************************************//



public class Tile
{

  public static final int minValue = 0;//Smallest value on a tile
  public static final int maxValue = 6;//Largest value on a tile

  private int leftVal = minValue;
  private int rightVal = minValue;

  //Creates the tiles
  public Tile(int left, int right)
  {
    if(left >= minValue && left <= maxValue)
    {
      leftVal = left;
    }

    if(right >= minValue && right <= maxValue)
    {
      rightVal = right;
    }
  }


  //Flips the tile around for placement
  public void flip()
  {
    int temp;
    temp = rightVal;
    rightVal = leftVal;
    leftVal = temp;
  }

  //Gives left and right values of the selected tile
  public int getLeftVal(){return leftVal;}
  public int getRightVal(){return rightVal;}

  //Creates a string for
  public String toString()
  {
    String leftStr;
    String rightStr;

    if(leftVal >= 1 && leftVal <= maxValue)
    {
      leftStr = "[" + leftVal;
    }
    else
    {
      leftStr = "[0";
    }

    if(rightVal >= 1 && rightVal <= maxValue)
    {
      rightStr = rightVal + "]";
    }
    else
    {
      rightStr = "0]";
    }

    return leftStr + "|" + rightStr;
  }
}
