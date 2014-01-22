/**
 * 
 */
package jp.co.city.mag.ping.service;

/**
 * @author jabaraster
 */
public class Duplicate extends Exception {
    private static final long  serialVersionUID = -3043106623389357509L;

    /**
     * 
     */
    @SuppressWarnings("synthetic-access")
    public static final Global GLOBAL           = new Global();

    /**
     * @author jabaraster
     */
    public static class Global extends Duplicate {
        private static final long serialVersionUID = -5444690262631941879L;

        private Global() {
            // 処理なし
        }
    }
}
