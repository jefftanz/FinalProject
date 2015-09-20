package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.jeff.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * Created by jeff on 9/17/15.
 */
public class EndpointsAsyncTaskTest extends TestCase {

    public void testSomeAsynTask() throws Throwable {

        final AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {
            private MyApi myApiService = null;
            //private Context context;

            @Override
            protected String doInBackground(String... params) {
                if(myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    // end options for devappserver

                    myApiService = builder.build();
                }

                //context = params[0].first;
                //String name = params[0].second;

                try {
                    //return myApiService.sayHi(name).execute().getData();
                    return myApiService.sayHi("").execute().getData();

                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                assertTrue(result != null && result.length() > 0);
            }
        };

        myTask.execute("");

    }
}