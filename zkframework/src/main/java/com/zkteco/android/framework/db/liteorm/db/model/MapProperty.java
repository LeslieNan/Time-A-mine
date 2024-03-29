package com.zkteco.android.framework.db.liteorm.db.model;

import com.zkteco.android.framework.db.liteorm.db.enums.Relation;

import java.lang.reflect.Field;

/**
 * 映射关系
 * @author MaTianyu
 * 2014-3-7下午11:16:19
 */
public class MapProperty extends com.zkteco.android.framework.db.liteorm.db.model.Property {
	private static final long serialVersionUID = 1641409866866426637L;
	public static final String PRIMARYKEY = " PRIMARY KEY ";
	public Relation relation;

	public MapProperty(com.zkteco.android.framework.db.liteorm.db.model.Property p, Relation relation) {
		this(p.column, p.field, relation);
	}

	private MapProperty(String column, Field field, Relation relation) {
		super(column, field);
		this.relation = relation;
	}

    public boolean isToMany(){
        return relation == Relation.ManyToMany || relation == Relation.OneToMany;
    }

    public boolean isToOne(){
        return relation == Relation.ManyToOne || relation == Relation.OneToOne;
    }

}
