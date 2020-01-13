import time 
import json
import random

from kafka import KafkaProducer


def generate():
    timestamp = int(time.time())
    # precision = "s"
    fields = {
        "status": random.randint(0,1),
        "temperature": round(random.uniform(20.0, 30.0),2),
    }
    measurement = "device"
    tags = {
        "eqpt_no": "PEC0-1900",
    }

    data = {
        "timestamp": timestamp,
        "fields": fields,
        "tags": tags,
    }
    data = json.dumps(data).encode('utf-8')
    return data 


def main():
    producer = KafkaProducer(bootstrap_servers='kafka:9092')

    while True:
        data = generate()
        producer.send('test', data) 
        print("send data to kafka~!")
        time.sleep(1)


if __name__ == '__main__':
    main()