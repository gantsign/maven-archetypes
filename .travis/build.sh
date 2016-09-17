#!/bin/bash

set -e

DOCKER_FILE_DIR="$(dirname "$0")"
(cd $DOCKER_FILE_DIR && docker build -t mvn-docker .)

mvn() {
    docker run --rm -it \
        -v /var/run/docker.sock:/var/run/docker.sock \
        -v ~/.m2:/root/.m2 \
        -v "$PWD":/usr/src/maven-build \
        -w /usr/src/maven-build \
        -e "BINTRAY_USER=$BINTRAY_USER" \
        -e "BINTRAY_API_KEY=$BINTRAY_API_KEY" \
        -e "GITHUB_OAUTH2TOKEN=$GITHUB_OAUTH2TOKEN" \
        mvn-docker mvn $@
}

mvn install site --batch-mode --show-version

if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
    if [ "$TRAVIS_TAG" == "" ]; then
        mvn site-deploy --settings .travis/settings.xml --batch-mode
    else
        mvn deploy --settings .travis/settings.xml -P publish-artifacts --batch-mode \
            && mvn site-deploy --settings .travis/settings.xml -P release --batch-mode
    fi
fi
