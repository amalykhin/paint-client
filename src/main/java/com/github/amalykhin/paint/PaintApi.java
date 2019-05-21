package com.github.amalykhin.paint;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaintApi {
    private static Gson gson = new Gson();
    private static String host = "http://127.0.0.1:3000";
    private static HttpClient client = createHttpClient();

    private static HttpClient createHttpClient() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials creds =
            new UsernamePasswordCredentials("user", "pass");
        credsProvider.setCredentials(AuthScope.ANY, creds);

        return HttpClientBuilder.create()
            .setDefaultCredentialsProvider(credsProvider)
            .build();
    }

    public static List<String> getPictureList() throws Exception {
        HttpGet request = new HttpGet(host + "/pictures");

        HttpResponse response = client.execute(request);
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        List<String> filenames = gson.fromJson(new BasicResponseHandler().handleEntity(response.getEntity()), listType);

        return filenames.stream()
            .map(FilenameUtils::removeExtension)
            .collect(Collectors.toList());
    }

    public static Image getPicture(String name) throws Exception {
        HttpGet request = new HttpGet(host + "/pictures/" + name);
        HttpResponse response = client.execute(request);

        return new Image(response.getEntity().getContent());
    }

    public static void postPicture(String filename, byte[] data) throws Exception {
        HttpEntity entity = MultipartEntityBuilder.create()
            .addPart("file", new ByteArrayBody(data, ContentType.IMAGE_PNG, filename))
            .build();
        HttpPost request = new HttpPost("http://127.0.0.1:3000/pictures");
        request.setEntity(entity);

        HttpResponse response = client.execute(request);
        if(response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED) {
            System.out.println(new BasicResponseHandler().handleEntity(response.getEntity()));
            throw new Exception("Image transfer failed");
        }
    }
}
