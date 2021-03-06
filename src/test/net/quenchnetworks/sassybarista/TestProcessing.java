package net.quenchnetworks.sassybarista;

import net.quenchnetworks.sassybarista.sass.JavaStringInterpolator;
import net.quenchnetworks.sassybarista.sass.ParseException;
import net.quenchnetworks.sassybarista.sass.SassParser;
import net.quenchnetworks.sassybarista.sass.SassSheetSerializer;
import net.quenchnetworks.sassybarista.sass.eval.EvaluationException;
import net.quenchnetworks.sassybarista.sass.eval.SassSheetEvaluator;
import net.quenchnetworks.sassybarista.sass.models.SassSheet;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestProcessing
{
    public void processTest(String testCase)
    {
        try {
            // load reference text
            String cssFile = "testcases/" + testCase + ".css";
           
            BufferedReader reader = new BufferedReader(new FileReader(cssFile));
            StringBuilder refTextBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                refTextBuffer.append(line);
                refTextBuffer.append("\n");
            }
            
            reader.close();
            
            String refText = refTextBuffer.toString();
            refText = refText.trim();
            refText = refText.replace("\r\n","\n");
            refText = refText.replace("\t","");
            refText = refText.replace("    ","");
        
            // load and parse scss
            File scssFile = new File("testcases/" + testCase + ".scss");
        
            SassParser parser = new SassParser(new FileReader(scssFile));
            SassSheet sheet = parser.parse(scssFile.getParentFile());
        
            sheet = sheet.copy();
        
            SassSheetEvaluator evaluator = new SassSheetEvaluator(new JavaStringInterpolator());
            evaluator.evaluate(sheet);
        
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            SassSheetSerializer serializer = new SassSheetSerializer(new PrintStream(os));
            
            serializer.render(sheet);
            
            String processedText = os.toString("UTF-8");
            processedText = processedText.trim();
            processedText = processedText.replace("\r\n","\n");
            processedText = processedText.replace("\t","");
            processedText = processedText.replace("    ","");
            
            if (!refText.equals(processedText)) {
                System.out.println(testCase + " failed.");
                System.out.println("reference text:");
                System.out.print(refText);
                System.out.println("\n");
                
                System.out.println("processed text:");
                System.out.print(processedText);
                System.out.println("\n");
            
                fail();
            } else {
                assertTrue(true);
            }
        }
        catch (ParseException e) {
            System.out.println("ParseException occured for " + testCase);
            e.printStackTrace();
            fail("Caught ParseException.");
        }
        catch (EvaluationException e) {
            e.printStackTrace();
            fail("Caught EvaluationException.");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fail("Caught UnsupportedEncodingException.");
        }
        catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException.");
        }
    }

    @Test
    public void nested_variables() {
        processTest("nested_variables");
    }

    @Test
    public void combination() {
        processTest("combination");
    }

    @Test
    public void built_in_functions() {
        processTest("built_in_functions");
    }

    @Test
    //@Ignore("WIP")
    public void imports()
    {
        processTest("import_css");
        processTest("import_sass");
    }


    
    @Test
    public void nesting()
    {
        processTest("nesting_basic");
        processTest("nesting_permutation");
    }
    
    @Test
    public void mixins()
    {
        processTest("mixin");
        processTest("mixin_parameter");
    }
    
    @Test
    public void variables()
    {
        processTest("variable");
    }

    @Test
    public void selectors()
    {
        processTest("selectors");
    }
    
    @Test 
    public void expressions()
    {
        processTest("expression_arithmetic");
    }
    
    @Test 
    public void inheritance()
    {
        processTest("extend_basic");
        processTest("extend_multiple");
        processTest("extend_chain");
        processTest("extend_complex");
    }
    
    @Test 
    public void controlstructures()
    {
        processTest("controlstructure_if");
    }

    @Test 
    public void parentReferences()
    {
        processTest("parentref");
        processTest("multiple_parent_ref");
    }

    @Test 
    public void interpolation()
    {
        processTest("interpolation");
    }
}
