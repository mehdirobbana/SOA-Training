package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/ue")
public class UniteEnsRestApi {
    //hi instance  that will help this class
    // to manipulate data : CRUD
    UniteEnseignementBusiness helper= new UniteEnseignementBusiness();
    //get list UEs
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListUES (){
        //
        return Response
                .status(200)
                .entity(helper.getListeUE()) // Insert the name from the URL into the response
                .build();
    }
    @Path("/new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUe(UniteEnseignement unite){
        if (helper.addUniteEnseignement(unite)){
            return Response.status(201).entity("ok").build();

        }
        else {
            return Response.status(400).entity("error").build();
        }
    }
    @Path("/{inputcode}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUeByCode(@PathParam("inputcode") int code){
        return Response.status(200)
                .entity(helper.getUEByCode(code))
                .build();
    }
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getUeBySemaestre(@QueryParam("semaestre") int semaestre ){
        return  Response.ok(helper.getUEBySemestre(semaestre)).build();
    }
    @Path("/delete")
    @DELETE
    public Response deleteUe(@QueryParam("id") int id){
        if (helper.deleteUniteEnseignement(id)){
            return Response.status(204).build();
        }
        else {
            return Response.status(404).build();
        }
    }
}