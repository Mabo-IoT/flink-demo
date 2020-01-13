FROM python:3.7-slim-buster
WORKDIR /data
COPY ./data-generate/generate.py .
RUN pip install --no-cache-dir pulsar-client==2.4.2
CMD ["sh", "-c", "python generate.py"]