package es.unex.asee.frojomar.asee_ses.listcenters;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ApiServiceUnitTest {

    @Test
    public void getCentersTest() throws IOException {

        //Step1: we create centers list
        Center center = new Center();
        center.setId(1);
        center.setName("Centro 1");

        Center center1 = new Center();
        center1.setId(2);
        center1.setName("Centro 2");

        List<Center> centers = new ArrayList<>();
        centers.add(center);
        centers.add(center1);

        //Step2: Define a mapper Object to JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //Step3: we create a mock server, independent of the real server
        MockWebServer mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Step4: we create a standard response to any request (in this case, only getCenters request will be performed)
        MockResponse mockedResponse = new MockResponse();
        mockedResponse.setResponseCode(200);
        mockedResponse.setBody(objectMapper.writeValueAsString(centers));
        mockWebServer.enqueue(mockedResponse);

        //Step5: we link the mock server with our retrofit api
        ApiService service = retrofit.create(ApiService.class);



        //Step6: we create the call to get centers
        Call<List<Center>> call = service.getCenters();
        // and we execute the call
        Response<List<Center>> response = call.execute();

        //Step7: let's check that the call is executed
        assertTrue(response != null);
        assertTrue(response.isSuccessful());

        //Step8bis: check the body content
        List<Center> centersResponse =response.body();
        assertFalse(centersResponse.isEmpty());
        assertTrue(centersResponse.size()==2);
        //IMPORTANT: We need to have implemented "equals" in Center class to perform this assert
        assertTrue(centersResponse.contains(center));
        assertTrue(centersResponse.contains(center1));

        //Step9: Finish web server
        mockWebServer.shutdown();
    }

    @Test
    public void getEmptyCentersTest() throws IOException {

        //Step1: we create empty centers list
        List<Center> centers = new ArrayList<>();


        //Step2: Define a mapper Object to JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //Step3: we create a mock server, independent of the real server
        MockWebServer mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Step4: we create a standard response to any request (in this case, only getCenters request will be performed)
        MockResponse mockedResponse = new MockResponse();
        mockedResponse.setResponseCode(200);
        mockedResponse.setBody(objectMapper.writeValueAsString(centers));
        mockWebServer.enqueue(mockedResponse);

        //Step5: we link the mock server with our retrofit api
        ApiService service = retrofit.create(ApiService.class);



        //Step6: we create the call to get centers
        Call<List<Center>> call = service.getCenters();
        // and we execute the call
        Response<List<Center>> response = call.execute();

        //Step7: let's check that the call is executed
        assertTrue(response != null);
        assertTrue(response.isSuccessful());

        //Step8bis: check the body content
        List<Center> centersResponse =response.body();
        assertTrue(centersResponse.isEmpty());

        //Step9: Finish web server
        mockWebServer.shutdown();
    }

    @Test
    public void getCenterTest() throws IOException {

        //Step1: we create centers list
        Center center = new Center();
        center.setId(1);
        center.setName("Centro 1");

        Center center1 = new Center();
        center1.setId(2);
        center1.setName("Centro 2");


        //Step2: Define a mapper Object to JSON
        ObjectMapper objectMapper = new ObjectMapper();

        //Step3: we create a mock server, independent of the real server
        MockWebServer mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Step4: we create a standard response to any request (in this case, only getCenters request will be performed)
        MockResponse mockedResponse = new MockResponse();
        mockedResponse.setResponseCode(200);
        mockedResponse.setBody(objectMapper.writeValueAsString(center));
        mockWebServer.enqueue(mockedResponse);

        //Step5: we link the mock server with our retrofit api
        ApiService service = retrofit.create(ApiService.class);



        //Step6: we create the call to get centers
        Call<Center> call = service.getCenter(1);
        // and we execute the call
        Response<Center> response = call.execute();

        //Step7: let's check that the call is executed
        assertTrue(response != null);
        assertTrue(response.isSuccessful());

        //Step8bis: check the body content
        Center centersResponse =response.body();
        assertFalse(centersResponse.equals(center1));
        assertTrue(centersResponse.equals(center));

        //Step9: Finish web server
        mockWebServer.shutdown();
    }

}
