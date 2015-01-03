package cqrs.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AggregateRoot {
	private final List<Event> changes = new ArrayList<Event>();

	public UUID id; // public abstract Guid Id { get; }
	protected int version;// { get; internal set; }

	public List<Event> getUncommittedChanges() {
		return changes;
	}

	public void markChangesAsCommitted() {
		changes.clear();
	}

	public void loadsFromHistory(List<Event> history) {
		for (Event e : history)
			applyChange(e, false);
	}

	protected void applyChange(Event event) {
		applyChange(event, true);
	}

	private void applyChange(Event event, boolean isNew) {
		try {
			Method m = getClass().getDeclaredMethod("apply", event.getClass());
			m.setAccessible(true);
			m.invoke(this, event);
			
			if (isNew)
				changes.add(event);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
