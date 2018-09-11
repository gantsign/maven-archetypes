#!/usr/bin/env bash

set -x

./mvnw install \
    -DskipTests=true \
    -Dmaven.javadoc.skip=true \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version
