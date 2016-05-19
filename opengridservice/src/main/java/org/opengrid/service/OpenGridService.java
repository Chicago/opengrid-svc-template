package org.opengrid.service;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.cors.CorsHeaderConstants;

import org.opengrid.data.ServiceCapabilities;
import org.opengrid.exception.ServiceException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/")
public interface OpenGridService {
	
	//authentication resource
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/users/token")
	public void getOpenGridUserToken(final String payLoad);
	
	//reads existing token from request header and renews expiry time
	@POST
	@Path("/users/renew")
	public void refreshOpenGridUserToken(@Context MessageContext mc);
		
	//lists all datasets and descriptors
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/datasets")
	public String getOpenGridDatasetList(@Context MessageContext mc) throws JsonParseException, JsonMappingException, ServiceException, IOException;

	
	//returns descriptor for a specific dataset
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/datasets/{datasetId}")
	public String getOpenGridDataset(@PathParam("datasetId") final String datasetId) throws JsonParseException, JsonMappingException, ServiceException, IOException;
	
	
		
	//executes query against one dataset/data type
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/datasets/{datasetId}/query")
	public String executeOpenGridQueryWithParams(@PathParam("datasetId") final String datasetId, 
												@QueryParam("q") final String filter,
												@QueryParam("n") final int max,
												@QueryParam("s") final String sort,
												@QueryParam("opts") final String options
												);
	
	//lists queries meeting specified filter
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/queries")
	public String getOpenGridQueriesList(@QueryParam("q") final String filter,
										@QueryParam("n") final int max,
										@QueryParam("s") final String sort);
	
	
	//updates an existing query
	@PUT 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/queries/{queryId}")
	public String updateOpenGridQuery(@PathParam("queryId") final String queryId,
												final String payLoad);
		

	//inserts new query, returns new object with id attribute
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/queries")
	public String addOpenGridNewQuery(final String payLoad);

	
	//deletes query
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/queries/{queryId}")
	public void deleteOpenGridQuery(@PathParam("queryId") final String queryId);
	

	//lists all users meeting given criteria
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users")
	public String getOpenGridUsersList(@QueryParam("q") final String filter,
									@QueryParam("n") final int max,
									@QueryParam("s") final String sort);

	//returns query given id
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/queries/{queryId}")
	public String getOpenGridQuery(@PathParam("queryId") final String queryId);

	

	//inserts new user, returns new object with id attribute
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users")
	public String addOpenGridNewUser(final String payLoad);
		
	
	//updates an existing user
	@PUT 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userId}")
	public String updateOpenGridUser(@PathParam("userId") final String userId,
												final String payLoad);

	//returns user given id
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userId}")
	public String getOpenGridUser(@PathParam("userId") final String userId);

	//deletes user
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userId}")
	public void deleteOpenGridUser(@PathParam("userId") final String userId);	
	

	//lists all groups meeting given criteria
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/groups")
	public String getOpenGridGroupsList(@QueryParam("q") final String filter,
									@QueryParam("n") final int max,
									@QueryParam("s") final String sort);


	//returns group given id
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/groups/{groupId}")
	public String getOpenGridGroup(@PathParam("groupId") final String groupId);


	//inserts new group, returns new object with id attribute
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/groups")
	public String addOpenGridNewGroup(final String payLoad);
		
	
	//updates an existing group
	@PUT 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/groups/{groupId}")
	public String updateOpenGridgroup(@PathParam("groupId") final String groupId,
												final String payLoad);

	//deletes group
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/groups/{groupId}")
	public void deleteOpenGridGroup(@PathParam("groupId") final String groupId);
	
	
	//returns capabilities flags
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/capabilities")
	public ServiceCapabilities getServiceCapabilities();

	@OPTIONS
	@Path("{path : .*}")
	public Response options();
	
}
