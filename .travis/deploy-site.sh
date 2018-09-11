#!/usr/bin/env bash

set -e
set -x

./mvnw site \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version

./mvnw site:stage \
    -Darchetype.test.skip=true \
    --batch-mode

./mvnw scm-publish:publish-scm \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    --batch-mode
