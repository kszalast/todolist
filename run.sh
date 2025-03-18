mvn clean package
./monolit/1_build_docker_image.sh
./frontend/3_build_docker_image.sh
docker compose up -d --force-recreate