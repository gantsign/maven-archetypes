#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.IOException
import java.lang.ProcessBuilder.Redirect.INHERIT
import java.nio.charset.Charset.defaultCharset

private val DOCKER_IMAGE = System.getProperty("docker.image")

private fun execute(vararg commandLine: String): String {
    val processBuilder = ProcessBuilder(*commandLine)
    processBuilder.redirectError(INHERIT)
    val process = processBuilder.start()

    if (process.waitFor() != 0) {
        throw IOException("Command failed with exit code: ${process.exitValue()}")
    }
    val output = process.inputStream.bufferedReader(defaultCharset()).readText()
    return output.trim()
}

class MainIT {

    @Test
    fun main() {
        val actual = execute("docker", "run", "--rm", DOCKER_IMAGE)
        assertThat(actual).endsWith(".Main -- Hello, World!")
    }
}
