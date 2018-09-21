dir = new File(new File(request.outputDirectory), request.artifactId)
idea = new File(dir, ".idea")

def run(String cmd) {
    def process = cmd.execute([], dir)
    process.waitForProcessOutput((Appendable)System.out, System.err)
    if (process.exitValue() != 0) {
        throw new Exception("Command '$cmd' exited with code: ${process.exitValue()}")
    }
}

def writeCodeStyle() {
    def sb = new StringBuilder()
    sb.append('<component name="ProjectCodeStyleConfiguration">\n')
    new URL(request.properties["intellijCodeStyleUrl"]).eachLine {
        sb.append("  " + it + "\n")
    }
    sb.append("</component>")
    xml = sb.toString()
    xml = xml.replaceFirst('<code_scheme name="[^"]+"', '<code_scheme name="Project"')
    def codeStyles = new File((File) idea, "codeStyles")
    def file = new File(codeStyles, "Project.xml")
    file.setText(xml, "UTF-8")
}

run("mvn -N io.takari:maven:wrapper")
run("./mvnw sortpom:sort")
run("./mvnw license:update-file-header@config")
writeCodeStyle()
