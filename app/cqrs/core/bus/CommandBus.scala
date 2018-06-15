package cqrs.core.bus

import cqrs.core.{Command, CommandSender, Handler}

class CommandBus extends AbstractBus with CommandSender {
  def send[T <: Command](command: T): Unit = {
     routes.get(command.getClass()) match {
      case Some(handlers) =>
        if (handlers.size > 1) throw new RuntimeException("cannot send to more than one handler")
        val handler = handlers.asInstanceOf[List[Handler[T]]].head
        handler.handle(command)
      case None =>
        throw new RuntimeException("no handler registered")
    }
  }
}