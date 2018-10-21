dir = new File(new File(request.outputDirectory), request.artifactId)
idea = new File(dir, ".idea")

def run(String cmd) {
    def process = cmd.execute(null, dir)
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

def writeInspectionProfile() {
    def sb = new StringBuilder()
    sb.append('<component name="InspectionProjectProfileManager">\n')
    def url = request.properties["intellijInspectionProfileUrl"]
    new URL(url).eachLine {
        sb.append("  " + it + "\n")
    }
    sb.append("</component>")
    xml = sb.toString()
    def m = (~/<option name="myName" value="([^"]+)"/).matcher(xml)
    if (!m.find()) {
        throw new RuntimeException("Unable to find inspection profile name in " + url)
    }
    def name = m.group(1)

    def inspectionProfiles = new File((File) idea, "inspectionProfiles")
    def file = new File(inspectionProfiles, name + ".xml")
    file.setText(xml, "UTF-8")

    def settings = new File(inspectionProfiles, "profiles_settings.xml")
    def contents = settings.getText("UTF-8").replaceFirst("@intellijInspectionProfileName@", name)
    settings.setText(contents, "UTF-8")
}

run("mvn -N io.takari:maven:wrapper")
run("./mvnw sortpom:sort")
run("./mvnw license:update-file-header@config")
run("./mvnw license:update-file-header@sources")
run("./mvnw ktlint:format")
writeCodeStyle()
writeInspectionProfile()
