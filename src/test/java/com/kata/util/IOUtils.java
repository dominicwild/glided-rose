package com.kata.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtils {

	private IOUtils() {
		// no-op
	}

	public static void store(Object object, String path) {
		try (ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(path))) {
			objectOutput.writeObject(object);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Object retrieve(String path) {
		Object object = null;
		try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(path))) {
			object = objectInput.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}
		return object;
	}

}
