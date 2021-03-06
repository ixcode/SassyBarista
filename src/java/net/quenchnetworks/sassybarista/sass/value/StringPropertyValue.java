package net.quenchnetworks.sassybarista.sass.value;

import java.util.*;
import java.math.*;
import java.io.Serializable;

import net.quenchnetworks.sassybarista.sass.*;
import net.quenchnetworks.sassybarista.sass.eval.*;
import net.quenchnetworks.sassybarista.sass.expression.*;
import net.quenchnetworks.sassybarista.sass.value.op.*;

public class StringPropertyValue extends AbstractPropertyValue implements Serializable
{
    private static class AdditionOp extends OpAdapter
    {
        private StringPropertyValue value1;

        public AdditionOp(StringPropertyValue value1)
        {
            super("StringPropertyValue");
            this.value1 = value1;
        }
        
        @Override
        public IPropertyValue op(DefaultPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            String v2 = value2.getValue();
            
            return new DefaultPropertyValue(v2 + v1);
        }
        
        @Override
        public IPropertyValue op(StringPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            String v2 = value2.getValue();
            
            return new StringPropertyValue(v2 + v1, value2.getQuoteType());
        }
        
        @Override
        public IPropertyValue op(NumberPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            BigDecimal v2 = value2.getValue();
            
            return new StringPropertyValue(v2 + v1, value1.getQuoteType());
        }
    }
    
    private static class EqOp extends OpAdapter
    {
        private StringPropertyValue value1;

        public EqOp(StringPropertyValue value1)
        {
            super("StringPropertyValue");
            this.value1 = value1;
        }
        
        @Override
        public IPropertyValue op(StringPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            String v2 = value2.getValue();
            
            return new BooleanPropertyValue(v1.equals(v2));
        }
        
        @Override
        public IPropertyValue op(DefaultPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            String v2 = value2.getValue();
            
            return new BooleanPropertyValue(v1.equals(v2));
        }
    }
    
    private static class NotEqOp extends OpAdapter
    {
        private StringPropertyValue value1;

        public NotEqOp(StringPropertyValue value1)
        {
            super("StringPropertyValue");
            this.value1 = value1;
        }
        
        @Override
        public IPropertyValue op(StringPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            String v2 = value2.getValue();
            
            return new BooleanPropertyValue(!v1.equals(v2));
        }
        
        @Override
        public IPropertyValue op(DefaultPropertyValue value2)
        throws EvaluationException
        {
            String v1 = value1.getValue();
            String v2 = value2.getValue();
            
            return new BooleanPropertyValue(!v1.equals(v2));
        }
    }

    private String value;
    private String quoteType;

    public StringPropertyValue()
    {
        super("StringPropertyValue");
        this.value = null;
    }
    
    public StringPropertyValue(String value, String quoteType)
    {
        super("StringPropertyValue");
        this.value = value;
        this.quoteType = quoteType;
    }
    
    public StringPropertyValue(String value)
    {
        super("StringPropertyValue");
        if (value.startsWith("\"")) {
            quoteType = "\"";
        } 
        else if (value.startsWith("'")) {
            quoteType = "'";
        }
        this.value = value.substring(1, value.length()-1);
    }
    
    public String getValue()
    {
        return value;
    }
    
    public String getQuoteType()
    {
        return quoteType;
    }
    
    @Override
    public IOp getAdditionOp()
    {
        return new AdditionOp(this);
    }
    
    @Override
    public IOp getEqOp()
    {
        return new EqOp(this);
    }
    
    @Override
    public IOp getNotEqOp()
    {
        return new NotEqOp(this);
    }
    
    @Override
    public IPropertyValue callAddOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getAdditionOp();
        return op.op(this);
    }

    @Override
    public IPropertyValue callSubOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getSubtractionOp();
        return op.op(this);
    }

    @Override
    public IPropertyValue callMulOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getMultiplicationOp();
        return op.op(this);
    }
    
    @Override
    public IPropertyValue callDivOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getDivisionOp();
        return op.op(this);
    }
    
    @Override
    public IPropertyValue callEqOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getEqOp();
        return op.op(this);
    }

    @Override
    public IPropertyValue callNotEqOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getNotEqOp();
        return op.op(this);
    }

    @Override
    public IPropertyValue callLtOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getLtOp();
        return op.op(this);
    }
    
    @Override
    public IPropertyValue callGtOp(IPropertyValue node) 
    throws EvaluationException
    {
        IOp op = node.getGtOp();
        return op.op(this);
    }
    
    @Override
    public IPropertyValue copy()
    {
        return new StringPropertyValue(value, quoteType);
    }
    
    @Override
    public String toString()
    {
        return quoteType + value + quoteType;
    }
}
