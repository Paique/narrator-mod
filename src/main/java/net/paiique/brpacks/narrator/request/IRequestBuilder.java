package net.paiique.brpacks.narrator.request;

import com.google.gson.JsonObject;

public interface IRequestBuilder {

    /**
     * (Required)
     * @return HttpsRequestBuilder
     * @param requestType
     * Sets the RequestType using DataTypes.Request enum
     */
    HttpsRequestBuilder setRequestType(DataTypes.Request requestType);

    /**
     * (Required)
     * @return HttpsRequestBuilder
     * @param outputType
     * Set the file output type using DataTypes.Output enum
     */
    HttpsRequestBuilder setOutputType(DataTypes.Output outputType);


    /**
     * (Required)
     * @return HttpsRequestBuilder
     * @param url
     * Sets the URL for a api request.
     */
    HttpsRequestBuilder setURL(String url);

    /**
     * (Required)
     * @return HttpsRequestBuilder
     * @param token
     * Sets the token for the api
     */
    HttpsRequestBuilder setToken(String token);

    /**
     * (Optional)
     * @return HttpsRequestBuilder
     * @param userAgent
     * Sets the user agent (Eg:. "Mozilla/3.0")
     */
    HttpsRequestBuilder setUserAgent(String userAgent);

    HttpsRequestBuilder setRequestObject(JsonObject jsonObject);

    /**
     * (Optional)
     * @return HttpsRequestBuilder
     * @param reason
     * Set the connection reason (Eg:. "Text Generation")
     */
    HttpsRequestBuilder setBuildReason(String reason);
}
