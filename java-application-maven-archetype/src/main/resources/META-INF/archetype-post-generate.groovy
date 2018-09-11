dir = new File(new File(request.outputDirectory), request.artifactId)

def run(String cmd) {
    def process = cmd.execute([], dir)
    process.waitForProcessOutput((Appendable)System.out, System.err)
    if (process.exitValue() != 0) {
        throw new Exception("Command '$cmd' exited with code: ${process.exitValue()}")
    }
}

run("mvn -N io.takari:maven:wrapper")
run("./mvnw sortpom:sort")
run("./mvnw license:update-file-header@config")
run("./mvnw license:update-file-header@sources")
run("./mvnw fmt:format")
