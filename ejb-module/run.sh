#!/bin/bash

echo "Eliminando cache..."
docker rm -f ejb-app || exit 1

echo "Buildando projeto..."
mvn clean package || exit 1

echo "Buildando imagem Docker..."
docker build -t ejb-app . || exit 1

echo "Subindo container (em modo interativo)..."
docker run -p 8083:8080 -p 9993:9990 ejb-app