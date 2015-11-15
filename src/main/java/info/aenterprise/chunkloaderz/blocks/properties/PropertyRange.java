package info.aenterprise.chunkloaderz.blocks.properties;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by AEnterprise
 */
public class PropertyRange extends PropertyBase {
	private final Collection allowedValues;

	public PropertyRange(String name, int start, int end, int increment) {
		super(name, Integer.class);
		allowedValues = new ArrayList();
		for (int i = start; i <= end; i += increment) {
			allowedValues.add(i);
		}
		System.out.println("created propertyscale");
	}

	@Override
	public Collection getAllowedValues() {
		return allowedValues;
	}
}
