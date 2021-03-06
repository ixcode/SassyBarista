package net.quenchnetworks.sassybarista.sass.expression;

import java.util.*;

import net.quenchnetworks.sassybarista.sass.*;
import net.quenchnetworks.sassybarista.sass.eval.*;
import net.quenchnetworks.sassybarista.sass.value.*;

public class AdditionNode extends AbstractNode
{
    public AdditionNode()
    {
    }
    
    @Override
    public IPropertyValue visit(NodeVisitor visitor)
    throws EvaluationException
    {
        return visitor.visitAddition(this);
    }
    
    @Override
    public INode copy()
    {
        AdditionNode newNode = new AdditionNode();
        newNode.setLeftNode(getLeftNode().copy());
        newNode.setRightNode(getRightNode().copy());
        
        return newNode;
    }
    
    @Override
    public String toString()
    {
        return getLeftNode() + " + " + getRightNode();
    }
}
