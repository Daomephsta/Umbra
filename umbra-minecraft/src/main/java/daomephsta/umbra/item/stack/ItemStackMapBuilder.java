package daomephsta.umbra.item.stack;

import java.util.Map;
import java.util.Objects;

import daomephsta.umbra.item.stack.ItemStackMapBuilderStages.*;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.item.ItemStack;

public class ItemStackMapBuilder implements ChooseLinked, ChooseLoadFactor, ChooseValueType
{
    private final Hash.Strategy<ItemStack> strategy;
    private float loadFactor = Float.NaN;
    private boolean linked = false;

    private ItemStackMapBuilder(Strategy<ItemStack> strategy)
    {
        this.strategy = strategy;
    }

    public static  ChooseLinked criteria(int criteria)
    {
        return new ItemStackMapBuilder(new ItemStackStrategy(criteria));
    }
    
    @Override
    public ChooseLoadFactor linked()
    {
        this.linked = true;
        return this;
    }
    
    @Override
    public ChooseLoadFactor nonlinked()
    {
        this.linked = false;
        return this;
    }

    @Override
    public ChooseValueType defaultLoadFactor()
    {
        return loadFactor(Hash.DEFAULT_LOAD_FACTOR);
    }
    
    @Override
    public ChooseValueType loadFactor(float loadFactor)
    {
        this.loadFactor = loadFactor;
        return this;
    }

    @Override
    public <V> FinalStageObjectValued<V> objectValued()
    {
        return objectValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public <V> FinalStageObjectValued<V> objectValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2ObjectLinkedOpenCustomHashMap<ItemStack, V>(expectedSize, loadFactor, strategy)
            : new Object2ObjectOpenCustomHashMap<ItemStack, V>(expectedSize, loadFactor, strategy);
    }

    @Override
    public <V> FinalStageObjectValued<V> objectValued(Map<? extends ItemStack, V> source)
    {
        return () -> linked 
            ? new Object2ObjectLinkedOpenCustomHashMap<ItemStack, V>(source, loadFactor, strategy)
            : new Object2ObjectOpenCustomHashMap<ItemStack, V>(source, loadFactor, strategy);
    }

    @Override
    public <V> FinalStageObjectValued<V> objectValued(ItemStack[] keys, V[] values)
    {
        return () -> linked 
            ? new Object2ObjectLinkedOpenCustomHashMap<ItemStack, V>(keys, values, loadFactor, strategy)
            : new Object2ObjectOpenCustomHashMap<ItemStack, V>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageLongValued longValued()
    {
        return longValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageLongValued longValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2LongLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2LongOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageLongValued longValued(Object2LongMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2LongLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2LongOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageLongValued longValued(Map<? extends ItemStack, ? extends Long> source)
    {
        return () -> linked 
            ? new Object2LongLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2LongOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageLongValued longValued(ItemStack[] keys, long[] values)
    {
        return () -> linked 
            ? new Object2LongLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2LongOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageIntValued intValued()
    {
        return intValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageIntValued intValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2IntLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2IntOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageIntValued intValued(Object2IntMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2IntLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2IntOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageIntValued intValued(Map<? extends ItemStack, ? extends Integer> source)
    {
        return () -> linked 
            ? new Object2IntLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2IntOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageIntValued intValued(ItemStack[] keys, int[] values)
    {
        return () -> linked 
            ? new Object2IntLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2IntOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageShortValued shortValued()
    {
        return shortValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageShortValued shortValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2ShortLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2ShortOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageShortValued shortValued(Object2ShortMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2ShortLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2ShortOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageShortValued shortValued(Map<? extends ItemStack, ? extends Short> source)
    {
        return () -> linked 
            ? new Object2ShortLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2ShortOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageShortValued shortValued(ItemStack[] keys, short[] values)
    {
        return () -> linked 
            ? new Object2ShortLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2ShortOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageCharValued charValued()
    {
        return charValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageCharValued charValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2CharLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2CharOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageCharValued charValued(Object2CharMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2CharLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2CharOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageCharValued charValued(Map<? extends ItemStack, ? extends Character> source)
    {
        return () -> linked 
            ? new Object2CharLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2CharOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageCharValued charValued(ItemStack[] keys, char[] values)
    {
        return () -> linked 
            ? new Object2CharLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2CharOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageByteValued byteValued()
    {
        return byteValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageByteValued byteValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2ByteLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2ByteOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageByteValued byteValued(Object2ByteMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2ByteLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2ByteOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageByteValued byteValued(Map<? extends ItemStack, ? extends Byte> source)
    {
        return () -> linked 
            ? new Object2ByteLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2ByteOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageByteValued byteValued(ItemStack[] keys, byte[] values)
    {
        return () -> linked 
            ? new Object2ByteLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2ByteOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageBooleanValued booleanValued()
    {
        return booleanValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageBooleanValued booleanValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2BooleanLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2BooleanOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageBooleanValued booleanValued(Object2BooleanMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2BooleanLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2BooleanOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageBooleanValued booleanValued(Map<? extends ItemStack, ? extends Boolean> source)
    {
        return () -> linked 
            ? new Object2BooleanLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2BooleanOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageBooleanValued booleanValued(ItemStack[] keys, boolean[] values)
    {
        return () -> linked 
            ? new Object2BooleanLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2BooleanOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageFloatValued floatValued()
    {
        return floatValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageFloatValued floatValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2FloatLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2FloatOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageFloatValued floatValued(Object2FloatMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2FloatLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2FloatOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageFloatValued floatValued(Map<? extends ItemStack, ? extends Float> source)
    {
        return () -> linked 
            ? new Object2FloatLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2FloatOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageFloatValued floatValued(ItemStack[] keys, float[] values)
    {
        return () -> linked 
            ? new Object2FloatLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2FloatOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }

    @Override
    public FinalStageDoubleValued doubleValued()
    {
        return doubleValued(Hash.DEFAULT_INITIAL_SIZE);
    }

    @Override
    public FinalStageDoubleValued doubleValued(int expectedSize)
    {
        return () -> linked 
            ? new Object2DoubleLinkedOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy)
            : new Object2DoubleOpenCustomHashMap<ItemStack>(expectedSize, loadFactor, strategy);
    }

    @Override
    public FinalStageDoubleValued doubleValued(Object2DoubleMap<? extends ItemStack> source)
    {
        return () -> linked 
            ? new Object2DoubleLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2DoubleOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageDoubleValued doubleValued(Map<? extends ItemStack, ? extends Double> source)
    {
        return () -> linked 
            ? new Object2DoubleLinkedOpenCustomHashMap<ItemStack>(source, loadFactor, strategy)
            : new Object2DoubleOpenCustomHashMap<ItemStack>(source, loadFactor, strategy);
    }

    @Override
    public FinalStageDoubleValued doubleValued(ItemStack[] keys, double[] values)
    {
        return () -> linked 
            ? new Object2DoubleLinkedOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy)
            : new Object2DoubleOpenCustomHashMap<ItemStack>(keys, values, loadFactor, strategy);
    }
    
    private static class ItemStackStrategy implements Hash.Strategy<ItemStack>
    {
        private final int criteria;
        
        private ItemStackStrategy(int criteria)
        {
            this.criteria = criteria;
        }

        @Override
        public int hashCode(ItemStack o)
        {
            final int prime = 31;
            int result = 1;
            if ((criteria & StackEquality.CHECK_ITEM) != 0)
                result = prime * result + o.getItem().hashCode();
            if ((criteria & StackEquality.CHECK_METADATA) != 0)
                result = prime * result + Objects.hashCode(o.getMetadata());
            if ((criteria & StackEquality.CHECK_COUNT) != 0)
                result = prime * result + Objects.hashCode(o.getCount());
            if ((criteria & StackEquality.CHECK_NBT) != 0)
                result = prime * result + Objects.hashCode(o.getTagCompound());
            return result;
        }

        @Override
        public boolean equals(ItemStack a, ItemStack b)
        {
            return StackEquality.check(a, b, criteria);
        }   
    }
}
