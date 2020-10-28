// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.n7space.capellatasteplugin.utils.GraphLayouter;
import com.n7space.capellatasteplugin.utils.Vector2D;
import com.n7space.capellatasteplugin.utils.Vector2I;

/**
 * Class facilitating element positioning.
 *
 */
public class Layout {

	/**
	 * A layout element.
	 *
	 */
	public static class LayoutElement {
		/**
		 * The object which this layout element represents.
		 */
		public final Object wrappedObject;
		/**
		 * Connections.
		 */
		public final List<LayoutElementConnection> connections = new LinkedList<Layout.LayoutElementConnection>();
		/**
		 * Internal layout for nested elements.
		 */
		public final Layout internalLayout = new Layout();

		private Position position = new Position(0, 0);
		private Size size = new Size(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		/**
		 * The constructor.
		 *
		 * @param objectThatIsWrapped
		 *            Object to be represented by this element
		 */
		public LayoutElement(final Object objectThatIsWrapped) {
			wrappedObject = objectThatIsWrapped;
		}

		/**
		 * Gets element position.
		 *
		 * @return Position
		 */
		public Position getPosition() {
			return position;
		}

		/**
		 * Gets element size.
		 *
		 * @return Size
		 */
		public Size getSize() {
			return size;
		}

		/**
		 * Moves the element to a new position, offsetting the internal layout.
		 *
		 * @param newPosition
		 *            New element position
		 */
		public void SetPosition(final Position newPosition) {
			final int dx = newPosition.x - position.x;
			final int dy = newPosition.y - position.y;
			internalLayout.applyOffset(dx, dy);
			position = newPosition;
		}

		/**
		 * Sets element size.
		 *
		 * @param newSize
		 *            New element size
		 */
		public void setSize(final Size newSize) {
			size = newSize;
		}
	}

	/**
	 * Class representing a connection between elements within the layout.
	 *
	 */
	public static class LayoutElementConnection {
		/**
		 * Object that this connection represents.
		 */
		public final Object wrappedObject;
		/**
		 * Position of source attachment.
		 */
		public Position sourceAttachmentPosition = new Position(0, 0);
		/**
		 * Position of target attachment.
		 */
		public Position targetAttachmentPosition = new Position(0, 0);
		/**
		 * Path between the source and target attachment position.
		 */
		public final ArrayList<Position> path = new ArrayList<>();
		/**
		 * Source of this connection.
		 */
		public final LayoutElement source;
		/**
		 * Target of this connection.
		 */
		public final LayoutElement target;

		/**
		 * A constructor.
		 *
		 * @param objectThatIsWrapped
		 *            Object to be represented by this connection.
		 * @param sourceElement
		 *            Source element
		 * @param targetElement
		 *            Target elemeent
		 */
		public LayoutElementConnection(final Object objectThatIsWrapped, final LayoutElement sourceElement,
				final LayoutElement targetElement) {
			wrappedObject = objectThatIsWrapped;
			source = sourceElement;
			target = targetElement;
		}
	}

	/**
	 * Enumeration listing possible layout types.
	 *
	 */
	public static enum LayoutType {
		/**
		 * A free graph layout
		 */
		GRAPH,
		/**
		 * Vertical list layout
		 */
		VERTICAL_LIST
	}

	/**
	 * 2D position.
	 *
	 */
	public static class Position extends Vector2I {

		/**
		 * A constructor.
		 *
		 * @param initialX
		 *            Initial X coordinate
		 * @param initialY
		 *            Initial Y coordinate
		 */
		public Position(final int initialX, final int initialY) {
			super(initialX, initialY);
		}

		/**
		 * Gets the distance to the other position.
		 *
		 * @param other
		 *            Other position
		 * @return Distance
		 */
		double getDistanceTo(final Position other) {
			final double a = other.x - x;
			final double b = other.y - y;
			return Math.sqrt(a * a + b * b);
		}

	}

	/**
	 * 2D size.
	 *
	 */
	public static class Size extends Vector2I {

		/**
		 * A constructor.
		 *
		 * @param initialX
		 *            Initial width
		 * @param initialY
		 *            Initial height
		 */
		public Size(final int initialX, final int initialY) {
			super(initialX, initialY);
		}
	}

	protected static final int DEFAULT_WIDTH = 400;
	protected static final int DEFAULT_HEIGHT = 100;
	protected final List<LayoutElement> elements = new LinkedList<Layout.LayoutElement>();
	protected final List<LayoutElementConnection> connections = new LinkedList<Layout.LayoutElementConnection>();
	protected final LayoutType type;

	/**
	 * The default constructor.
	 */
	public Layout() {
		type = LayoutType.VERTICAL_LIST;
	}

	/**
	 * A constructor.
	 *
	 * @param layoutType
	 *            Type of the layout
	 */
	public Layout(final LayoutType layoutType) {
		type = layoutType;
	}

	/**
	 * Adds connection to the layout.
	 *
	 * @param connection
	 *            Connection to be added.
	 */
	public void addConnection(final LayoutElementConnection connection) {
		connections.add(connection);
	}

	/**
	 * Adds element to the layout.
	 *
	 * @param element
	 *            Element to be added.
	 */
	public void addElement(final LayoutElement element) {
		elements.add(element);
	}

	protected void adjustBounds() {
		final Position[] bounds = getBounds();
		final int minX = bounds[0].x;
		final int minY = bounds[0].y;
		applyOffset(-minX, -minY);

	}

	/**
	 * Applies offset to the layout and all its elements.
	 *
	 * @param x
	 *            X offset
	 * @param y
	 *            Y offset
	 */
	public void applyOffset(final int x, final int y) {
		for (final LayoutElement element : elements) {
			element.SetPosition(new Position(element.position.x + x, element.position.y + y));
		}
		for (final LayoutElementConnection connection : connections) {
			connection.sourceAttachmentPosition = new Position(connection.sourceAttachmentPosition.x + x,
					connection.sourceAttachmentPosition.y + y);
			connection.targetAttachmentPosition = new Position(connection.targetAttachmentPosition.x + x,
					connection.targetAttachmentPosition.y + y);
		}
	}

	protected void calculateElementSizes() {
		for (final LayoutElement element : elements) {
			if (!element.internalLayout.isEmpty()) {
				element.internalLayout.generateLayout();
				final Position[] bounds = element.internalLayout.getBounds();
				final int sizeX = bounds[1].x - bounds[0].x;
				final int sizeY = bounds[1].y - bounds[0].y;
				element.size = new Size(sizeX, sizeY);
			}
		}
	}

	protected void generateConnectionPaths() {
		for (final LayoutElementConnection connection : connections) {
			connection.path.clear();
			if (connection.sourceAttachmentPosition.x == connection.targetAttachmentPosition.x) {
				connection.path.add(connection.sourceAttachmentPosition);
				connection.path.add(connection.targetAttachmentPosition);
			} else if (connection.sourceAttachmentPosition.y == connection.targetAttachmentPosition.y) {
				connection.path.add(connection.sourceAttachmentPosition);
				connection.path.add(connection.targetAttachmentPosition);
			} else {
				final int middleY = (connection.sourceAttachmentPosition.y + connection.targetAttachmentPosition.y) / 2;
				connection.path.add(connection.sourceAttachmentPosition);
				connection.path.add(new Position(connection.sourceAttachmentPosition.x, middleY));
				connection.path.add(new Position(connection.targetAttachmentPosition.x, middleY));
				connection.path.add(connection.targetAttachmentPosition);
			}
		}
	}

	/**
	 * Generates layout for the contained elements and connections.
	 */
	public void generateLayout() {
		calculateElementSizes();
		generateLayoutForElements();
		generateLayoutForConnections();
		adjustBounds();
		generateConnectionPaths();
	}

	/**
	 * Gets layout bounds.
	 *
	 * @return The layout bounds
	 */
	public Position[] getBounds() {
		final Position[] bounds = new Position[2];
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (final LayoutElement element : elements) {
			minX = Math.min(minX, element.position.x);
			minY = Math.min(minY, element.position.y);

			maxX = Math.max(maxX, element.position.x + element.size.x);
			maxY = Math.max(maxY, element.position.y + element.size.y);
		}
		bounds[0] = new Position(minX - DEFAULT_WIDTH / 2, minY - DEFAULT_HEIGHT / 2);
		bounds[1] = new Position(maxX + DEFAULT_WIDTH / 2, maxY + DEFAULT_HEIGHT / 2);
		return bounds;
	}

	/**
	 * Gets the connection representing the given object.
	 *
	 * @param wrappedObject
	 *            The object
	 * @return The connection representing the given object
	 */
	public LayoutElementConnection getConnectionForWrappedObject(final Object wrappedObject) {
		for (final LayoutElementConnection connection : connections) {
			if (wrappedObject.equals(connection.wrappedObject)) {
				return connection;
			}
		}
		return null;
	}

	/**
	 * Gets the element representing the given object.
	 *
	 * @param wrappedObject
	 *            The object
	 * @return The element representing the given object
	 */
	public LayoutElement getElementForWrappedObject(final Object wrappedObject) {
		for (final LayoutElement element : elements) {
			if (wrappedObject.equals(element.wrappedObject)) {
				return element;
			}
			final LayoutElement internalElement = element.internalLayout.getElementForWrappedObject(wrappedObject);
			if (internalElement != null)
				return internalElement;
		}
		return null;
	}

	/**
	 * Gets the position of the element representing the given object.
	 *
	 * @param wrappedObject
	 *            The object
	 * @return Position
	 */

	public Position getPositionOfWrappedObject(final Object wrappedObject) {
		final LayoutElement element = getElementForWrappedObject(wrappedObject);
		return element.getPosition();
	}

	/**
	 * Gets the size of the element representing the given object.
	 *
	 * @param wrappedObject
	 *            The object
	 * @return Size
	 */
	public Size getSizeOfWrappedObject(final Object wrappedObject) {
		final LayoutElement element = getElementForWrappedObject(wrappedObject);
		return element.getSize();
	}

	/**
	 * Is layout empty.
	 *
	 * @return Whether the layout is empty
	 */
	public boolean isEmpty() {
		return elements.size() == 0;
	}

	protected void generateLayoutForConnections() {
		for (final LayoutElementConnection connection : connections) {
			final Vector2D targetPosition = new Vector2D(connection.target.position.x, connection.target.position.y);
			final Vector2D sourcePosition = new Vector2D(connection.source.position.x, connection.source.position.y);
			final Vector2D sourceSize = new Vector2D(connection.source.size.x, connection.source.size.y);
			final Vector2D targetSize = new Vector2D(connection.target.size.x, connection.target.size.y);

			final Vector2D toTarget = targetPosition.subtract(sourcePosition);
			final Vector2D sourceAttachmentPosition = toTarget.intersectionWithRectangle(sourcePosition, sourceSize);
			final Vector2D targetAttachmentPosition = toTarget.scale(-1).intersectionWithRectangle(targetPosition,
					targetSize);
			connection.sourceAttachmentPosition.x = (int) sourceAttachmentPosition.x;
			connection.sourceAttachmentPosition.y = (int) sourceAttachmentPosition.y;
			connection.targetAttachmentPosition.x = (int) targetAttachmentPosition.x;
			connection.targetAttachmentPosition.y = (int) targetAttachmentPosition.y;
		}
	}

	protected void generateLayoutForElements() {
		switch (type) {
		case GRAPH:
			final GraphLayouter layouter = new GraphLayouter();
			for (final LayoutElement element : elements) {
				layouter.addVertex(element, element.size.x * element.size.y);
			}
			for (final LayoutElementConnection connection : connections) {
				layouter.addEdge(connection.source, connection.target);
			}
			layouter.calculateLayout();
			for (final LayoutElement element : elements) {
				final Vector2D position = layouter.getVertexPosition(element);
				element.SetPosition(new Position((int) position.x, (int) position.y));
			}
			break;
		case VERTICAL_LIST:
			int offset = 0;
			for (final LayoutElement element : elements) {
				element.SetPosition(new Position(0, offset));
				offset += element.size.y + (DEFAULT_HEIGHT / 2);
			}
			break;
		default:
			break;

		}
	}

}
