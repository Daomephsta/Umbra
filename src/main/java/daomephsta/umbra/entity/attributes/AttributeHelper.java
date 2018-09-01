package daomephsta.umbra.entity.attributes;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public class AttributeHelper
{
	public static AttributeModifier createModifier(UUID id, String name, double amount, AttributeModifierOperation operation)
	{
		return new AttributeModifier(id, name, amount, operation.getOperation());
	}
	
	public static AttributeModifier createModifier(String name, double amount, AttributeModifierOperation operation)
	{
		return new AttributeModifier(name, amount, operation.getOperation());
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
