#!/bin/bash

echo "🔧 Buildando projeto..."
mvn clean package || exit 1

echo "🐳 Buildando imagem Docker..."
docker build -t ejb-app . || exit 1

echo "🚀 Subindo container..."
docker run -p 8083:8083 ejb-app