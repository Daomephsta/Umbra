package daomephsta.umbra.item.stack;

import net.minecraft.item.ItemStack;

public class StackEquality
{
    public static final int 
        CHECK_ITEM =     0b00001,
        CHECK_METADATA = 0b00010,
        CHECK_COUNT =    0b00100,
        CHECK_NBT =      0b01000;

    public static boolean check(ItemStack stackA, ItemStack stackB, int criteria)
    {
        if ((criteria & CHECK_ITEM) != 0 && stackA.getItem() != stackB.getItem())
            return false;
        if ((criteria & CHECK_METADATA) != 0 && stackA.getMetadata() != stackB.getMetadata())
            return false;
        if ((criteria & CHECK_COUNT) != 0 && stackA.getCount() != stackB.getCount())
            return false;
        if ((criteria & CHECK_NBT) != 0 && !ItemStack.areItemStackTagsEqual(stackA, stackB))
            return false;
        return true;
    }
}
