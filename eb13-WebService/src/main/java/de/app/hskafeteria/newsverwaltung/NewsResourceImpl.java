package de.app.hskafeteria.newsverwaltung;

import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;



@Path("/News")
public class NewsResourceImpl implements NewsResource {
	@Autowired
	private NewsDao newsDao;
	@Context
	UriInfo uriInfo;
	
	@Override
	public Response getAllNews() {
		List<News> news = newsDao.getAllNews();
		GenericEntity<List<News>> entity = new GenericEntity<List<News>>(news){};
		return Response.status(200).entity(entity).build();
	}

	@Override
	public Response createNews(
			JAXBElement<News> news) {
		newsDao.createNews(news.getValue());
		newsDao.shutdown();
		return Response.status(201).build();
	}

}
