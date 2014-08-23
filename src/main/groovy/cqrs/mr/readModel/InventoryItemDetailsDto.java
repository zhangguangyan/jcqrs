package cqrs.mr.readModel;

import java.util.UUID;

public class InventoryItemDetailsDto {
	public UUID id;
	public String name;
	public int currentCount;
	public int version;

	public InventoryItemDetailsDto(UUID id, String name, int currentCount,
			int version) {
		this.id = id;
		this.name = name;
		this.currentCount = currentCount;
		this.version = version;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public int getVersion() {
		return version;
	}
}
