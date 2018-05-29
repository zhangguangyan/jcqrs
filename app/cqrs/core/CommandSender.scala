package cqrs.core

trait CommandSender {
	def send[T <: Command](command: T): Unit
}
