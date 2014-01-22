/**
 * 
 */
package jp.co.city.mag.ping.entity;

import jabara.jpa.entity.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author jabaraster
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EPingCategory extends EntityBase<EPingCategory> {
    private static final long serialVersionUID        = 8888969132595867294L;

    /**
     * 
     */
    public static final int   MAX_CHAR_COUNT_CATEGORY = 20;

    /**
     * 
     */
    @Column(nullable = false, length = MAX_CHAR_COUNT_CATEGORY, unique = true)
    protected String          category;

    /**
     * 
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected State           state                   = State.ON;

    /**
     * @param pCategory -
     */
    public EPingCategory(final String pCategory) {
        this.category = pCategory;
    }

    /**
     * @author jabaraster
     */
    public enum State {
        /**
         * 
         */
        ON,
        /**
         * 
         */
        OFF, ;
    }
}
