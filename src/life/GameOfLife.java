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
    for (int c = 0; c < columns; ++c) {
      for (int r = 0; r < rows; ++r) {
        int numLivingNeighbors = 0;
        for (int i = c - 1; i <= c + 1; ++i) {
          for (int j = r - 1; j <= r + 1; ++j) {
            if (i == c && j == r)
              continue;
            
            if ((i >= 0 && i < columns && j >= 0 && j < rows) 
                  ? grid[i][j]
                  : false) 
              numLivingNeighbors += 1;
          }
        }
                  
        if (numLivingNeighbors == 3) {
          nextLivingCells.add(new int[] { c, r });          
        } else {
          boolean isLiveAtCurrentStage = 
            (c >= 0 && c < columns && r >= 0 && r < rows) 
            ? grid[c][r]
            : false;
          if (isLiveAtCurrentStage && numLivingNeighbors == 2) 
            nextLivingCells.add(new int[] { c, r });
        }
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
