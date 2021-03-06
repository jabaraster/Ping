package jp.co.city.mag.ping.web.rest;

import jabara.general.Sort;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.co.city.mag.ping.entity.EUser;
import jp.co.city.mag.ping.entity.EUser_;
import jp.co.city.mag.ping.service.IUserService;

/**
 *
 */
@Path("user")
public class UserResource {

    private final IUserService employeeService;

    /**
     * @param pUserService -
     */
    @Inject
    public UserResource(final IUserService pUserService) {
        this.employeeService = pUserService;
    }

    /**
     * @return ユーザ情報全件.
     */
    @Path("all")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<EUser> getAll() {
        return this.employeeService.getAll(Sort.asc(EUser_.userId.getName()));
    }
}
