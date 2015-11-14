package info.aenterprise.chunkloaderz.blocks.properties;

import net.minecraft.block.properties.IProperty;

import java.util.Collection;

/**
 * Created by AEnterprise
 */
public abstract class PropertyBase implements IProperty {
	private final String name;
	private final Class clazz;

	public PropertyBase(String name, Class clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class getValueClass() {
		return clazz;
	}

	@Override
	public String getName(Comparable value) {
		return value.toString();
	}
}
