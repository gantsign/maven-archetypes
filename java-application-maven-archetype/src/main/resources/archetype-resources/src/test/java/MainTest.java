#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class MainTest {

  private static String captureStdout(Runnable runnable) throws Exception {

    PrintStream out = System.out;
    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      System.setOut(new PrintStream(buffer, true, "UTF-8"));

      runnable.run();

      return new String(buffer.toByteArray(), UTF_8).trim();
    } finally {
      System.setOut(out);
    }
  }

  @Test
  public void main() throws Exception {
    String actual = captureStdout(() -> Main.main(new String[0]));
    assertThat(actual).endsWith(".Main - Hello, World!");
  }

  @Test
  public void instantiate() {
    new Main();
  }
}
