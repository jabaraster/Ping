package jp.co.city.mag.ping.entity;

import jabara.jpa.entity.EntityBase_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jp.co.city.mag.ping.entity.EPingCategory.State;

@Generated(value="Dali", date="2014-01-22T11:40:54.121+0900")
@StaticMetamodel(EPingCategory.class)
public class EPingCategory_ extends EntityBase_ {
	public static volatile SingularAttribute<EPingCategory, String> category;
	public static volatile SingularAttribute<EPingCategory, State> state;
}
