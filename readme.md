# overview
Flink Demo
# dev
1. generate demo kafka data by python;
2. build python env by docker-compose;
# usage
1. build flink app, and you can find `flink-quickstart-1.0-SNAPSHOT.jar` in `flink-quickstart/target` dir:
   ```
   cd flink-quickstart
   mvn clean package 
   ```
2. start all app:
   ```
   docker-compose up
   ```
3. submit build jar to flink app and you can see the running state, also can run
   ```
   docker logs --tail 10 -f flink-demo_taskmanager_1
   ``` 
   to see result
