package cqrs.core.bus

import cqrs.core.{Event, EventPublisher, Handler, Message}
import java.util.ArrayList
import java.util.HashMap
import java.util.List

class EventBus extends EventPublisher {
  private val routes = new HashMap[Class[_], List[Handler[_]]]

  def registerHandler[T <: Message](typ: Class[T], handler: Handler[T]): Unit = {
    var handlers = routes.get(typ)

    if (handlers == null) {
      handlers = new ArrayList[Handler[_]]()
      handlers.add(handler)
      routes.put(typ, handlers)
    } else {
      handlers.add(handler)
    }
  }

  def publish[T <: Event](event: T): Unit = {
    val handlers = routes.get(event.getClass()).asInstanceOf[List[Handler[T]]]

    if (handlers == null) return

    handlers.forEach { handler =>
      handler.handle(event)
    }
  }
}
