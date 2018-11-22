package daomephsta.umbra.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class SRGReflectionHelper
{
	private static final boolean IS_DEV = Launch.blackboard.containsKey("fml.deobfuscatedEnvironment");
	
    public static Field findField(Class<?> clazz, String srgName)
    {
    	if (!IS_DEV) return ReflectionHelper.findField(clazz, srgName);
    	String obfClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap(clazz.getName().replace('.', '/'));
		String deobfFieldName = FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(obfClassName, srgName, null);
		return ReflectionHelper.findField(clazz, deobfFieldName);
    }

    @SuppressWarnings("unchecked")
	public static <T, E> T getPrivateValue(Class <? super E > classToAccess, E instance, String srgName)
    {
    	if (!IS_DEV) return ReflectionHelper.getPrivateValue(classToAccess, instance, srgName);
    	try
		{
			return (T) findField(classToAccess, srgName).get(instance);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new ReflectionHelper.UnableToAccessFieldException(new String[] {srgName}, e); 
		}
    }

    public static <T, E> void setPrivateValue(Class <? super T > classToAccess, T instance, E value, String srgName)
    {
    	if (!IS_DEV) ReflectionHelper.setPrivateValue(classToAccess, instance, value, srgName);
    	try
		{
			findField(classToAccess, srgName).set(instance, value);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new ReflectionHelper.UnableToAccessFieldException(new String[] {srgName}, e); 
		}
    }
    
    @Nonnull
    public static Method findMethod(@Nonnull Class<?> clazz, @Nonnull String srgName, Class<?> returnType, Class<?>... parameterTypes)
    {
    	String methodName = srgName;
    	if (IS_DEV)
    	{
    		String obfClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap(clazz.getName().replace('.', '/'));
    		String desc = createMethodDescription(returnType, parameterTypes);
    		methodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(obfClassName, srgName, desc);
    	}
    	try
		{
			Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
			method.setAccessible(true);
    		return method;
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			throw new ReflectionHelper.UnableToFindMethodException(new String[] {methodName}, e);
		}
    }
    
    private static String createMethodDescription(Class<?> returnType, Class<?>... parameterTypes)
    {
    	StringBuilder desc = new StringBuilder();
		// Parameters
		desc.append('(');
		for(Class<?> parameterType : parameterTypes)
		{
			desc.append(getTypeString(parameterType));
		}
		desc.append(')');
		// Return Type
		desc.append(getTypeString(returnType));
		return desc.toString();
    }
	
	private static String getTypeString(Class<?> type)
	{
		StringBuilder typeString = new StringBuilder();
		if(type.isArray()) typeString.append(type.getName().replace('.', '/'));
		if (!type.isPrimitive()) typeString.append('L').append(type.getName().replace('.', '/')).append(';');		
		else if (type == boolean.class) typeString.append('Z');
		else if (type == char.class) typeString.append('C');
		else if (type == byte.class) typeString.append('B');
		else if (type == short.class) typeString.append('S');
		else if (type == int.class) typeString.append('I');
		else if (type == long.class) typeString.append('J');
		else if (type == float.class) typeString.append('F');
		else if (type == double.class) typeString.append('D');
		else if (type == Void.TYPE) typeString.append('V');
		else throw new RuntimeException("Could not create type string for " + type);
		return typeString.toString();
	}
}
