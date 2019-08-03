#set($h1 = '#')
#set($h2 = '##')
#set($h3 = '###')
#set($h4 = '####')
$h1 ${projectName}

$h2 Requirements

* Java 8
* Direct internet access / Apache Maven proxy configured

$h2 Building

This project is built using [Maven Wrapper](https://github.com/takari/maven-wrapper).

$h3 Building Java only (no Docker image)

$h4 Linux/macOS

```bash
./mvnw install
```

$h4 Windows

```bash
mvnw.cmd install
```

$h3 Building to your local Docker daemon

$h4 Linux/macOS

```bash
./mvnw install -P docker-local
```

$h4 Windows

```bash
mvnw.cmd install -P docker-local
```

$h3 Building to a remote Docker registry

First you need to change the `docker.image` property in the `pom.xml` to specify the registry.

```xml
<properties>
  ...
  <docker.image>e.g. gcr.io/my-gcp-project/my-app</docker.image>
  ...
</properties>
```

You'll likely need to authenticate with your Docker registry, follow the
[instructions here](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#authentication-methods).

Once you've configured the authentication for your Docker registry run the following command to
build and push the Docker image (this doesn't require a local Docker daemon):

$h4 Linux/macOS

```bash
./mvnw install -P docker-registry
```

$h4 Windows

```bash
mvnw.cmd install -P docker-registry
```
