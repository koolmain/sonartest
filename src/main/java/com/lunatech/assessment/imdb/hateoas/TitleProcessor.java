package com.lunatech.assessment.imdb.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import com.lunatech.assessment.imdb.controller.TitlesController;
import com.lunatech.assessment.imdb.model.Title;

@Component
public class TitleProcessor implements RepresentationModelProcessor<EntityModel<Title>> {

    private static final String URL_SYNTAX_EXCEPTION = "URL Syntax exception";
	private static final Logger log = LoggerFactory.getLogger(TitleProcessor.class); 
	private RepositoryRestConfiguration configuration; 

    public TitleProcessor(RepositoryRestConfiguration configuration){
        this.configuration= configuration; 
    }

    @Override
	@SuppressWarnings("java:S2259")
    public EntityModel<Title> process(EntityModel<Title> model) {
        TitlesController controller = methodOn(TitlesController.class);
        String basePath = configuration.getBasePath().toString(); 
        model.add(applyBasePath(linkTo(controller.geTitleDetails(model.getContent().getTconst())).withSelfRel(),basePath)); 
        return model;
    }


    /**
	 * Adjust the {@link Link} such that it starts at {@literal basePath}.
	 *
	 * @param link - link presumably supplied via Spring HATEOAS
	 * @param basePath - base path provided by Spring Data REST
	 * @return new {@link Link} with these two values melded together
	 */
	private static Link applyBasePath(Link link, String basePath) {

		URI uri = link.toUri();

		URI newUri = uri;
		try {
			newUri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), //
					uri.getPort(), basePath + uri.getPath(), uri.getQuery(), uri.getFragment());
		} catch (URISyntaxException e) {
			log.error(URL_SYNTAX_EXCEPTION, e); 
		}

		return Link.of(newUri.toString(), link.getRel());
	}
    
}
