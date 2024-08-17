package com.example.tic_tac_toe.service;

import java.util.List;

import com.example.tic_tac_toe.entity.Game;
import com.example.tic_tac_toe.entity.User;

public interface GameService {
	Game startNewGame(String email,String name );

	Game makeMove(Long gameId, int position);

	String checkWinner(String boardState);

	void updateUserStats(User user, String gameResult);
	
    List<User> getLeaderboard();

}
