package cqrs.mr.readModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cqrs.mr.readModel.listview.InventoryItemDetailsDto;
import cqrs.mr.readModel.listview.InventoryItemListDto;

public class BullShitDatabase {
    public static HashMap<UUID, InventoryItemDetailsDto> details = new HashMap<UUID,InventoryItemDetailsDto>();
    public static List<InventoryItemListDto> list = new ArrayList<InventoryItemListDto>();
}
