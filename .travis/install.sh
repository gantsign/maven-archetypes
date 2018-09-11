#!/usr/bin/env bash

set -x

mvn install \
    -DskipTests=true \
    -Dmaven.javadoc.skip=true \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version
