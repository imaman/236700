package life;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameOfLive_Test {

  private GameOfLife game(String... lines) {
    int numRows = lines.length;
    int numColumns = lines[0].length();
    
    GameOfLife result = new GameOfLife(numColumns, numRows);
    for (int row = 0; row < numRows; ++row) {
      for(int column = 0; column < numColumns; ++column) {
        if (lines[row].charAt(column) == 'X')
          result.setLive(column, row);
      }
    }
    return result;
  }
  
  
  private void assertBoard(GameOfLife game, String... lines) {
    StringBuilder sb = new StringBuilder();
    for (String line : lines)
      sb.append(line).append("\n");
    assertEquals(sb.toString(), game.toString());
  }

  @Test
  public void generatesStringRepresentationOfTheBoard() {
    GameOfLife game = game(
      ".X.",
      ".X.",
      ".X.");

    assertBoard(game, ".X.", ".X.", ".X.");
  }

  @Test
  public void stepEvolvesTheBoard() {
    GameOfLife game = game(
      ".X.",
      ".X.",
      ".X.");
    
    game.step();
    assertBoard(game, 
      "...", 
      "XXX", 
      "...");
  }

  @Test
  public void aDeadCellWithThreeNeighborsBecomesLive() {
    GameOfLife game = game(
      ".X",
      "XX");

    game.step();
    assertBoard(game, 
      "XX", 
      "XX");     
  }
  
  @Test
  public void aLiveCellWithThreeNeighborsStaysLive() {
    GameOfLife game = game(
      "XX", 
      "XX");
    
    game.step();
    assertBoard(game, 
      "XX", 
      "XX");     
  }  

  @Test
  public void beehive() {
    GameOfLife game = game(
      "......",
      "..XX..",
      ".X..X.",
      "..XX..",
      "......");
    
    game.step();
    assertBoard(game, 
      "......",
      "..XX..",
      ".X..X.",
      "..XX..",
      "......");
  }  

  @Test
  public void toad() {
    GameOfLife game = game(
      "......",
      "......",
      "..XXX.",
      ".XXX..",
      "......",
      "......");
    
    game.step();
    assertBoard(game, 
      "......",
      "...X..",
      ".X..X.",
      ".X..X.",
      "..X...",
      "......");
  }  
}
