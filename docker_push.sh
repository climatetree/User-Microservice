#!/bin/bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker build -t climatetree-user .
docker tag climatetree-user patelvp/climatetree-user
docker push patelvp/climatetree-user