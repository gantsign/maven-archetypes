#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static java.lang.ProcessBuilder.Redirect.INHERIT;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertThat;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.junit.Test;

public class MainIT {

  private static final String APPLICATION_JAR = System.getProperty("application.jar");

  @Test
  public void main()
    throws Exception {

    String actual = execute("java", "-jar", APPLICATION_JAR).trim();
    assertThat(actual, endsWith(".Main - Hello, World!"));
  }

  private static String execute(String... commandLine)
    throws IOException, InterruptedException {

    ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
    processBuilder.redirectError(INHERIT);
    Process process = processBuilder.start();
    InputStream inputStream = process.getInputStream();
    byte[] bytes = ByteStreams.toByteArray(inputStream);
    if (process.waitFor() != 0) {
      throw new IOException("Command failed with exit code: " + process.exitValue());
    }
    return new String(bytes, Charset.defaultCharset());
  }
}
