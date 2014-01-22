/**
 * 
 */
package jp.co.city.mag.ping.service;

import jp.co.city.mag.ping.model.FailAuthentication;
import jp.co.city.mag.ping.model.LoginUser;
import jp.co.city.mag.ping.service.impl.AuthenticationServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * @author jabaraster
 */
@ImplementedBy(AuthenticationServiceImpl.class)
public interface IAuthenticationService {

    /**
     * @param pUserId
     * @param pPassword
     * @return -
     * @throws FailAuthentication
     */
    LoginUser login(String pUserId, String pPassword) throws FailAuthentication;
}
