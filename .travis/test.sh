#!/usr/bin/env bash

set -euxo pipefail

./mvnw install --batch-mode --show-version
./mvnw site --batch-mode --show-version
