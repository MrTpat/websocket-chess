FROM python:3.10

EXPOSE 5000
WORKDIR /app
COPY poetry.lock pyproject.toml /app/
ENV PYTHONPATH=${PYTHONPATH}:${PWD} 
RUN pip3 install poetry
RUN poetry config virtualenvs.create false
RUN poetry install --no-dev
COPY . .
CMD [ "poetry", "run" , "python", "chess_server/server_manager.py" ]
