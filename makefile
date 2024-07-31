create-networks:
	@if docker network inspect ecommerce-network > /dev/null 2>&1; then \
		echo "Network 'ecommerce-network' already exists"; \
	else \
		docker network create --driver bridge ecommerce-network; \
		echo "network created: 'ecommerce-network'"; \
	fi

create-volumes:
	@if docker volume inspect mongo-data > /dev/null 2>&1; then \
		echo "Volume 'mongo-data' already exists"; \
	else \
		docker volume create mongo-data; \
		echo "volume created: 'mongo-data'"; \
	fi

docker-build:
	docker build -t product-query ./services/product-query
	docker build -t customer-query ./services/customer-query
	docker build -t process-order-command ./services/process-order-command

up: create-networks create-volumes docker-build
	@docker compose -p ecommerce-test --project-directory . \
	-f ./docker-compose.yaml \
	up -d --force-recreate
	chmod +x ./kafka/create-topic.sh && ./kafka/create-topic.sh

producer:
	@echo '{"date": "2024-07-31", "customerID": "60d5f483f1e1b6c9b4f6c98f", "products": [{"id": "60d5f483f1e1b6c9b4f6c993", "quantity": 1}, {"id": "60d5f483f1e1b6c9b4f6c994", "quantity": 2}]}' | docker exec -i kafka_ecommerce /usr/bin/kafka-console-producer --broker-list localhost:29092 --topic order-receive-topic