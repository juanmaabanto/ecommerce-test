while ! docker exec kafka_ecommerce kafka-topics --list --bootstrap-server localhost:29092 > /dev/null 2>&1; do
  echo "Waiting for kafka to create topic..."
  sleep 1
done

docker exec kafka_ecommerce kafka-topics --create --if-not-exists --topic order-receive-topic --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1