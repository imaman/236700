package life;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
  
  private class Cell {

    public final int c;
    public final int r;

    public Cell(int c, int r) {
      this.c = c;
      this.r = r;
    }

    public void applyNeighbors(Action action) {
      for (int i = c - 1; i <= c + 1; ++i) {
        for (int j = r - 1; j <= r + 1; ++j) {
          if (i == c && j == r)
            continue;
          
          action.run(new Cell(i, j));          
        }
      }
    }
    
  }
  
  public void step() {
    final Set<Cell> nextLivingCells = new HashSet<Cell>();
    apply(new Action() {
      @Override
      public void run(Cell cell) {
        final List<Cell> livingNeighbors = new ArrayList<Cell>();
        cell.applyNeighbors(new Action() {
          @Override
          public void run(Cell cell) {
            if (isLive(cell))
              livingNeighbors.add(cell);
          }
        });
                  
        int numLivingNeighbors = livingNeighbors.size();
        if (numLivingNeighbors == 3) {
          nextLivingCells.add(cell);          
        } else {
          if (isLive(cell) && numLivingNeighbors == 2) 
            nextLivingCells.add(cell);
        }
      }
    });
    
    apply(new Action() {
      @Override
      public void run(Cell cell) {
        grid[cell.c][cell.r] = false;
      }
    });
    
    for (Cell cell : nextLivingCells) {
      grid[cell.c][cell.r] = true;
    }
  }

  private void apply(Action action) {
    for (int r = 0; r < rows; ++r) {
      for (int c = 0; c < columns; ++c) {
        action.run(new Cell(c, r));
      }
    }
  }
  
  private interface Action {
    public void run(Cell cell);
  }
   
  private boolean isLive(Cell cell) {
    return (cell.c >= 0 && cell.c < columns && cell.r >= 0 && cell.r < rows) 
      ? grid[cell.c][cell.r]
      : false;
  }
  
  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    apply(new Action() {
      @Override
      public void run(Cell cell) {
        result.append(isLive(cell) ? "X" : ".");
        if (cell.c == columns - 1) 
          result.append("\n");
      }
    });
    
    return result.toString();
  }
}
