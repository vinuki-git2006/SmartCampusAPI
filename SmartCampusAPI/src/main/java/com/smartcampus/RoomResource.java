package com.smartcampus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/rooms")
public class RoomResource {

    public static final List<Room> rooms = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms() {
        return rooms;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room addRoom(Room room) {

        if (room.getId() <= 0) {
            throw new WebApplicationException("Invalid ID", 400);
        }

        if (room.getName() == null || room.getName().isEmpty()) {
            throw new WebApplicationException("Name is required", 400);
        }

        if (room.getCapacity() <= 0) {
            throw new WebApplicationException("Invalid capacity", 400);
        }

        rooms.add(room);
        return room;
    }

    @DELETE
    @Path("/{id}")
    public String deleteRoom(@PathParam("id") int id) {

        boolean roomExists = rooms.stream()
                .anyMatch(r -> r.getId() == id);

        if (!roomExists) {
            throw new WebApplicationException("Room not found", 404);
        }

        boolean hasSensors = SensorResource.getSensorsList().stream()
                .anyMatch(s -> {
                    try {
                        return s.getRoomId() != null &&
                               Integer.parseInt(s.getRoomId()) == id;
                    } catch (Exception e) {
                        return false;
                    }
                });

        if (hasSensors) {
            throw new WebApplicationException("Room has active sensors", 409);
        }

        rooms.removeIf(r -> r.getId() == id);
        return "Room deleted";
    }
    
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") int id) {
        return rooms.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Room not found", 404));
    }
    
     @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room updateRoom(@PathParam("id") int id, Room updatedRoom) {

        Room existing = rooms.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Room not found", 404));

        if (updatedRoom.getName() == null || updatedRoom.getName().isEmpty()) {
            throw new WebApplicationException("Name is required", 400);
        }

        if (updatedRoom.getCapacity() <= 0) {
            throw new WebApplicationException("Invalid capacity", 400);
        }

        existing.setName(updatedRoom.getName());
        existing.setCapacity(updatedRoom.getCapacity());

        return existing;
    }
}