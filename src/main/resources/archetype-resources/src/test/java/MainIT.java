#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static com.google.common.truth.Truth.assertThat;
import static java.lang.ProcessBuilder.Redirect.INHERIT;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.junit.Test;

public class MainIT {

  private static final String DOCKER_IMAGE = System.getProperty("docker.image");

  @Test
  public void main()
      throws Exception {

    String actual = execute("docker", "run", "--rm", DOCKER_IMAGE).trim();
    assertThat(actual).endsWith(".Main - Hello, World!");
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
