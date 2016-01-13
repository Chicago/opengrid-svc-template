package org.opengrid.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.opengrid.data.KeyValuePair;


@Path("/")
public interface AutoCompleteService {
	//returns an array of strings
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/autocomplete/{listId}")
	List<KeyValuePair>  getAutoCompleteSuggestions(@PathParam("listId") String listId);
}