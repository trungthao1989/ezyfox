package com.tvd12.ezyfox.factory;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityCreator;
import com.tvd12.ezyfox.factory.EzySimpleEntityCreator;

public final class EzyEntityFactory {

	private static final EzyEntityCreator CREATOR 
			= EzySimpleEntityCreator.getInstance();
	public static final EzyArray EMPTY_ARRAY
			= CREATOR.newArray();
	public static final EzyObject EMPTY_OBJECT
			= CREATOR.newObject();
	
	private EzyEntityFactory() {
		// do nothing
	}
	
	public static <T> T create(Class<T> productType) {
		return CREATOR.create(productType);
	}
	
	public static EzyObject newObject() {
		return CREATOR.newObject();
	}
	
	public static EzyArray newArray() {
		return CREATOR.newArray();
	}
	
	public static EzyObjectBuilder newObjectBuilder() {
		return CREATOR.newObjectBuilder();
	}
	
	public static EzyArrayBuilder newArrayBuilder() {
		return CREATOR.newArrayBuilder();
	}
	
}
