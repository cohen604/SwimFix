FROM python:3.8.11-slim

RUN apt-get update && apt-get install -y sudo git build-essential python3-opencv

ARG DEVICE=cpu

WORKDIR /home/appuser

COPY requirements ./requirements
RUN pip install --user -r requirements/${DEVICE}.txt

COPY . .
EXPOSE 5050

CMD [ "python", "src/app.py" ] 
