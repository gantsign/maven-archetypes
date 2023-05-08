#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static java.lang.ProcessBuilder.Redirect.INHERIT;
import static java.nio.charset.Charset.defaultCharset;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import org.junit.Test;

public class MainIT {

  private static final String DOCKER_IMAGE = System.getProperty("docker.image");

  @Test
  public void main()
      throws Exception {

    String actual = execute("docker", "run", "--rm", DOCKER_IMAGE).trim();
    assertThat(actual).endsWith(".Main -- Hello, World!");
  }

  private static String execute(String... commandLine)
      throws IOException, InterruptedException {

    ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
    processBuilder.redirectError(INHERIT);
    Process process = processBuilder.start();

    try (Reader reader = new InputStreamReader(process.getInputStream(), defaultCharset())) {
      String output = CharStreams.toString(reader);
      if (process.waitFor() != 0) {
        throw new IOException("Command failed with exit code: " + process.exitValue());
      }
      return output;
    }
  }
}
