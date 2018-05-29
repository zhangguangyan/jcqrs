package cqrs.core.bus

import cqrs.core.Command
import cqrs.core.CommandSender
import cqrs.core.Handler
import cqrs.core.Message

import java.util.ArrayList
import java.util.HashMap
import java.util.List

class CommandBus extends CommandSender {
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

  def send[T <: Command](command: T): Unit = {
    val handlers = routes.get(command.getClass()).asInstanceOf[List[Handler[T]]]
    if (handlers != null) {
      if (handlers.size() != 1) throw new RuntimeException("cannot send to more than one handler")
      val handler = handlers.get(0)
      handler.handle(command)
    } else {
      throw new RuntimeException("no handler registered")
    }
  }
}