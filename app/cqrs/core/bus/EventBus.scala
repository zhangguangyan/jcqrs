package cqrs.core.bus

import cqrs.core.{Event, EventPublisher, Handler}

class EventBus extends AbstractBus with EventPublisher {
  def publish[T <: Event](event: T): Unit = {
    routes.get(event.getClass()) match {
      case Some(handlers) =>
        val hs = handlers.asInstanceOf[List[Handler[T]]]
        hs.foreach { handler =>
          handler.handle(event)
        }
      case None =>
    }
  }
}
