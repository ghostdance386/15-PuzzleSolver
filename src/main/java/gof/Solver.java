package gof;

import java.util.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Solver {


  private int movesCount = 0;
  private final UniqueQueue<List<Integer>> queue = new UniqueQueue<>();
  private Set<List<Integer>> alreadyChecked = new HashSet<>();

  public List<Integer> findThePossibleMoves(Board board, int emptyTileIndex) {
    Set<Integer> indexesToSwapWithEmptyTile = new HashSet<>();
    for (int i = 0; i < board.getGameBoard().size(); i++) {
      if (emptyTileIndex % Board.ROWLENGTH == 0) {
        indexesToSwapWithEmptyTile.add(emptyTileIndex + 1);
        if (emptyTileIndex >= Board.ROWLENGTH) {
          indexesToSwapWithEmptyTile.add(emptyTileIndex - Board.ROWLENGTH);
        }
        if (emptyTileIndex < board.getGameBoard().size() - Board.ROWLENGTH) {
          indexesToSwapWithEmptyTile.add(emptyTileIndex + Board.ROWLENGTH);
        }
      } else if (emptyTileIndex % Board.ROWLENGTH == Board.ROWLENGTH - 1) {
        indexesToSwapWithEmptyTile.add(emptyTileIndex - 1);
        if (emptyTileIndex > (Board.ROWLENGTH - 1)) {
          indexesToSwapWithEmptyTile.add(emptyTileIndex - Board.ROWLENGTH);
        }
        if (emptyTileIndex < board.getGameBoard().size() - Board.ROWLENGTH) {
          indexesToSwapWithEmptyTile.add(emptyTileIndex + Board.ROWLENGTH);
        }
      } else {
        if (emptyTileIndex < board.getGameBoard().size() - Board.ROWLENGTH) {
          indexesToSwapWithEmptyTile.add(emptyTileIndex + 1);
          indexesToSwapWithEmptyTile.add(emptyTileIndex - 1);
          indexesToSwapWithEmptyTile.add(emptyTileIndex + Board.ROWLENGTH);
        }
        if (emptyTileIndex >= Board.ROWLENGTH) {
          indexesToSwapWithEmptyTile.add(emptyTileIndex + 1);
          indexesToSwapWithEmptyTile.add(emptyTileIndex - 1);
          indexesToSwapWithEmptyTile.add(emptyTileIndex - Board.ROWLENGTH);
        }
      }
    }
    return new ArrayList<>(indexesToSwapWithEmptyTile);
  }

  public Board solve(Board boardToSolve) throws InterruptedException {
    queue.add(boardToSolve.getGameBoard());
    System.out.println(boardToSolve.getGameBoard().toString());
    while (!queue.isEmpty()) {
      Board board = new Board();
      System.out.println("before remove " + queue.toString());
      board.setBoard(queue.remove());
      if (board.isSolved()) {
        System.out.println("Gameboard is solved");
        return board;
      }
      alreadyChecked.add(board.getGameBoard());
      List<Integer> correctMoves = findThePossibleMoves(board, board.findEmptyTile());
      for (int i = 0; i < correctMoves.size(); i++) {
        Board newBoard = new Board(board);
        move(newBoard, correctMoves.get(i), newBoard.findEmptyTile());
        queue.add(newBoard.getGameBoard());
      }
      queue.removeAll(alreadyChecked);
      Thread.sleep(1000);
    }
    return null;
  }


  public void move(Board board, int correctMove, int emptyFileIndex) {
    Collections.swap(board.getGameBoard(), correctMove, emptyFileIndex);
  }
}
