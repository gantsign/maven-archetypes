#!/usr/bin/env bash

set -x

mvn deploy \
    -P publish-artifacts \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version
