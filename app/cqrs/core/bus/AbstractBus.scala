package cqrs.core.bus

import cqrs.core.{Handler, Message}

import scala.collection.mutable

class AbstractBus {
  protected val routes = mutable.Map.empty[Class[_], List[Handler[_]]]

  def registerHandler[T <: Message](typ: Class[T], handler: Handler[T]): Unit = {
     routes.get(typ) match {
      case None =>
        routes += (typ -> List(handler))
      case Some(handlers) =>
        routes += (typ -> (handler::handlers))
    }
  }
}
