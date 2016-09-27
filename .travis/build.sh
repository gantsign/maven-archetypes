#!/bin/bash

set -e

DOCKER_FILE_DIR="$(dirname "$0")"

USER_ID=$(id -u $USER)
GROUP_ID=$(id -g $USER)
GROUP_NAME=$(id -g -n $USER)
DOCKER_GID=$(getent group docker | cut -d: -f3)

cat <<EOF > $DOCKER_FILE_DIR/Dockerfile
FROM maven:3.3.9-jdk-8

RUN set -x \\
    && apt-get update \\
    ; apt-get install -y \\
		ca-certificates \\
		curl \\
		openssl \\
    && apt-get clean \\
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

ENV DOCKER_BUCKET get.docker.com
ENV DOCKER_VERSION 1.12.1
ENV DOCKER_SHA256 05ceec7fd937e1416e5dce12b0b6e1c655907d349d52574319a1e875077ccb79

RUN set -x \\
	&& curl -fSL "https://\${DOCKER_BUCKET}/builds/Linux/x86_64/docker-\${DOCKER_VERSION}.tgz" -o docker.tgz \\
	&& echo "\${DOCKER_SHA256} *docker.tgz" | sha256sum -c - \\
	&& tar -xzvf docker.tgz \\
	&& mv docker/* /usr/local/bin/ \\
	&& rmdir docker \\
	&& rm docker.tgz \\
	&& docker -v

# Delete 'ping' group (999) as it's using the same GID as 'docker' group on Travis CI.

RUN addgroup --gid $GROUP_ID $GROUP_NAME \\
    && adduser --home /home/$USER --disabled-password --ingroup $GROUP_NAME --uid $USER_ID $USER \\
    && addgroup --gid $DOCKER_GID docker \\
    && adduser $USER docker

USER $USER

ENV MAVEN_CONFIG "/home/$USER/.m2"
EOF

(cd $DOCKER_FILE_DIR && docker build -t mvn-docker .)

mvn() {
    docker run --rm -it \
        -v /var/run/docker.sock:/var/run/docker.sock \
        -v ~/.m2:/home/$USER/.m2 \
        -v "$PWD":/usr/src/maven-build \
        -w /usr/src/maven-build \
        -e "BINTRAY_USER=$BINTRAY_USER" \
        -e "BINTRAY_API_KEY=$BINTRAY_API_KEY" \
        -e "GITHUB_OAUTH2TOKEN=$GITHUB_OAUTH2TOKEN" \
        -e "VERSIONEYE_API_KEY=$VERSIONEYE_API_KEY" \
        mvn-docker mvn $@
}

mvn install site --batch-mode --show-version

GENERATED_DIR=target/test-classes/projects/basic/project/java-application-maven-archetype-generated
PROJECT_ID=57df1519c2cd3f00100b7741
if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
    if [ "$TRAVIS_BRANCH" == "master" ]; then
        mvn versioneye:update
        (cd $GENERATED_DIR && mvn versioneye:update -DprojectId=$PROJECT_ID)

        if [ "$TRAVIS_TAG" == "" ]; then
            mvn site-deploy --settings .travis/settings.xml --batch-mode
        fi
    else
        mvn versioneye:securityAndLicenseCheck
        (cd $GENERATED_DIR && mvn versioneye:securityAndLicenseCheck -DprojectId=$PROJECT_ID)
    fi

    if [ "$TRAVIS_TAG" != "" ]; then
        mvn deploy --settings .travis/settings.xml -P publish-artifacts --batch-mode \
            && mvn site-deploy --settings .travis/settings.xml -P release --batch-mode
    fi
fi
