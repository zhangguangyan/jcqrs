package cqrs.core.bus

import java.util

import cqrs.core.{Event, EventPublisher, Handler, Message}

class EventBus extends EventPublisher {
  private val routes = new util.HashMap[Class[_], util.List[Handler[_]]]

  def registerHandler[T <: Message](typ: Class[T], handler: Handler[T]): Unit = {
    var handlers = routes.get(typ)

    if (handlers == null) {
      handlers = new util.ArrayList[Handler[_]]()
      handlers.add(handler)
      routes.put(typ, handlers)
    } else {
      handlers.add(handler)
    }
  }

  def publish[T <: Event](event: T): Unit = {
    val handlers = routes.get(event.getClass()).asInstanceOf[util.List[Handler[T]]]

    if (handlers == null) return

    handlers.forEach { handler =>
      handler.handle(event)
    }
  }
}
