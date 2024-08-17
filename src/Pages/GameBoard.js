import React, { useState, useEffect } from 'react';

function GameBoard() {
  const [board, setBoard] = useState(Array(9).fill(null));
  const [nextMove, setNextMove] = useState('X'); // 'X' for user, 'O' for AI
  const [gameId, setGameId] = useState(null);
  const [status, setStatus] = useState('Game in progress');
  const user = JSON.parse(localStorage.getItem('user'));

  useEffect(() => {
    // Start a new game
    const startGame = async () => {
      const response = await fetch('http://localhost:8080/api/games', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: user.id,
          boardState: board.join(','),
          nextMove: 'X',
        }),
      });
      const game = await response.json();
      setGameId(game.id);
    };

    startGame();
  }, []);

  const handleClick = async index => {
    if (board[index] || status !== 'Game in progress') return;

    const newBoard = board.slice();
    newBoard[index] = nextMove;

    setBoard(newBoard);

    // Send the move to the backend
    const response = await fetch(
      `http://localhost:8080/api/games/${gameId}/move`,
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          boardState: newBoard.join(','),
          nextMove: 'O', // AI plays after user
        }),
      }
    );

    const game = await response.json();
    setBoard(game.boardState.split(','));
    setNextMove(game.nextMove);
    setStatus(game.status);
  };

  return (
    <div>
      <h1>{status}</h1>
      <div className='board'>
        {board.map((value, index) => (
          <button key={index} onClick={() => handleClick(index)}>
            {value}
          </button>
        ))}
      </div>
    </div>
  );
}

export default GameBoard;
