package life;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameOfLive_Test {

  @Test
  public void generatesStringRepresentationOfTheBoard() {
    GameOfLife game = new GameOfLife(3, 3);
    game.setLive(1, 0);
    game.setLive(1, 1);
    game.setLive(1, 2);

    assertBoard(game, ".X.", ".X.", ".X.");
  }

  @Test
  public void stepEvolvesTheBoard() {
    GameOfLife game = new GameOfLife(3, 3);
    game.setLive(1, 0);
    game.setLive(1, 1);
    game.setLive(1, 2);
    
    game.step();
    assertBoard(game, "...", "XXX", "...");
  }

  @Test
  public void aDeadCellWithThreeNeighborsBecomesLive() {
    GameOfLife game = new GameOfLife(2, 2);
    // .X
    // XX
    
    game.setLive(1, 0);
    game.setLive(1, 1);
    game.setLive(0, 1);
    
    game.step();
    assertBoard(game, 
      "XX", 
      "XX");     
  }
  
  @Test
  public void aLiveCellWithThreeNeighborsStaysLive() {
    GameOfLife game = new GameOfLife(2, 2);
    // XX
    // XX
    game.setLive(0, 0);
    game.setLive(1, 0);
    game.setLive(1, 1);
    game.setLive(0, 1);
    
    game.step();
    assertBoard(game, 
      "XX", 
      "XX");     
  }
  
  
  private void assertBoard(GameOfLife game, String... lines) {
    StringBuilder sb = new StringBuilder();
    for (String line : lines)
      sb.append(line).append("\n");
    assertEquals(sb.toString(), game.toString());
  }
}
