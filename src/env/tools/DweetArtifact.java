package tools;

import cartago.Artifact;
import cartago.OPERATION;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/* TASK 2 - STEP 1
  Extend the implementation of the artifact that exploits the dweet.io API
*/
public class DweetArtifact extends Artifact {

  private final static String DWEET_ENDPOINT = "https://dweet.io/dweet/for/";
  private final static String PREFIX = "ruben-";

  public void init() {

  }

  @OPERATION
  public void dweet(String message, String agent) {
    log("Publish dweet with message: \"" + message + "\", agent: " + agent);

    URI uri = URI.create(DWEET_ENDPOINT + PREFIX + this.getCurrentOpAgentId().getAgentName());
    log("URI: " + uri.toString());

    HttpClient client = HttpClient.newHttpClient();
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(uri)
              .headers("Content-Type", "application/json")
              .POST(HttpRequest.BodyPublishers.ofString("{\"text\":\""+ message+ "\"}"))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      int statusCode = response.statusCode();
      if (statusCode != 200) {
        failed("Unexpected response status code from dweet: " + statusCode);
      }

  } catch (Exception e) {
     failed("Could not send dweet" + e);
  }
  }
}
