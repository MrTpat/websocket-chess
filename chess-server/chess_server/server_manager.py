from flask import Flask, request
from server import ChessServer
import asyncio

app = Flask(__name__)

in_progress_servers = set()
available_ports = list(range(6000, 7000))
need_one_player_servers = []

@app.route('/registerPlayer')
async def registerPlayer():
    id = request.args.get('id')
    if len(need_one_player_servers) > 0:
        server = need_one_player_servers.pop()
    else:
        server = ChessServer(port=available_ports.pop())
        need_one_player_servers.append(server)
        asyncio.create_task(server.start())
    server.addPlayer(id)
    return {'port': server.port}

app.run(host='localhost', port=5000)