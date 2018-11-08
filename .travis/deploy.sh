#!/usr/bin/env bash

set -e
set +x

# See http://www.debonair.io/post/maven-cd/ for instructions
openssl aes-256-cbc \
    -K "$encrypted_458d6d279c1d_key" \
    -iv "$encrypted_458d6d279c1d_iv" \
    -in .travis/codesigning.asc.enc \
    -out .travis/codesigning.asc -d
gpg --fast-import .travis/codesigning.asc

set -x

gpg --list-secret-keys

./mvnw deploy \
    -P publish-artifacts \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    --batch-mode \
    --show-version
