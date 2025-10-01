package tech.challenge.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.challenge.dtos.CarSoldRequestDto;
import tech.challenge.entities.CarEntity;
import tech.challenge.services.CarService;

import java.time.LocalDateTime;
import java.util.UUID;

@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarController {

    @Inject
    CarService carService;

    @POST
    @Transactional
    public Response createCar(CarEntity carEntity) {
        try {
            CarEntity createdCar = carService.createCar(carEntity);
            return Response.status(Response.Status.CREATED).entity(createdCar).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao cadastrar veículo: " + e.getMessage()).build();
        }
    }

    @GET
    public Response findAllCars() {
        var carList = carService.findAll();
        return Response.ok(carList).build();
    }

    @GET
    @Path("/{carId}")
    public Response findCarById(@PathParam("carId") UUID carId) {
        CarEntity car = carService.findById(carId);
        if (car != null) {
            return Response.ok(car).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Veículo não encontrado").build();
        }
    }

    @PUT
    @Path("/{carId}")
    @Transactional
    public Response updateCar(@PathParam("carId") UUID carId, CarEntity updatedCar) {
        try {
            CarEntity car = carService.updateCar(carId, updatedCar);
            if (car != null) {
                return Response.ok(car).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Veículo não encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar veículo: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("set-as-sold")
    @Transactional
    public Response setCarAsSold(CarSoldRequestDto request) {
        try {
            CarEntity car = carService.setCarAsSold(request);
            return Response.ok(car).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar veículo: " + e.getMessage()).build();
        }
    }

}