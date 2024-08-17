package com.example.tic_tac_toe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tic_tac_toe.entity.Game;
import com.example.tic_tac_toe.entity.User;
import com.example.tic_tac_toe.service.GameService;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")

public class GameController {

    @Autowired
    private GameService gameService;
//to star the game
    @PostMapping("/start")
    public ResponseEntity<Game> startNewGame(@RequestBody User user) {
        Game game = gameService.startNewGame(user.getEmail(), user.getName());
        return ResponseEntity.ok(game);
    }

//to make the move api
    @PostMapping("/{gameId}/move/{position}")
    public Game makeMove(@PathVariable Long gameId, @PathVariable int position) {
        return gameService.makeMove(gameId, position);
    }
//to show the leader board
    @GetMapping("/leaderboard")
    public List<User> getLeaderboard() {
        return gameService.getLeaderboard();
    }
    }
