package webservices;

import metiers.UniteEnseignementBusiness;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}