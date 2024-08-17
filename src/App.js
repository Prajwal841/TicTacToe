import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [user, setUser] = useState({ name: '', email: '' });
  const [game, setGame] = useState(null);
  const [leaderboard, setLeaderboard] = useState([]);
  const [message, setMessage] = useState('');

  // Handle user input
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  // Start a new game
  const startNewGame = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/games/start', user);
      setGame(response.data);
      setMessage("Game started! It's your move.");
    } catch (error) {
      setMessage('Error starting game');
    }
  };

  // Make a move
  const makeMove = async (position) => {
    if (!game || game.status !== 'IN_PROGRESS') {
      setMessage('Game is not in progress.');
      return;
    }

    try {
      const response = await axios.post(`http://localhost:8080/api/games/${game.id}/move/${position}`);
      setGame(response.data);

      if (response.data.status === 'IN_PROGRESS') {
        setMessage("AI's turn...");
      } else {
        setMessage(`Game Over: ${response.data.status}`);
        fetchLeaderboard(); // Fetch the updated leaderboard after game ends
      }
    } catch (error) {
      setMessage('Invalid move');
    }
  };

  // Get the leaderboard
  const fetchLeaderboard = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/games/leaderboard');
      setLeaderboard(response.data);
    } catch (error) {
      setMessage('Error fetching leaderboard');
    }
  };

  useEffect(() => {
    fetchLeaderboard();
  }, []);

  return (
    <div className="App">
      <h1>Tic-Tac-Toe Game</h1>

      {!game && (
        <div>
          <h2>Enter your details to start a new game</h2>
          <input type="text" name="name" placeholder="Name" value={user.name} onChange={handleInputChange} />
          <input type="email" name="email" placeholder="Email" value={user.email} onChange={handleInputChange} />
          <button onClick={startNewGame}>Start Game</button>
        </div>
      )}

      {game && (
        <div>
          <h2>{message}</h2>
          <div className="board">
            {game.boardState.split('').map((cell, index) => (
              <button key={index} onClick={() => makeMove(index)} disabled={cell !== '-' || game.status !== 'IN_PROGRESS'}>
                {cell}
              </button>
            ))}
          </div>
          {game.status !== 'IN_PROGRESS' && (
            <button onClick={() => setGame(null)}>Start New Game</button>
          )}
        </div>
      )}

      <h2>Leaderboard</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Wins</th>
            <th>Losses</th>
            <th>Draws</th>
          </tr>
        </thead>
        <tbody>
          {leaderboard.map((player, index) => (
            <tr key={index}>
              <td>{player.name}</td>
              <td>{player.email}</td>
              <td>{player.wins}</td>
              <td>{player.losses}</td>
              <td>{player.draws}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
