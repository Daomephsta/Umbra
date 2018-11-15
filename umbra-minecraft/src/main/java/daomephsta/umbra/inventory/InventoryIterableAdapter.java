package daomephsta.umbra.inventory;

import java.util.Iterator;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class InventoryIterableAdapter implements Iterable<ItemStack>, Iterator<ItemStack>
{
	private final IItemHandler itemHandler;
	private int currentSlot = 0;

	public InventoryIterableAdapter(IInventory inventory)
	{
		this(new InvWrapper(inventory));
	}
	
	public InventoryIterableAdapter(IItemHandler itemHandler)
	{
		this.itemHandler = itemHandler;
	}

	@Override
	public Iterator<ItemStack> iterator()
	{
		this.currentSlot = 0;
		return this;
	}
	
	@Override
	public boolean hasNext()
	{
		return currentSlot < itemHandler.getSlots();
	}

	@Override
	public ItemStack next()
	{
		ItemStack next = itemHandler.getStackInSlot(currentSlot);
		currentSlot++;
		return next;
	}
	 
	@Override
	public void remove()
	{
		if (itemHandler instanceof IItemHandlerModifiable)
			((IItemHandlerModifiable) itemHandler).setStackInSlot(currentSlot, ItemStack.EMPTY);
		else 
			throw new UnsupportedOperationException("remove() is only supported when iterating over instances of IItemHandlerModifiable");
	}
}