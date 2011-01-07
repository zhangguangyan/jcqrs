package cqrs.domain;

import java.util.UUID;

import org.hsqldb.lib.StringUtil;

import cqrs.events.InventoryItemCreated;
import cqrs.events.InventoryItemDeactivated;
import cqrs.events.InventoryItemRenamed;
import cqrs.events.ItemsCheckedInToInventory;
import cqrs.events.ItemsRemovedFromInventory;

public class InventoryItem extends AggregateRoot{
	private String name;
	private int currentCount;
    private boolean activated;

	public InventoryItem(UUID id, String name) {
        applyChange(new InventoryItemCreated(id, name));
	}

	public void changeName(String newName) {
        if (StringUtil.isEmpty(newName)) throw new IllegalArgumentException("newName");
        applyChange(new InventoryItemRenamed(id, newName));
	}
	
    public void remove(int count){
        if (count <= 0) throw new InvalidOperationException("cant remove negative count from inventory");
        int left = currentCount - count;
        applyChange(new ItemsRemovedFromInventory(id, left));
    }

    public void checkIn(int count){
        if(count <= 0) throw new InvalidOperationException("must have a count greater than 0 to add to inventory");
        int totalCount = currentCount + count;
        applyChange(new ItemsCheckedInToInventory(id, totalCount));
    }

    public void deactivate(){
        if(!activated) throw new InvalidOperationException("already deactivated");
        applyChange(new InventoryItemDeactivated(id));
    }

    /* state change */
    protected void apply(InventoryItemCreated e) {
        this.id = e.id;
        this.name = e.name;
        this.activated = true;
    }
    
    protected void apply(InventoryItemRenamed e) {
        this.id = e.id;
        this.name = e.newName;
    }
    
    protected void apply(ItemsRemovedFromInventory e) {
        this.id = e.id;
        this.currentCount = e.count;
    }
    
    protected void apply(ItemsCheckedInToInventory e) {
        this.id = e.id;
        this.currentCount = e.count;
    }

    protected void apply(InventoryItemDeactivated e) {
    	this.id = e.id;
        activated = false;
    }

    public InventoryItem() {
        // used to create in repository ... many ways to avoid this, eg making private constructor
    }
}
