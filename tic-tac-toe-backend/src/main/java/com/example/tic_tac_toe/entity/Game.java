package com.example.tic_tac_toe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String boardState; // Example: "XOXOXOXOX"
    private String status; // "IN_PROGRESS", "WON", "LOST", "DRAW"
    private String nextMove; // "X" or "O"
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getBoardState() {
		return boardState;
	}
	public void setBoardState(String boardState) {
		this.boardState = boardState;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNextMove() {
		return nextMove;
	}
	public void setNextMove(String nextMove) {
		this.nextMove = nextMove;
	}
}