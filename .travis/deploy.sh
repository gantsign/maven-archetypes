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

gpg --list-secret-keys --verbose

./mvnw deploy \
    -P publish-artifacts,sign-artifacts,ossrh-deploy \
    --settings .travis/settings.xml \
    -Darchetype.test.skip=true \
    "-Drevision=${TRAVIS_TAG:-development-SNAPSHOT}" \
    --batch-mode \
    --show-version
