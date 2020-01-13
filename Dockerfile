FROM python:3.7-slim-buster
WORKDIR /data
COPY ./data-generate/generate.py .
RUN pip install --no-cache-dir kafka-python==1.4.7
CMD ["sh", "-c", "python generate.py"]