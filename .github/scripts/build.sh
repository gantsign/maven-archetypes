#!/usr/bin/env bash

set -euxo pipefail

./mvnw install \
    "-Drevision=${GIT_TAG:-development-SNAPSHOT}" \
    --batch-mode \
    --show-version

./mvnw site \
    "-Drevision=${GIT_TAG:-development-SNAPSHOT}" \
    --batch-mode \
    --show-version
