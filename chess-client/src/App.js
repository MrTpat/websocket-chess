import Chessboard from 'chessboardjsx'
import './App.css';
import React, { useState, useEffect} from 'react';
import { Chess } from 'chess.js';
import { useLocation } from "react-router-dom";

const ws = new WebSocket('ws://localhost:8765')
const STARTING_FEN = 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1'
const game = Chess(STARTING_FEN)

function App() {
  const [fen, setFen] = useState(STARTING_FEN);
  const [connected, setConnected] = useState(false)
  const [orient, setOrient] = useState("white")

  let handleMessage = (data) => {
    console.log(data)
    setFen(data)
    game.load(data)
  }
  useEffect(() => {
    ws.onmessage = function (event) {
      handleMessage(event.data)
    }
    ws.onopen = function (event) {
      setConnected(true)
    };
    const color = window.location.search.split("=")[1];
    setOrient(color)
  })


  let onDrop = (event) => {
    let sourceSquare = event.sourceSquare
    let targetSquare = event.targetSquare
    let move = game.move(sourceSquare + '-' + targetSquare, {sloppy: true})
    setFen(game.fen())
    // send movement message to the server
    console.log('sent: ' + game.fen())
    ws.send(game.fen())
  }
  return (
    <div className="App">
      <Chessboard position={fen} onDrop={onDrop} orientation={orient}/>
    </div>
  );
}

export default App;
