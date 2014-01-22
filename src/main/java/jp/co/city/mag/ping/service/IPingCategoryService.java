/**
 * 
 */
package jp.co.city.mag.ping.service;

import jabara.general.NotFound;

import java.util.List;

import jp.co.city.mag.ping.entity.EPingCategory;
import jp.co.city.mag.ping.service.impl.PingCategoryServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * @author jabaraster
 */
@ImplementedBy(PingCategoryServiceImpl.class)
public interface IPingCategoryService {

    /**
     * @param pCategory -
     * @return -
     * @throws NotFound -
     */
    EPingCategory.State findStateByCategory(final String pCategory) throws NotFound;

    /**
     * @return -
     */
    List<EPingCategory> getAll();

    /**
     * @param pPingCategory -
     * @throws Duplicate -
     */
    void insert(final EPingCategory pPingCategory) throws Duplicate;

    /**
     * @param pPingCategory -
     * @throws Duplicate カテゴリ名が重複した場合.
     */
    void insertOrUpdate(final EPingCategory pPingCategory) throws Duplicate;

    /**
     * @param pPingCategory -
     */
    void delete(final EPingCategory pPingCategory);

}
