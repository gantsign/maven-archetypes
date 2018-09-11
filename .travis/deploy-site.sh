#!/usr/bin/env bash

set -x

mvn site site-deploy \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version
