package com.example.tic_tac_toe.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tic_tac_toe.entity.Game;
import com.example.tic_tac_toe.entity.User;
import com.example.tic_tac_toe.repository.GameRepository;
import com.example.tic_tac_toe.repository.UserRepository;
import com.example.tic_tac_toe.service.GameService;

@Service
public class GameServiceImpl implements GameService{
	   @Autowired
	    private GameRepository gameRepository;

	    @Autowired
	    private UserRepository userRepository;
	@Override
	public Game startNewGame(String email,String name) {
		  User user = userRepository.findByEmail(email).orElseGet(() -> {
		        User newUser = new User();
		        newUser.setName(name);
		        newUser.setEmail(email);
		        return userRepository.save(newUser);
		    });

		    // Now create the game with the saved user
		    Game game = new Game();
		    game.setUser(user);
		    game.setBoardState("---------"); // Empty board
		    game.setStatus("IN_PROGRESS");
		    game.setNextMove("X"); // X always starts

		    return gameRepository.save(game);
		
	}

	@Override
	public Game makeMove(Long gameId, int position) {
	    Game game = gameRepository.findById(gameId).orElseThrow();

	    // Check if the position is valid and not already taken
	    if (position < 0 || position > 8 || game.getBoardState().charAt(position) != '-') {
	        throw new IllegalArgumentException("Invalid move");
	    }

	    // Player move
	    StringBuilder board = new StringBuilder(game.getBoardState());
	    board.setCharAt(position, game.getNextMove().charAt(0)); // Player's move (either "X" or "O")
	    game.setBoardState(board.toString());

	    // Check if player wins after this move
	    String result = checkWinner(game.getBoardState());
	    if (result.equals("WON")) {
	        // Player wins, AI loses
	        game.setStatus("WON");
	        updateUserStats(game.getUser(), "WON"); // Player wins
	        return gameRepository.save(game);
	    } else if (result.equals("DRAW")) {
	        // Game is a draw
	        game.setStatus("DRAW");
	        updateUserStats(game.getUser(), "DRAW");
	        return gameRepository.save(game);
	    }

	    // Update next move to the AI
	    game.setNextMove(game.getNextMove().equals("X") ? "O" : "X");

	    // AI move (more intelligent decision-making)
	    if (game.getStatus().equals("IN_PROGRESS")) {
	        int aiMove = findWinningMove(board.toString(), game.getNextMove().charAt(0));

	        // If no winning move, try to block the player
	        if (aiMove == -1) {
	            aiMove = findWinningMove(board.toString(), game.getNextMove().equals("X") ? 'O' : 'X');
	        }

	        // If no block move, pick a random move
	        if (aiMove == -1) {
	            aiMove = findRandomMove(board.toString());
	        }

	        // Make the AI move
	        board.setCharAt(aiMove, game.getNextMove().charAt(0));
	        game.setBoardState(board.toString());

	        // Check if AI wins after this move
	        result = checkWinner(game.getBoardState());
	        if (result.equals("WON")) {
	            // AI wins, so the player loses
	            game.setStatus("LOST");
	            updateUserStats(game.getUser(), "LOST"); // Player loses
	            return gameRepository.save(game);
	        } else if (result.equals("DRAW")) {
	            // Game is a draw
	            game.setStatus("DRAW");
	            updateUserStats(game.getUser(), "DRAW");
	            return gameRepository.save(game);
	        }

	        // Update next move back to the player
	        game.setNextMove(game.getNextMove().equals("X") ? "O" : "X");
	    }

	    return gameRepository.save(game);
	}

	// Helper function to find a winning move
	private int findWinningMove(String boardState, char player) {
	    int[][] winningPositions = {
	        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
	        {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
	        {0, 4, 8}, {2, 4, 6}  // Diagonals
	    };

	    for (int[] pos : winningPositions) {
	        if (boardState.charAt(pos[0]) == player && boardState.charAt(pos[1]) == player && boardState.charAt(pos[2]) == '-') {
	            return pos[2];
	        }
	        if (boardState.charAt(pos[0]) == player && boardState.charAt(pos[2]) == player && boardState.charAt(pos[1]) == '-') {
	            return pos[1];
	        }
	        if (boardState.charAt(pos[1]) == player && boardState.charAt(pos[2]) == player && boardState.charAt(pos[0]) == '-') {
	            return pos[0];
	        }
	    }
	    return -1;
	}

	// Helper function to find a random move
	private int findRandomMove(String boardState) {
	    List<Integer> emptyPositions = new ArrayList<>();
	    for (int i = 0; i < 9; i++) {
	        if (boardState.charAt(i) == '-') {
	            emptyPositions.add(i);
	        }
	    }
	    // Pick a random position from available spots
	    Random random = new Random();
	    return emptyPositions.get(random.nextInt(emptyPositions.size()));
	}


	
	@Override
	public String checkWinner(String boardState) {   // Winning positions on the board
	    int[][] winningPositions = {
	            {0, 1, 2}, // Row 1
	            {3, 4, 5}, // Row 2
	            {6, 7, 8}, // Row 3
	            {0, 3, 6}, // Column 1
	            {1, 4, 7}, // Column 2
	            {2, 5, 8}, // Column 3
	            {0, 4, 8}, // Diagonal 1
	            {2, 4, 6}  // Diagonal 2
	        };

	        // Check for a winner
	        for (int[] pos : winningPositions) {
	            if (boardState.charAt(pos[0]) != '-' &&
	                boardState.charAt(pos[0]) == boardState.charAt(pos[1]) &&
	                boardState.charAt(pos[1]) == boardState.charAt(pos[2])) {
	                return "WON";
	            }
	        }

	        // Check for a draw
	        if (!boardState.contains("-")) {
	            return "DRAW";
	        }

	        // Otherwise, the game is still in progress
	        return "IN_PROGRESS";
	    }

	@Override
	public void updateUserStats(User user, String gameResult) {
		  switch (gameResult) {
          case "WON":
              user.setWins(user.getWins() + 1);
              break;
          case "LOST":
              user.setLosses(user.getLosses() + 1);
              break;
          case "DRAW":
              user.setDraws(user.getDraws() + 1);
              break;
      }
      userRepository.save(user);
  }

	@Override
	public List<User> getLeaderboard() {
        return userRepository.findTop10ByOrderByWinsDesc();
    }
	
}
