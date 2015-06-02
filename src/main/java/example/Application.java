package example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TODO Add some meaningful class description...
 */
public class Application {

	private static Path WORKING = Paths.get("./working");

	public static Collection<Path> getResources(Path path) {
		Objects.requireNonNull(path);
		if (!path.toFile().exists()) {
			throw new IllegalArgumentException("'path' does not exist: " + path);
		}
		if (!path.toFile().isDirectory()) {
			throw new IllegalArgumentException("'path' is not a directory: " + path);
		}

		Set<Path> result = new HashSet<>();
		try {
			for (Path child : Files.newDirectoryStream(path)) {
				if (child.toFile().isDirectory()) {
					result.addAll(getResources(child));
				} else {
					result.add(child);
				}
			}
		} catch (IOException ignored) {
		}
		return result;
	}

	public static void main(String[] args) {

		for (Path path : getResources(WORKING)) {
			System.out.println("* " + path);
		}

		System.out.println("Done.");
	}
}
