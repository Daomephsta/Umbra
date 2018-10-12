package daomephsta.umbra.entity.attributes;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Streams;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.Constants.NBT;

public class AttributeHelper
{
	/**
	 * Creates an attribute modifier with a specific UUID
	 * @param id a UUID for the modifier. Must be unique among the modifiers
	 * applied to an item.
	 * @param name a name for the modifier. This is not the attribute name.
	 * @param amount the amount for the modifier. What this means is defined
	 * by the values of the {@code operation} parameter.
	 * @param operation defines how {@code amount} affects an attribute
	 * @return
	 */
	public static AttributeModifier createModifier(UUID id, String name, double amount, AttributeModifierOperation operation)
	{
		return new AttributeModifier(id, name, amount, operation.getOperation());
	}
	
	/**
	 * Creates an attribute modifier with a random UUID
	 * @param name a name for the modifier. This is not the attribute name.
	 * @param amount the amount for the modifier. What this means is defined
	 * by the values of the {@code operation} parameter.
	 * @param operation defines how {@code amount} affects an attribute
	 * @return
	 */
	public static AttributeModifier createModifier(String name, double amount, AttributeModifierOperation operation)
	{
		return new AttributeModifier(name, amount, operation.getOperation());
	}
	 
	private static final String TAG_ATTRIBUTE_MODIFIERS = "AttributeModifiers";
	/**
	 * Adds an attribute modifier to an ItemStack, merging it with an existing modifier 
	 * for the same attribute with the same operation and the same slot, if one exists.
	 * @param stack the stack to add the modifier to
	 * @param attributeName the name of the attribute to modify
	 * @param modifier the modifier to add
	 * @param slot the slot the itemstack must be in to activate the modifier. If null
	 * all slots will activate the modifier
	 */
	public static void addAttributeModifierMerging(ItemStack stack, String attributeName, AttributeModifier modifier, 
		@Nullable EntityEquipmentSlot slot) 
	{	
		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		NBTTagList attributeModifiersTag;
		if (stack.getTagCompound().hasKey(TAG_ATTRIBUTE_MODIFIERS))
			attributeModifiersTag = stack.getTagCompound().getTagList(TAG_ATTRIBUTE_MODIFIERS, NBT.TAG_COMPOUND);
		else
		{
			attributeModifiersTag = new NBTTagList();
			stack.getTagCompound().setTag(TAG_ATTRIBUTE_MODIFIERS, attributeModifiersTag);
		}
		NBTTagCompound modifierNBT = writeAttributeModifierToNBT(slot, attributeName, modifier);
		//Find any existing modifier for the same attribute with the same operation and the same slot 
		Optional<NBTTagCompound> existing = Streams.stream(attributeModifiersTag)
			.map(NBTTagCompound.class::cast)
			.filter(attributeModifierCompound -> shouldMerge(attributeModifierCompound, attributeName, slot, modifier))
			.findFirst();
		//If an existing modifier was found, increase it by modifier's amount
		if (existing.isPresent())
			existing.get().setDouble("Amount", modifier.getAmount() + existing.get().getDouble("Amount"));
		else
			//Add modifier to the attribute modifiers tag
			attributeModifiersTag.appendTag(modifierNBT);
	}
	
	private static NBTTagCompound writeAttributeModifierToNBT(@Nullable EntityEquipmentSlot slot, String name, AttributeModifier modifier)
	{
		NBTTagCompound modifierNBT = SharedMonsterAttributes.writeAttributeModifierToNBT(modifier);
		modifierNBT.setString("AttributeName", name);
		if (slot != null)
			modifierNBT.setString("Slot", slot.getName());
		return modifierNBT;
	}
	
	private static boolean shouldMerge(NBTTagCompound attributeModifierCompound, String attributeName, @Nullable EntityEquipmentSlot slot,
		AttributeModifier modifier)
	{
		boolean sameSlot = (slot == null && attributeModifierCompound.getString("Slot").isEmpty())
			|| (slot != null && attributeModifierCompound.getString("Slot").equals(slot.getName()));
		return attributeModifierCompound.getString("AttributeName").equals(attributeName)
		&& sameSlot
		&& attributeModifierCompound.getInteger("Operation") == modifier.getOperation();
	}
	
	/**Enum representation of {@link net.minecraft.entity.ai.attributes.AttributeModifier AttributeModifier} operations
	 * @see <a href="https://minecraft.gamepedia.com/Attribute#Operations">the vanilla wiki article</a>**/
	public enum AttributeModifierOperation
	{  
		/**The value of the attribute is incremented by the sum of the modifiers**/
		ADDITIVE(0),
		/**The value of the attribute is multiplied by the sum of the modifiers**/
		MULTIPLICATIVE_ADDING(1),
		/**The value of the attribute is multiplied by the product of the modifiers**/
		MULTIPLICATIVE_STACKING(2);
		
		private final int operation;
		
		private AttributeModifierOperation(int operation)
		{
			this.operation = operation;
		}
		
		int getOperation()
		{
			return operation;
		}
	}
}
