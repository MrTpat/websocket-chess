#!/usr/bin/env python

import asyncio
import websockets
import chess

class ChessServer(object):
    def __init__(self, port):
        self.socket = None
        self.running = asyncio.Event()
        self.sockets = set()
        self.port = port
        self.whiteId = None
        self.blackId = None

    async def start(self):
        if self.running:
            pass

        async def sockethandler(socket):
            self.socket = socket
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
    
    def addPlayer(self,id):
        if not self.whiteId:
            self.whiteId = id
        elif not self.blackId:
            self.blackId = id
        else:
            pass



#c = ChessServer(port=8765)
#c.start()