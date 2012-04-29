package life;

import java.util.HashSet;
import java.util.Set;

public class GameOfLife {

  private boolean[][] grid;
  private int columns;
  private int rows;
  
  public GameOfLife(int numColumns, int numRows) {
    this.columns = numColumns;
    this.rows = numRows;
    grid = new boolean[numColumns][];
    for(int i = 0; i < numColumns; ++i)
      grid[i] = new boolean[numRows];
  }
  
  public void setLive(int col, int row) {
    grid[col][row] = true;
  }
  
  public void step() {
    Set<int[]> nextLivingCells = new HashSet<int[]>();
    for (int c = 0; c < grid.length; ++c) {
      for (int r = 0; r < grid[c].length; ++r) {
        if (step(c, r)) 
          nextLivingCells.add(new int[] { c, r });
      }
    }
    
    for (int c = 0; c < columns; ++c) {
      for (int r = 0; r < rows; ++r) {
        grid[c][r] = false;
      }
    }
    
    for (int[] cell : nextLivingCells) {
      grid[cell[0]][cell[1]] = true;
    }
  }
   
  public boolean step(int col, int row) {
    int n = 0;
    for (int i = col - 1; i <= col + 1; ++i) {
      for (int j = row - 1; j <= row + 1; ++j) {
        if (i == col && j == row)
          continue;
        
        if (isLive(i, j)) 
          n += 1;
      }
    }
    boolean isLive = (col >= 0 && col < columns && row >= 0 && row < rows) 
      ? grid[col][row]
      : false;
      
    if (isLive)
      return n == 2 || n == 3;
    else
      return n == 3;
  }

  private boolean isLive(int col, int row) {
    return (col >= 0 && col < columns && row >= 0 && row < rows) 
      ? grid[col][row]
      : false;
  }
  
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for(int r = 0; r < rows; ++r) {
      for (int c = 0; c < columns; ++c) {
        result.append(isLive(c, r) ? "X" : ".");
      }
      result.append("\n");
    }
    
    return result.toString();
  }
}
