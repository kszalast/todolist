mvn clean package
cd monolit
./1_build_docker_image.sh
cd ../exchange-rates
./7_build_docker_image.sh
cd ../frontend
./3_build_docker_image.sh
cd ../haproxy
./5_build_docker_image.sh
cd ..
docker compose up -d --force-recreate