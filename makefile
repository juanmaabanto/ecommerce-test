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

up: create-networks create-volumes docker-build
	@docker compose -p ecommerce-test --project-directory . \
	-f ./docker-compose.yaml \
	up -d --force-recreate