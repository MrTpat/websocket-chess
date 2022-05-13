import Chessboard from 'chessboardjsx'
import './App.css';
import React, { useState, useEffect} from 'react';
import { Chess } from 'chess.js';
import { useBeforeunload } from 'react-beforeunload';


const ws = new WebSocket('ws://localhost:8765')
const STARTING_FEN = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1'
const game = Chess(STARTING_FEN)

function App() {
  const [side, setSide] = useState('WHITE');
  const [playerId, setPlayerId] = useState('')
  const [gameId, setGameId] = useState('')
  const [fen, setFen] = useState(STARTING_FEN)

  let handleGameInit = (data)  => {
    let side = data['side']
    let playerId = data['playerId']
    let gameId = data['gameId']
    setSide(side)
    setPlayerId(playerId)
    setGameId(gameId)
    console.log('gameID: ' + gameId)
    console.log('playerID: ' + playerId)
    console.log('side: ' + side)
  }

  let handleGameState = (data) => {
    let fen = data['fen']
    setFen(fen)
  }

  let handleMessage = (data) => {
    let message = JSON.parse(data)
    console.log(JSON.stringify(message))
    let type = message['type']
    switch(type) {
      case 'gameInit':
        handleGameInit(message)
        break
      case 'gameState':
        handleGameState(message)
        break
    }
  }
  useEffect(() => {
    ws.onmessage = function (event) {
      handleMessage(event.data)
    }
    ws.onopen = function (event) {
    };
  })
  useBeforeunload(() => {
    console.log('closing connection')
    ws.close()
  })


  let onDrop = (event) => {
    let sourceSquare = event.sourceSquare
    let targetSquare = event.targetSquare
    let move = {'from': sourceSquare, 'to': targetSquare}
    let x = game.move(move)
    console.log(JSON.stringify(x))
    move = {'from': sourceSquare.toUpperCase(), 'to': targetSquare.toUpperCase(), 'promotion': null}
    //setFen(game.fen())
    // send movement message to the server
    let message = {'gameId': gameId, 'playerId': playerId, 'move': move, 'type': 'move'}

    console.log('sent: ' + JSON.stringify(message))
    ws.send(JSON.stringify(message))
  }
  return (
    <div className="App">
      <Chessboard position={fen} onDrop={onDrop} orientation={side}/>
    </div>
  );
}

export default App;
