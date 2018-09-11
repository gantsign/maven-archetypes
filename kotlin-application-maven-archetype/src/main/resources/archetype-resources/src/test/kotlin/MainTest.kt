#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets.UTF_8

private fun captureStdout(callable: () -> Unit): String {
  return System.out.let { out ->
    val buffer = ByteArrayOutputStream()
    try {
      System.setOut(PrintStream(buffer, true, "UTF-8"))

      callable()

      return@let buffer.toByteArray().toString(UTF_8).trim()
    } finally {
      System.setOut(out)
    }
  }
}

class MainTest {

  @Test
  fun testMain() {
    val actual = captureStdout { main(arrayOf()) }
    assertThat(actual).endsWith(".MainKt - Hello, World!")
  }

}
