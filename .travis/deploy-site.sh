#!/usr/bin/env bash

set -e
set -x

./mvnw site \
    -P gh-pages \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version

./mvnw site:stage \
    -P gh-pages \
    -Darchetype.test.skip=true \
    --batch-mode

./mvnw scm-publish:publish-scm \
    -P gh-pages \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    --batch-mode
