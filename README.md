# Tic-Tac-Toe Application
https://docs.google.com/document/d/1TSdkL8AZVhB_6Qc5HMnh4FbpaSQcI8BMYXGtN1__ZpE/edit?usp=sharing
link for detail documentation
## Overview

This is a simple Tic-Tac-Toe game application with a backend built using Spring Boot and a frontend using React. The application allows users to start a new game, make moves, and view a leaderboard of players based on their game results.

## Prerequisites

Ensure you have the following installed:

- [Java Development Kit (JDK) 11 or higher](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Apache Maven](https://maven.apache.org/)
- [Node.js and npm](https://nodejs.org/)
- [SQLite](https://www.sqlite.org/download.html) (for the database)

## Setup Instructions

### Backend Setup

1. **Clone the Repository**

   ```bash
   git clone <your-repository-url>
   cd tic-tac-toe backend
cd backend
Configure the Database

Ensure SQLite is installed. The application uses SQLite by default. Modify the application.properties file if using a different database.

Build and Run the Backend

bash
mvn clean install
mvn spring-boot:run


Frontend Setup
Navigate to the Frontend Directory

bash
Copy code
cd ../frontend
Install Dependencies

bash
Copy code
npm install
Run the Frontend

bash
Copy code
npm start
The frontend will be available at http://localhost:3000.

Usage
Start a New Game

Open http://localhost:3000 in your browser.
Enter your name and email to start a new game.
Play the Game

Click on the board to make your moves.
The AI will make its move automatically after your turn.
View the Leaderboard

The leaderboard can be accessed from the API endpoint: http://localhost:8080/api/games/leaderboard.
API Endpoints
Start a New Game: POST /api/games/start
Make a Move: POST /api/games/{gameId}/move/{position}
Get Leaderboard: GET /api/games/leaderboard
Troubleshooting
Ensure both backend and frontend servers are running.
Check the console logs in the browser and server for error messages.
Verify that the API endpoints used in the frontend match those provided by the backend.
Notes
The backend uses SQLite for simplicity. You may want to switch to a more robust database for production use.
The AI’s decision-making is basic and can be improved for a more challenging gameplay experience.![Screenshot (130)](https://github.com/user-attachments/assets/1756c6fc-e7c3-47f8-b0db-9d87b6cedb6f)
![Screenshot (129)](https://github.com/user-attachments/assets/a035c3de-5013-4974-9979-4ddaa5d8f78e)
![Screenshot (128)](https://github.com/user-attachments/assets/846c385a-eb4b-42ee-b920-296c5145748d)
![Screenshot (127)](https://github.com/user-attachments/assets/e91f8830-a47b-4b44-9bea-08255312c023)
![Screenshot (126)](https://github.com/user-attachments/assets/aee43479-82d5-43d1-a389-6abe56c05788)
