/**
 * 
 */
package jp.co.city.mag.ping.service.impl;

import jabara.general.ArgUtil;
import jabara.general.NotFound;
import jabara.jpa.JpaDaoBase;
import jabara.jpa.entity.EntityBase_;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jp.co.city.mag.ping.entity.EPingCategory;
import jp.co.city.mag.ping.entity.EPingCategory.State;
import jp.co.city.mag.ping.entity.EPingCategory_;
import jp.co.city.mag.ping.service.Duplicate;
import jp.co.city.mag.ping.service.IPingCategoryService;

/**
 * @author jabaraster
 */
public class PingCategoryServiceImpl extends JpaDaoBase implements IPingCategoryService {
    private static final long serialVersionUID = 3048841480267105179L;

    /**
     * @param pEntityManagerFactory -
     */
    @Inject
    public PingCategoryServiceImpl(final EntityManagerFactory pEntityManagerFactory) {
        super(pEntityManagerFactory);
    }

    /**
     * @see jp.co.city.mag.ping.service.IPingCategoryService#delete(jp.co.city.mag.ping.entity.EPingCategory)
     */
    @Override
    public void delete(final EPingCategory pPingCategory) {
        ArgUtil.checkNull(pPingCategory, "pPingCategory"); //$NON-NLS-1$
        if (!pPingCategory.isPersisted()) {
            return;
        }
        final EntityManager em = getEntityManager();
        em.remove(em.merge(pPingCategory));
    }

    /**
     * @see jp.co.city.mag.ping.service.IPingCategoryService#findStateByCategory(java.lang.String)
     */
    @Override
    public State findStateByCategory(final String pCategory) throws NotFound {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EPingCategory> query = builder.createQuery(EPingCategory.class);
        final Root<EPingCategory> root = query.from(EPingCategory.class);

        query.where(builder.equal(root.get(EPingCategory_.category), pCategory));

        return getSingleResult(em.createQuery(query)).getState();
    }

    /**
     * @see jp.co.city.mag.ping.service.IPingCategoryService#getAll()
     */
    @Override
    public List<EPingCategory> getAll() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EPingCategory> query = builder.createQuery(EPingCategory.class);
        final Root<EPingCategory> root = query.from(EPingCategory.class);

        query.orderBy(builder.asc(root.get(EPingCategory_.category)));

        return em.createQuery(query).getResultList();
    }

    /**
     * @see jp.co.city.mag.ping.service.IPingCategoryService#insert(jp.co.city.mag.ping.entity.EPingCategory)
     */
    @Override
    public void insert(final EPingCategory pPingCategory) throws Duplicate {
        ArgUtil.checkNull(pPingCategory, "pEPingCategory"); //$NON-NLS-1$
        if (pPingCategory.isPersisted()) {
            throw new IllegalArgumentException("永続化済みのエンティティはこのメソッドでは処理出来ない."); //$NON-NLS-1$
        }
        insertCore(pPingCategory);
    }

    /**
     * @see jp.co.city.mag.ping.service.IPingCategoryService#insertOrUpdate(jp.co.city.mag.ping.entity.EPingCategory)
     */
    @Override
    public void insertOrUpdate(final EPingCategory pPingCategory) throws Duplicate {
        ArgUtil.checkNull(pPingCategory, "pCategory"); //$NON-NLS-1$
        if (pPingCategory.isPersisted()) {
            updateCore(pPingCategory);
        } else {
            insertCore(pPingCategory);
        }
    }

    private boolean existsCategoryForUpdate(final EPingCategory pPingCategory) {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EPingCategory> query = builder.createQuery(EPingCategory.class);
        final Root<EPingCategory> root = query.from(EPingCategory.class);

        query.where( //
                builder.notEqual(root.get(EntityBase_.id), pPingCategory.getId()) //
                , builder.equal(root.get(EPingCategory_.category), pPingCategory.getCategory()) //
        );
        try {
            getSingleResult(em.createQuery(query));
            return true;
        } catch (final NotFound e) {
            return false;
        }
    }

    private void insertCore(final EPingCategory pPingCategory) throws Duplicate {
        try {
            findStateByCategory(pPingCategory.getCategory());
            throw Duplicate.GLOBAL;
        } catch (final NotFound e) {
            getEntityManager().persist(pPingCategory);
        }
    }

    private void updateCore(final EPingCategory pPingCategory) throws Duplicate {
        if (existsCategoryForUpdate(pPingCategory)) {
            throw Duplicate.GLOBAL;
        }

        final EPingCategory merged = getEntityManager().merge(pPingCategory);
        merged.setCategory(pPingCategory.getCategory());
        merged.setState(pPingCategory.getState());
    }
}
