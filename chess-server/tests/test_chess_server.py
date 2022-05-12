import pytest

from chess_server.server import ChessServer

server = ChessServer(port=8765)
pytest_plugins = ('pytest_asyncio',)


@pytest.mark.asyncio
async def test_version():
    await server.start()
