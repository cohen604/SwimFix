FROM python:3.8.11-slim

RUN apt-get update && apt-get install -y sudo git build-essential python3-opencv

ARG DEVICE=cpu
ARG USER_ID=1000
RUN useradd -m --no-log-init --system  --uid ${USER_ID} appuser -g sudo
RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
USER appuser
WORKDIR /home/appuser

COPY requirements ./requirements
RUN pip install -r requirements/${DEVICE}.txt

COPY . .
EXPOSE 5050

CMD [ "python", "src/app.py" ] 
