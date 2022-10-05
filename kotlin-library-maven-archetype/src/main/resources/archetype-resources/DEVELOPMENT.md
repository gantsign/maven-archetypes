#set($h1 = '#')
#set($h2 = '##')
#set($h3 = '###')
#set($h4 = '####')
$h1 ${projectName}

$h2 Requirements

* Java 11
* Direct internet access / Apache Maven proxy configured

$h3 Changing the required Java version

Java 11 is required by default. To move the project to Java 11, change the following two properties
in your `pom.xml`:

```xml
<properties>
  ...
  <java.require.version>[17,18)</java.require.version>
  <java.target.version>17</java.target.version>
  ...
</properties>
```

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

$h2 Security scanning

This project includes the [SpotBugs Maven Plugin](https://spotbugs.github.io/spotbugs-maven-plugin)
with the [Find Security Bugs](https://find-sec-bugs.github.io) plugin for performing static analysis
on your code. The static analysis can be quite time consuming so it's not run by default.

To run the security scan run the following from the project root:

$h4 Linux/macOS

```bash
./mvnw clean install
./mvnw spotbugs:spotbugs -P find-sec-bugs
```

$h4 Windows

```bash
mvnw.cmd clean install
mvnw.cmd spotbugs:spotbugs -P find-sec-bugs
```

To view the results run the following from each Maven module directory:

$h4 Linux/macOS

```bash
./mvnw spotbugs:gui -P find-sec-bugs
```

$h4 Windows

```bash
mvnw.cmd spotbugs:gui -P find-sec-bugs
```
