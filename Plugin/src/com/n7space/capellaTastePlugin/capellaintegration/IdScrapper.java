// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for scrapping and seeking IDs within the Capella model.
 *
 */
public class IdScrapper {

	/**
	 * Returns all IDs that can be found traversing the model from the given list of
	 * roots.
	 *
	 * @param objects
	 *            Objects from which to start the ID search
	 * @return The list of all found IDs
	 */
	public static HashSet<String> getAllIds(final List<Object> objects) {
		final HashSet<String> ids = new HashSet<String>();
		for (final Object object : objects)
			getAllIds(object, ids);
		return ids;
	}

	/**
	 * Returns all IDs that can be found traversing the model from the given root.
	 *
	 * @param objects
	 *            Object from which to start the ID search
	 * @return The list of all found IDs
	 */
	public static HashSet<String> getAllIds(final Object object) {
		final HashSet<String> ids = new HashSet<String>();
		getAllIds(object, ids);
		return ids;
	}

	protected static void getAllIds(final Object startObject, final HashSet<String> ids) {
		if (!isClassOfInterest(startObject.getClass()))
			return;
		final String id = getId(startObject);
		if (ids.contains(id))
			return;
		ids.add(id);
		final List<Method> methods = getAllListingMethods(startObject);
		for (final Method method : methods) {
			try {
				final List<?> objects = (List<?>) method.invoke(startObject);
				for (final Object object : objects)
					getAllIds(object, ids);
			} catch (final Throwable t) {

			}
		}
	}

	protected static List<Method> getAllListingMethods(final Object object) {
		final LinkedList<Method> result = new LinkedList<>();
		for (final Method method : object.getClass().getMethods()) {
			if (method.getParameterCount() > 0)
				continue;
			final Class<?> returnType = method.getReturnType();
			if (isClassOfListType(returnType)) {
				result.add(method);
			}
		}
		return result;
	}

	protected static String getId(final Object object) {
		try {
			final Method method = object.getClass().getMethod("getId");
			if (method == null)
				return null;
			if (!method.getReturnType().equals(String.class))
				return null;
			return (String) method.invoke(object);
		} catch (final Throwable t) {
			return null;
		}
	}

	protected static String getName(final Object object) {
		try {
			final Method method = object.getClass().getMethod("getName");
			if (method == null)
				return null;
			if (!method.getReturnType().equals(String.class))
				return null;
			return (String) method.invoke(object);
		} catch (final Throwable t) {
			return null;
		}
	}

	/**
	 * Returns the path to an object with the given ID
	 *
	 * @param startObject
	 *            Starting object
	 * @param searchedId
	 *            ID of the object to be found
	 * @return A list of object and method names to be invoked in order to obtain an
	 *         object with the given id
	 */
	public static List<String> getPathToId(final Object startObject, final String searchedId) {
		final HashSet<String> ids = new HashSet<String>();
		final List<String> path = getPathToId(startObject, searchedId, ids);
		return path;
	}

	protected static List<String> getPathToId(final Object startObject, final String searchedId,
			final HashSet<String> ids) {
		if (!isClassOfInterest(startObject.getClass()))
			return null;
		final String id = getId(startObject);
		final String name = getName(startObject);
		final List<String> result = new LinkedList<String>();
		result.add(name);
		if (searchedId.equals(id))
			return result;
		if (ids.contains(id))
			return null;
		ids.add(id);
		final List<Method> methods = getAllListingMethods(startObject);
		for (final Method method : methods) {
			try {
				final List<?> objects = (List<?>) method.invoke(startObject);
				for (final Object object : objects) {
					final List<String> path = getPathToId(object, searchedId, ids);
					if (path != null && path.size() > 0) {
						result.add("->" + method.getName() + "()");
						result.addAll(path);
						return result;
					}
				}
			} catch (final Throwable t) {

			}
		}
		return null;
	}

	protected static boolean hasGetIdMethod(final Class<?> clazz) {
		try {
			final Method method = clazz.getMethod("getId");
			if (method == null)
				return false;
			if (!method.getReturnType().equals(String.class))
				return false;
			return true;
		} catch (final Throwable t) {
			return false;
		}
	}

	protected static boolean hasGetNameMethod(final Class<?> clazz) {
		try {
			final Method method = clazz.getMethod("getName");
			if (method == null)
				return false;
			if (!method.getReturnType().equals(String.class))
				return false;
			return true;
		} catch (final Throwable t) {
			return false;
		}
	}

	protected static boolean isClassOfInterest(final Class<?> clazz) {
		return hasGetIdMethod(clazz) && hasGetNameMethod(clazz);
	}

	protected static boolean isClassOfListType(final Class<?> clazz) {
		return List.class.isAssignableFrom(clazz);
	}
}
