package dao;

import model.InventoryTransaction;

import java.util.List;

public class InventoryTransactionDao implements BaseDao<InventoryTransaction> {
    @Override
    public void save(InventoryTransaction inventoryTransaction) {

    }

    @Override
    public void update(InventoryTransaction inventoryTransaction) {

    }

    @Override
    public void delete(InventoryTransaction inventoryTransaction) {

    }

    @Override
    public InventoryTransaction findById(int id) {
        return null;
    }

    @Override
    public List<InventoryTransaction> getAll() {
        return List.of();
    }
}
