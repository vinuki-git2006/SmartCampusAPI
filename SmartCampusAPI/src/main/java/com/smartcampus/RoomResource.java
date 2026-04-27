package com.smartcampus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/rooms")
public class RoomResource {

    private static final List<Room> rooms = new ArrayList<>();

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
        rooms.removeIf(r -> r.getId() == id);
        return "Room deleted";
    }
}