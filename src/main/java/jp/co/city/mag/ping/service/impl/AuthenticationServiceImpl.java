/**
 * 
 */
package jp.co.city.mag.ping.service.impl;

import jabara.general.ArgUtil;
import jabara.general.NotFound;
import jabara.jpa.JpaDaoBase;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jp.co.city.mag.ping.entity.ELoginPassword;
import jp.co.city.mag.ping.entity.ELoginPassword_;
import jp.co.city.mag.ping.entity.EUser_;
import jp.co.city.mag.ping.model.FailAuthentication;
import jp.co.city.mag.ping.model.LoginUser;
import jp.co.city.mag.ping.service.IAuthenticationService;

/**
 * @author jabaraster
 */
public class AuthenticationServiceImpl extends JpaDaoBase implements IAuthenticationService {
    private static final long serialVersionUID = 2054579716238078862L;

    /**
     * @param pEntityManagerFactory -
     */
    @Inject
    public AuthenticationServiceImpl(final EntityManagerFactory pEntityManagerFactory) {
        super(pEntityManagerFactory);
    }

    /**
     * @see jp.co.city.mag.ping.service.IAuthenticationService#login(java.lang.String, java.lang.String)
     */
    @Override
    public LoginUser login(final String pUserId, final String pPassword) throws FailAuthentication {
        ArgUtil.checkNullOrEmpty(pUserId, "pUserId"); //$NON-NLS-1$

        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<ELoginPassword> query = builder.createQuery(ELoginPassword.class);
        final Root<ELoginPassword> root = query.from(ELoginPassword.class);

        query.distinct(true);
        root.fetch(ELoginPassword_.user);

        query.where( //
        builder.equal(root.get(ELoginPassword_.user).get(EUser_.userId), pUserId) //
        );

        try {
            final ELoginPassword member = getSingleResult(em.createQuery(query));
            if (!member.equal(pPassword)) {
                throw FailAuthentication.INSTANCE;
            }

            return new LoginUser(member.getUser());

        } catch (final NotFound e) {
            throw FailAuthentication.INSTANCE;
        }
    }
}
