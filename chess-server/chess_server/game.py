import chess
import json


class Game(object):
    def __init__(self, black_player, white_player, game_id):
        self.black_player = black_player
        self.white_player = white_player
        self.game_id = game_id
        self.board = chess.Board()

    def is_finished(self):
        return self.board.is_game_over()

    def game_state_identifier(self, plr):
        color = 'B' if plr == self.black_player else 'W'
        return {'id': self.game_id, 'fen': self.board.fen(), 'color': color}

    # emit start messages to both players
    async def start(self):
        b_sock = self.black_player[1]
        w_sock = self.white_player[1]
        await b_sock.send(json.dumps(self.game_state_identifier(self.black_player)))
        await w_sock.send(json.dumps(self.game_state_identifier(self.white_player)))
