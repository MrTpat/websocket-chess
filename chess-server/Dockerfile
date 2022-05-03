FROM python:3.10

EXPOSE 8765
WORKDIR /app
COPY . .
ENV PYTHONPATH=${PYTHONPATH}:${PWD} 
RUN pip3 install poetry
RUN poetry config virtualenvs.create false
RUN poetry install --no-dev
CMD [ "poetry", "run" , "python", "chess_server/server.py" ]