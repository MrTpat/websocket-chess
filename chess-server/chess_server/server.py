#!/usr/bin/env python

import asyncio
import websockets


class ChessServer(object):
    def __init__(self):
        self.socket = None
        self.running = False
        self.sockets = set()

    def start(self):
        if self.running:
            pass

        async def sockethandler(socket):
            self.socket = socket
            self.sockets.add(socket)
            async for message in socket:
                deleted = set()
                for s in self.sockets:
                    try:
                        await s.send(message)
                    except:
                        deleted.add(s)
                for d in deleted:
                    self.sockets.remove(d)


        async def startserver():
            async with websockets.serve(sockethandler, port=8765):
                self.running = True
                await asyncio.Future()  # run forever

        asyncio.run(startserver())


c = ChessServer()
c.start()