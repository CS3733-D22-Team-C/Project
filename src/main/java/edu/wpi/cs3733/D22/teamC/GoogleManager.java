package edu.wpi.cs3733.D22.teamC;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class GoogleManager {
    
    private static final String APPLICATION_NAME = "cs3733-application";
    private static HttpTransport httpTransport;
    private static FileDataStoreFactory dataStoreFactory;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static Plus plus;
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), ".store/plus_sample");
    
    private GoogleManager() {}
    
    public static void authenticateClient() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            // authorization
            Credential credential = authorize();
            // set up global Plus instance
            plus = new Plus.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                    APPLICATION_NAME).build();
    
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(GoogleManager.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
                            + "into /client_secrets.json");
            System.exit(1);
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(
                dataStoreFactory).build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
    
}
