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
