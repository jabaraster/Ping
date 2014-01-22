package jp.co.city.mag.ping.service;

import jabara.general.Sort;

import java.util.List;

import jp.co.city.mag.ping.entity.EUser;
import jp.co.city.mag.ping.service.impl.UserServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * 
 */
@ImplementedBy(UserServiceImpl.class)
public interface IUserService {

    /**
     * @param pSort ソート条件.
     * @return 全件.
     */
    List<EUser> getAll(Sort pSort);

    /**
     * 
     */
    void insertAdministratorIfNotExists();
}
