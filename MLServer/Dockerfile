FROM python:3.8.11-slim AS builder

RUN apt-get update && apt-get install -y git build-essential

ARG DEVICE=cpu

WORKDIR /home/app

RUN python -m venv venv
ENV PATH="/home/app/venv/bin:$PATH"

COPY requirements ./requirements
RUN pip install -r requirements/${DEVICE}.txt

FROM python:3.8.11-slim

RUN apt-get update \
    && apt-get install -y python3-opencv \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /home/app
COPY . .

COPY --from=builder /home/app/venv /home/app/venv

EXPOSE 5050

ENV PATH="/home/app/venv/bin:$PATH"
CMD [ "python", "src/app.py" ] 
