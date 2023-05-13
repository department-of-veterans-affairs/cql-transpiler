package gov.va.sparkcql.common;

import java.io.IOException;

final public class Resources {
	private Resources() {
	}
	
	public static String load(Object instance, String relativePath) {
		return load(instance.getClass(), relativePath);
	}

	public static String load(Class<?> cls, String relativePath) {
		try {
			var stream = cls.getResourceAsStream(relativePath);
			return new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Unable to load CQL test library '%s' from resources.", relativePath));
		}
	}

	public static String load(String relativePath) throws Exception {
		try {
			var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativePath);
			if (stream == null)
				throw new Exception("Unable to find resource. Be sure to omit a leading slash.");
			return new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new Exception(String.format("Unable to load CQL test library '%s' from resources.", relativePath));
		}
	}   	
}
