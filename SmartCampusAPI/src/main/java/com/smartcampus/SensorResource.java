package com.smartcampus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.List;

@Path("/sensors")
public class SensorResource {

    private static final List<Sensor> sensors = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {

        if (type == null) {
            return sensors;
        }

        List<Sensor> filtered = new ArrayList<>();

        for (Sensor s : sensors) {
            if (s.getType().equalsIgnoreCase(type)) {
                filtered.add(s);
            }
        }

        return filtered;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor addSensor(Sensor sensor) {

        boolean roomExists = RoomResource.rooms.stream()
                .anyMatch(r -> r.getId() == Integer.parseInt(sensor.getRoomId()));

        if (!roomExists) {
            throw new WebApplicationException("Room not found", 422);
        }

        sensors.add(sensor);
        return sensor;
    }
    
    @Path("/{id}/readings")
    public SensorReadingResource getReadingResource(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
    
    public static List<Sensor> getSensorsList() {
        return sensors;
    }
  
}