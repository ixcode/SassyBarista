package net.quenchnetworks.sassybarista.sass.value;

import java.util.*;
import java.io.Serializable;

public class DimensionPropertyValue implements IPropertyValue, Serializable
{
    private String value;

    public DimensionPropertyValue()
    {
        this.value = null;
    }
    
    public DimensionPropertyValue(String value)
    {
        this.value = value;
    }
    
    @Override
    public String serialize(Map<String, IPropertyValue> variables)
    {
        return toString();
    }
    
    @Override
    public String toString()
    {
        return value;
    }
}