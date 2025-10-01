package tech.challenge.interfaces;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.challenge.dtos.CarSalesDto;

@Path( "/cars/save")
@RegisterRestClient(configKey = "sales-api")
public interface SalesRestClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response saveCar(CarSalesDto carData);
}
