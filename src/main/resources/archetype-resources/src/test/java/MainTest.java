#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class MainTest {

  @Test
  public void main()
    throws Exception {

    PrintStream out = System.out;
    try {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      System.setOut(new PrintStream(buffer, true, "UTF-8"));

      Main.main(new String[0]);

      assertEquals(format("Hello, World!%n"), new String(buffer.toByteArray(), UTF_8));
    } finally {
      System.setOut(out);
    }
  }

}
