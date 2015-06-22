package org.opengrid.service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.eclipse.jetty.util.ajax.JSON;
import org.opengrid.service.OpenGridResult;

@Path("/")
public interface OpenGridService {
	@GET 
	@Description(value="Resource", target="DocTarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	public String getAllOpenGridLists();
	
	//Users
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/{users}/{password}")
	public OpenGridResult postOpenGridUserToken(@PathParam("users") final String users, @PathParam("password") final String password);
	
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/users/{filter}")
	public String getOpenGridUserList(@PathParam("filter") final String filter);
	
	@GET 
	@Description(value="Resource", target="DocTarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/users/{userId}")
	public String getOpenGridOneUser(@PathParam("usersId") final String userId);
	
	@PUT 
	@Description(value="Resource", target="DocTarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/users/{userId}")
	public OpenGridResult updateOpenGridOneUser(@PathParam("usersId") final String userid);
	
	//Groups
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups")
	public String getOpenGridGroupsList();
	
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups")
	public OpenGridResult createOpenGridNewGroup();
	
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}")
	public String getOpenGridOneGroup(@PathParam("groupId") final String groupId);
	
	@PUT 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}")
	public OpenGridResult updateOpenGridOneGroup(@PathParam("groupId") final String groupId);
	
	
	
	@DELETE 
	@Description(value="Resource", target="DocTarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}")
	public OpenGridResult deleteOpenGridOneGroup(@PathParam("groupId") final String groupId);
	
	//Group Member
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}/members")
	public String getOpenGridOneGroupMembersList(@PathParam("groupId") final String groupId);
	
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}/members")
	public OpenGridResult addOpenGridOneGroupOneMember(@PathParam("groupId") final String groupId);
	
	@DELETE 
	@Description(value="Resource", target="DocTarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}/members/{memberId}")
	public OpenGridResult deleteOpenGridOneGroupOneMember(@PathParam("groupId") final String groupId, @PathParam("memberId") final String memberId);
	
	//Group Alert
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}/alerts")
	public String getOpenGridOneGroupAlertsList(@PathParam("groupId") final String groupId);
	
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/groups/{groupId}/alerts")
	public OpenGridResult createOpenGridOneGroupNewAlert(@PathParam("groupId") String groupId);
	
	
	//Alert
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/alerts")
	public String getOpenGridAlertsList();
	
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/alerts")
	public OpenGridResult addOpenGridNewAlert();

	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/alerts/{alertId}")
	public String getOpenGridOneAlert(@PathParam("alertId") final String alertId);
	
	@PUT
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/alerts/{alertId}")
	public OpenGridResult putOpenGridOneAlert(@PathParam("alertId") final String alertId);
	
	@DELETE 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/alerts/{alertId}")
	public OpenGridResult deleteOpenGridOneAlert(@PathParam("alertId") final String alertId);

	//Datasets
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/datasets")
	public String getOpenGridDatasetsList();
	
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/datasets")
	public OpenGridResult addOpenGridNewdataset();

	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/datasets/{datasetId}")
	public String getOpenGridOneDataset(@PathParam("datasetId") final String datasetId);
	
	
	//Datasets Query
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/datasets/{datasetId}/query")
	public String executeOpenGridQueryWithParams(@PathParam("datasetId") final String datasetId, 
												@QueryParam("q") final String filter,
												@QueryParam("n") final int max,
												@QueryParam("s") final String sort
												);
	
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/datasets/{datasetId}/query/{queryId}")
	public String executeOpenGridQuery(@PathParam("datasetId") final String datasetId, @PathParam("queryId") final String queryId);
	
	
	//Queries
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/queries")
	public String getOpenGridQueriesList(@QueryParam("q") final String filter,
										@QueryParam("n") final int max,
										@QueryParam("s") final String sort);
	
	@POST 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/queries")
	public String addOpenGridNewQuery(final String requestBody);
	//public String addOpenGridNewQuery(@QueryParam("o") final String entity);
		
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/queries/{queryId}")
	public String getOpenGridOneQuery(@PathParam("queryId") final String queryId);
	
	@PUT 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/queries/{queryId}")
	public OpenGridResult updateOpenGridOneQuery(@PathParam("queryId") final String queryId,
										@QueryParam("o") final String entity);
	
	
	@DELETE 
	@Description(value="Resource", target="DocTarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/queries/{queryId}")
	public OpenGridResult deleteOpenGridOneQuery(@PathParam("queryId") final String querId);
	
	//Geoboundries
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/geoboundaries")
	public String getOpenGridGeoboundariesList();
	
	@GET 
	@Description(value="Resource", target="Doctarget.RESOURCE")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_PLAIN})
	@Path("/geoboundaries/{boundaryId}")
	public String getOpenGridOneGeoboundary(@PathParam("boundaryId") final String boudaryId);
}
