package jp.co.city.mag.ping;

/**
 *
 */
public final class Environment {

    /**
     * 
     */
    public static final String  APPLICATION_NAME     = "Ping";                        //$NON-NLS-1$

    private static final String PARAM_PREFIX         = "ping.";                       //$NON-NLS-1$

    /**
     * 
     */
    public static final String  PARAM_ADMIN_PASSWORD = PARAM_PREFIX + "adminPassword"; //$NON-NLS-1$

    /**
     * @return -
     */
    public static String getAdminPassword() {
        return getString(PARAM_ADMIN_PASSWORD, "admin"); //$NON-NLS-1$
    }

    /**
     * @return アプリケーション名.
     */
    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    private static String getString(final String pParameterName, final Object pDefaultValue) {
        String value = System.getenv(pParameterName);
        if (value == null) {
            value = System.getProperty(pParameterName); // テスト用.
        }
        if (value != null) {
            return value;
        }
        return pDefaultValue == null ? null : pDefaultValue.toString();
    }

}
