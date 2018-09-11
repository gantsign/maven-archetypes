#!/usr/bin/env bash

set -e
set -x

mvn site \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version

mvn site:stage \
    -Darchetype.test.skip=true \
    --batch-mode

mvn scm-publish:publish-scm \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    --batch-mode
