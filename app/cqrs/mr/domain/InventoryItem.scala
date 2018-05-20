package cqrs.mr.domain

import java.util.UUID

import org.apache.commons.lang.StringUtils

import cqrs.core.AggregateRoot
import cqrs.mr.events.InventoryItemCreated
import cqrs.mr.events.InventoryItemDeactivated
import cqrs.mr.events.InventoryItemRenamed
import cqrs.mr.events.ItemsCheckedInToInventory
import cqrs.mr.events.ItemsRemovedFromInventory

class InventoryItem extends AggregateRoot {
  private var name: String = null
  private var currentCount: Int = 0
  private var activated: Boolean = true

  def this(id: UUID, name: String) {
    this()
    applyChange(new InventoryItemCreated(id, name))
  }

  def changeName(newName: String) {
    if (StringUtils.isEmpty(newName)) throw new IllegalArgumentException("newName")
    applyChange(new InventoryItemRenamed(id, newName))
  }

  def remove(count: Int) {
    if (count <= 0) throw new InvalidOperationException("cant remove negative count from inventory")
    val left = currentCount - count
    applyChange(new ItemsRemovedFromInventory(id, left))
  }

  def checkIn(count: Int) {
    if (count <= 0) throw new InvalidOperationException("must have a count greater than 0 to add to inventory")
    val totalCount = currentCount + count
    applyChange(new ItemsCheckedInToInventory(id, totalCount))
  }

  def deactivate() {
    if (!activated) throw new InvalidOperationException("already deactivated")
    applyChange(new InventoryItemDeactivated(id))
  }

  /* state change */
  protected def apply(e: InventoryItemCreated) {
    this.id = e.id
    this.name = e.name
    this.activated = true
  }

  protected def apply(e: InventoryItemRenamed) {
    this.id = e.id
    this.name = e.newName
  }

  def apply(e: ItemsRemovedFromInventory) {
    this.id = e.id
    this.currentCount = e.count
  }

  protected def apply(e: ItemsCheckedInToInventory) {
    this.id = e.id
    this.currentCount = e.count
  }

  protected def apply(e: InventoryItemDeactivated) {
    this.id = e.id
    this.activated = false
  }
}
