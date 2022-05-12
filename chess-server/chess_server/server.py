#!/usr/bin/env python

import asyncio
import websockets
import json

from chess_server.game import Game
from chess_server.id_service import IdService


class ChessServer(object):
    def __init__(self, port):
        self.port = port
        self.running = asyncio.Event()
        self.connections = set()
        self.games = {}
        self.queued = set()
        self.id_to_socket: dict[int, websockets.server.WebSocketServerProtocol] = {}
        self.id_service = IdService()

    # assigns a player to a game
    async def registerPlayer(self, socket):
        player_id = self.id_service.new_player_id()
        self.id_to_socket[player_id] = socket
        if len(self.queued) == 0:
            self.queued.add(player_id)
        else:
            opponent = self.queued.pop()
            game_id = self.id_service.new_game_id()
            game = Game(opponent, player_id, game_id, self)
            self.games[game_id] = game
            await game.start()

    async def handle_message(self, msg, socket):
        msg = json.loads(msg)
        msg_type = msg['type']
        if msg_type == 'register':
            await self.registerPlayer(socket)

    async def start(self):
        if self.running:
            pass

        async def sockethandler(socket):
            self.connections.add(socket)
            print(len(self.connections))
            try:
                async for message in socket:
                    self.handle_message(message, socket)
            finally:
                self.connections.remove(socket)

        async with websockets.serve(sockethandler, port=self.port):
            await self.running.wait()

# c = ChessServer(port=8765)
# asyncio.run(c.start())
