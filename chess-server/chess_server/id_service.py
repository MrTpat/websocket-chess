class IdService(object):
    def __init__(self):
        self.player_id = 0
        self.game_id = 0

    def new_player_id(self):
        self.player_id += 1
        return self.player_id

    def new_game_id(self):
        self.game_id += 1
        return self.game_id
