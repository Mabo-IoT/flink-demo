import pulsar
import json
import random
import time


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
    client = pulsar.Client('pulsar://standalone:6650')

    producer = client.create_producer('test')


    while True:
        data = generate()
        # data = '123'
        print(data)
        producer.send(data) 
        print("send data to pulsar~!")
        time.sleep(1)


if __name__ == '__main__':
    main()