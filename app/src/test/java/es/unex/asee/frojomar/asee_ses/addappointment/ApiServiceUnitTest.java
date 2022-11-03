package es.unex.asee.frojomar.asee_ses.addappointment;

import org.junit.Test;

import java.io.IOException;

import es.unex.asee.frojomar.asee_ses.model.Appointment;
import es.unex.asee.frojomar.asee_ses.repository.networking.ApiService;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.TestCase.assertTrue;

public class ApiServiceUnitTest {

    @Test
    public void createAppointmentTest() throws IOException {

        //we create a mock server, independent of the real server
        MockWebServer mockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // we create a standard response to any request
        mockWebServer.enqueue(new MockResponse());
        //we link the mock server with our retrofit api
        ApiService service = retrofit.create(ApiService.class);

        //we create an appointment
        Appointment appointment = new Appointment();
        appointment.setDate("2020-03-12");
        appointment.setDescription("Dolor de cabeza");
        appointment.setId(0);
        appointment.setPersonId(1);
        appointment.setTelephone("666666666");
        appointment.setTime("10:00:00");

        // we create the call to add an appointment
        Call<ResponseBody> call = service.createAppointment(0, appointment);
        // we execute the call
        Response<ResponseBody> response = call.execute();

        //let's check that the call is executed
        assertTrue(response != null);
        assertTrue(response.isSuccessful());

        //Finish web server
        mockWebServer.shutdown();
    }
}
