/**
 * 
 */
package jp.co.city.mag.ping.web.rest;

import jabara.general.ArgUtil;
import jabara.general.NotFound;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import jp.co.city.mag.ping.entity.EPingCategory.State;
import jp.co.city.mag.ping.service.IPingCategoryService;

/**
 * @author jabaraster
 */
@Path("ping")
public class PingResource {

    private final IPingCategoryService pingCategoryService;

    /**
     * @param pPingCategoryService -
     */
    @Inject
    public PingResource(final IPingCategoryService pPingCategoryService) {
        this.pingCategoryService = ArgUtil.checkNull(pPingCategoryService, "pPingCategoryService"); //$NON-NLS-1$ 
    }

    /**
     * @param pCategory -
     * @return -
     */
    @Path("{category}")
    @Produces(MediaType.TEXT_PLAIN)
    @GET
    public State getCategoryStatus(@PathParam("category") final String pCategory) {
        try {
            return this.pingCategoryService.findStateByCategory(pCategory);
        } catch (final NotFound e) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }
}
