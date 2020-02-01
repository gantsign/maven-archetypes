#!/usr/bin/env bash

set -x

./mvnw install \
    -DskipTests=true \
    -Dmaven.javadoc.skip=true \
    -Darchetype.test.skip=true \
    "-Drevision=${TRAVIS_TAG:-development-SNAPSHOT}" \
    --batch-mode \
    --show-version
