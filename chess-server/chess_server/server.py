#!/usr/bin/env python

import asyncio
import websockets
import chess

class ChessServer(object):
    def __init__(self, port):
        self.port = port
        self.running = asyncio.Event()
        self.sockets = set()

    async def start(self):
        if self.running:
            pass

        async def sockethandler(socket):
            self.sockets.add(socket)
            try:
                async for message in socket:
                    deleted = set()
                    for s in self.sockets:
                        try:
                            await s.send(message)
                        except:
                            deleted.add(s)
                    for d in deleted:
                        self.sockets.remove(d)
            except:
                self.sockets.remove(socket)
                if (len(self.sockets) == 0):
                    self.running.set()

        async with websockets.serve(sockethandler, port=self.port):
            print('running')
            await self.running.wait()


#c = ChessServer(port=8765)
#asyncio.run(c.start())
