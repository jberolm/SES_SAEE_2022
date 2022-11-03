package es.unex.asee.frojomar.asee_ses.repository.networking;

import java.util.List;

import es.unex.asee.frojomar.asee_ses.model.Appointment;
import es.unex.asee.frojomar.asee_ses.model.AppointmentSpecialist;
import es.unex.asee.frojomar.asee_ses.model.Center;
import es.unex.asee.frojomar.asee_ses.model.Person;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService
{
    @POST("persons/{id_person}/appointments")
    Call<ResponseBody> createAppointment(@Path("id_person")Integer id_person, @Body Appointment appointment);

    @PUT("persons/{id_person}/appointments/{id}")
    Call<ResponseBody> editAppointment(@Path("id_person")Integer id_person, @Path("id")Integer id,
                                       @Body Appointment appointment);

    @DELETE("persons/{id_person}/appointments/{id}")
    Call<ResponseBody> deleteAppointment(@Path("id_person")Integer id_person, @Path("id")Integer id);

    @PUT("persons/{id_person}/appointmentsspecialist/{id}")
    Call<ResponseBody> aplazarAppointment(@Path("id_person")Integer id_person, @Path("id")Integer id,
                                          @Body AppointmentSpecialist appointment);

    @PUT("persons/{id_person}")
    Call<ResponseBody> updateUser(@Path("id_person")Integer id_person, @Body Person person);

    @GET("centers")
    Call<List<Center>> getCenters();

    @GET("centers/{id_center}")
    Call<Center> getCenter(@Path("id_center")Integer id_center);

}
