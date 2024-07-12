package net.paiique.narrator.request;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.paiique.narrator.exeptions.HttpsRequest.InvalidRequestFieldExeption;
import net.paiique.narrator.request.util.ConErrorHandler;
import org.slf4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;

public class HttpsRequestBuilder implements IRequestBuilder {

    private final Logger LOGGER = LogUtils.getLogger();

    private DataTypes.Response contentType;
    private DataTypes.Request requestType;
    private DataTypes.Output outputType;
    private URL url;
    private String token;
    private String userAgent = "Mozilla/3.0";
    private String buildReason;
    private JsonObject requestObject;

    @Override
    public HttpsRequestBuilder setRequestType(DataTypes.Request requestType) {
        this.requestType = requestType;
        return this;
    }

    @Override
    public HttpsRequestBuilder setURL(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.error(e.toString());
        }
        return this;
    }

    @Override
    public HttpsRequestBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public HttpsRequestBuilder contentType(DataTypes.Response contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public HttpsRequestBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public HttpsRequestBuilder setRequestObject(JsonObject jsonObject) {
        this.requestObject = jsonObject;
        return this;
    }

    @Override
    public HttpsRequestBuilder setBuildReason(String buildReason) {
        this.buildReason = buildReason;
        return this;
    }

    @Override
    public HttpsRequestBuilder setOutputType(DataTypes.Output outputType) {
        this.outputType = outputType;
        return this;
    }

    public Object buildAndConnect() {

        try {
            if (token.isEmpty()) throw new InvalidRequestFieldExeption("Token is not set");
            if (contentType == null)
                throw new InvalidRequestFieldExeption("ContentType is null \n Valid ContentTypes: " + Arrays.toString(DataTypes.Response.values()));
            if (url == null) throw new InvalidRequestFieldExeption("The url is null");


            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(requestType.name());

            if (url.toString().contains("elevenlabs")) {
                con.setRequestProperty("xi-api-key", token);
            } else if (url.toString().contains("openai")) {
                con.setRequestProperty("Authorization", "Bearer " + token);
            }

            con.setRequestProperty("Content-Type", contentType.getContentType());
            con.setRequestProperty("User-Agent", userAgent);
            con.setDoOutput(true);

            String jsonString = new Gson().toJson(requestObject);
            System.out.println(jsonString);
            OutputStream os = con.getOutputStream();
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            if (outputType == DataTypes.Output.TEXT) {
                JsonObject respObj = (JsonObject) JsonParser.parseString(conResp(con));
                return respObj.getAsJsonArray("choices").get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();

            } else if (outputType == DataTypes.Output.VOICE) {
                return conFilePath(con);
            }
        } catch (IOException | InvalidRequestFieldExeption e) {
            LOGGER.error(String.valueOf(e));
            ConErrorHandler.handle(String.valueOf(e), buildReason);
        }
        return null;
    }

    private String conResp(HttpsURLConnection con) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    private Path conFilePath(HttpsURLConnection con) throws IOException {
        byte[] audioData;
        BufferedInputStream in = new BufferedInputStream(con.getInputStream());
        audioData = in.readAllBytes();

        Path path = Path.of("output.mp3");
        FileOutputStream fos = new FileOutputStream(path.toFile());
        fos.write(audioData);
        in.close();
        fos.close();
        return path;
    }

}
