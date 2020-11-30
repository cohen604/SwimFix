FROM python:3.8.6

COPY ./requirements.txt /usr/src/app/requirements.txt

WORKDIR /usr/src/app

RUN pip install -r requirements.txt

COPY . /usr/src/app

WORKDIR /usr/src/app

EXPOSE 8080

ENV FLASK_APP=app.py


CMD ["python3", "app.py"]
