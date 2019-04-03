package daomephsta.umbra.item.stack;

import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2ByteMap;
import it.unimi.dsi.fastutil.objects.Object2CharMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import net.minecraft.item.ItemStack;

public class ItemStackMapBuilderStages
{
    public interface ChooseLinked
    {
        public ChooseLoadFactor nonlinked();
        
        public ChooseLoadFactor linked();
    }
    
    public interface ChooseLoadFactor
    {
        public ChooseValueType defaultLoadFactor();
        
        public ChooseValueType loadFactor(float loadFactor);
    }
    
    public interface ChooseValueType
    {
        //Object-valued
        
        public <V> FinalStageObjectValued<V> objectValued();
        
        public <V> FinalStageObjectValued<V> objectValued(int expectedSize);
        
        public <V> FinalStageObjectValued<V> objectValued(Map<? extends ItemStack, V> source);
        
        public <V> FinalStageObjectValued<V> objectValued(ItemStack[] keys, V[] values);
        
        //Long-valued
        
        public FinalStageLongValued longValued();
        
        public FinalStageLongValued longValued(int expectedSize);
        
        public FinalStageLongValued longValued(Object2LongMap<? extends ItemStack> source);
        
        public FinalStageLongValued longValued(Map<? extends ItemStack, ? extends Long> source);
        
        public FinalStageLongValued longValued(ItemStack[] keys, long[] values);
        
        //Int-valued
        
        public FinalStageIntValued intValued();
        
        public FinalStageIntValued intValued(int expectedSize);
        
        public FinalStageIntValued intValued(Object2IntMap<? extends ItemStack> source);
        
        public FinalStageIntValued intValued(Map<? extends ItemStack, ? extends Integer> source);
        
        public FinalStageIntValued intValued(ItemStack[] keys, int[] values);
        
        //Short-valued
        
        public FinalStageShortValued shortValued();
        
        public FinalStageShortValued shortValued(int expectedSize);
        
        public FinalStageShortValued shortValued(Object2ShortMap<? extends ItemStack> source);
        
        public FinalStageShortValued shortValued(Map<? extends ItemStack, ? extends Short> source);
        
        public FinalStageShortValued shortValued(ItemStack[] keys, short[] values);
        
        //Char-valued
        
        public FinalStageCharValued charValued();
        
        public FinalStageCharValued charValued(int expectedSize);
        
        public FinalStageCharValued charValued(Object2CharMap<? extends ItemStack> source);
        
        public FinalStageCharValued charValued(Map<? extends ItemStack, ? extends Character> source);
        
        public FinalStageCharValued charValued(ItemStack[] keys, char[] values);
        
        //Byte-valued
        
        public FinalStageByteValued byteValued();
        
        public FinalStageByteValued byteValued(int expectedSize);
        
        public FinalStageByteValued byteValued(Object2ByteMap<? extends ItemStack> source);
        
        public FinalStageByteValued byteValued(Map<? extends ItemStack, ? extends Byte> source);
        
        public FinalStageByteValued byteValued(ItemStack[] keys, byte[] values);
        
        //Boolean-valued
        
        public FinalStageBooleanValued booleanValued();
        
        public FinalStageBooleanValued booleanValued(int expectedSize);
        
        public FinalStageBooleanValued booleanValued(Object2BooleanMap<? extends ItemStack> source);
        
        public FinalStageBooleanValued booleanValued(Map<? extends ItemStack, ? extends Boolean> source);
        
        public FinalStageBooleanValued booleanValued(ItemStack[] keys, boolean[] values);
        
        //Double-valued
        
        public FinalStageDoubleValued doubleValued();
        
        public FinalStageDoubleValued doubleValued(int expectedSize);
        
        public FinalStageDoubleValued doubleValued(Object2DoubleMap<? extends ItemStack> source);
        
        public FinalStageDoubleValued doubleValued(Map<? extends ItemStack, ? extends Double> source);
        
        public FinalStageDoubleValued doubleValued(ItemStack[] keys, double[] values);
        
        //Float-valued
        
        public FinalStageFloatValued floatValued();
        
        public FinalStageFloatValued floatValued(int expectedSize);
        
        public FinalStageFloatValued floatValued(Object2FloatMap<? extends ItemStack> source);
        
        public FinalStageFloatValued floatValued(Map<? extends ItemStack, ? extends Float> source);
        
        public FinalStageFloatValued floatValued(ItemStack[] keys, float[] values);
    }
    
    public interface SetLoadFactor<T>
    {
        public T loadFactor(float loadFactor);
    }
    
    public interface FinalStageObjectValued<V>
    {
        public Map<ItemStack, V> build();
    }
    
    public interface FinalStageLongValued
    {
        public Object2LongMap<ItemStack> build();
    }
    
    public interface FinalStageIntValued
    {
        public Object2IntMap<ItemStack> build();
    }
    
    public interface FinalStageShortValued
    {
        public Object2ShortMap<ItemStack> build();
    }
    
    public interface FinalStageCharValued
    {
        public Object2CharMap<ItemStack> build();
    }
    
    public interface FinalStageByteValued
    {
        public Object2ByteMap<ItemStack> build();
    }
    
    public interface FinalStageBooleanValued
    {
        public Object2BooleanMap<ItemStack> build();
    }
    
    public interface FinalStageDoubleValued
    {
        public Object2DoubleMap<ItemStack> build();
    }
    
    public interface FinalStageFloatValued
    {
        public Object2FloatMap<ItemStack> build();
    }
}
