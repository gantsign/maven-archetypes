#!/usr/bin/env bash

set -euxo pipefail

./mvnw install \
    "-Drevision=${TRAVIS_TAG:-development-SNAPSHOT}" \
    --batch-mode \
    --show-version

./mvnw site \
    "-Drevision=${TRAVIS_TAG:-development-SNAPSHOT}" \
    --batch-mode \
    --show-version
