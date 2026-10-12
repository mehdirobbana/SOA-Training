package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("modules")
public class ModuleBusRestApi {

    private static ModuleBusiness moduleBusiness = new ModuleBusiness();
    private static UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    // POST /modules : ajouter un module
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addModule(Module module) {
        if (module == null || module.getUniteEnseignement() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Module or UniteEnseignement is missing")
                    .build();
        }
        if (moduleBusiness.addModule(module)) {
            return Response.status(Response.Status.CREATED).entity(module).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("UniteEnseignement not found")
                .build();
    }

    // GET /modules  ou  GET /modules?type=PROFESSIONNEL
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModules(@QueryParam("type") String type) {
        if (type == null || type.isEmpty()) {
            return Response.ok(moduleBusiness.getAllModules()).build();
        }
        try {
            Module.TypeModule typeModule = Module.TypeModule.valueOf(type.toUpperCase());
            return Response.ok(moduleBusiness.getModulesByType(typeModule)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid type. Allowed values: PROFESSIONNEL, TRANSVERSAL")
                    .build();
        }
    }

    // GET /modules/all : tous les modules
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModules() {
        return Response.ok(moduleBusiness.getAllModules()).build();
    }

    // GET /modules/ue/{code} : modules d'une UE
    @GET
    @Path("ue/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModulesByUE(@PathParam("code") int code) {
        UniteEnseignement ue = ueBusiness.getUEByCode(code);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("UniteEnseignement not found")
                    .build();
        }
        return Response.ok(moduleBusiness.getModulesByUE(ue)).build();
    }

    // GET /modules/{matricule} : un module
    @GET
    @Path("{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module m = moduleBusiness.getModuleByMatricule(matricule);
        if (m == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Module not found")
                    .build();
        }
        return Response.ok(m).build();
    }

    // PUT /modules/{matricule} : modifier
    @PUT
    @Path("{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Module is missing")
                    .build();
        }
        if (moduleBusiness.updateModule(matricule, module)) {
            return Response.ok(module).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Module or UniteEnseignement not found")
                .build();
    }

    // DELETE /modules/{matricule} : supprimer
    @DELETE
    @Path("{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (moduleBusiness.deleteModule(matricule)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Module not found")
                .build();
    }
}