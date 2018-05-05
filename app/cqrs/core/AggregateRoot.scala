package cqrs.core

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.ArrayList
import java.util.List
import java.util.UUID

abstract class AggregateRoot {
	private val changes = new ArrayList[Event]()

	var id: UUID // public abstract Guid Id { get; }
	var version: Int = 0 // { get; internal set; }

	def getUncommittedChanges(): List[Event] = changes

	def markChangesAsCommitted(): Unit = {
		changes.clear()
	}

	def loadsFromHistory(history: List[Event]): Unit = {
    history.forEach { e: Event =>
      applyChange(e,false)
    }
	}

	def applyChange(event:Event): Unit = {
		applyChange(event, true)
	}

	private def applyChange(event: Event, isNew: Boolean): Unit = {
		try {
			val m: Method = getClass().getDeclaredMethod("apply", event.getClass())
			m.setAccessible(true)
			m.invoke(this, event)
			
			if (isNew) {
        changes.add(event)
      }
		} catch {
			case e: NoSuchMethodException =>
				throw new RuntimeException(e)
			case e: IllegalArgumentException =>
        throw new RuntimeException(e)
			case e: IllegalAccessException =>
			  throw new RuntimeException(e)
      case e: InvocationTargetException =>
        throw new RuntimeException(e)
		}
	}
}
