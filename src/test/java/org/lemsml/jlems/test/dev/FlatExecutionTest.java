package org.lemsml.jlems.test.dev;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;
import org.lemsml.jlems.core.expression.ParseError;
import org.lemsml.jlems.core.flatten.ComponentFlattener;
import org.lemsml.jlems.core.logging.E;
import org.lemsml.jlems.core.run.ConnectionError;
import org.lemsml.jlems.core.run.RuntimeError;
import org.lemsml.jlems.core.sim.ContentError;
import org.lemsml.jlems.core.sim.ParseException;
import org.lemsml.jlems.core.sim.Sim;
import org.lemsml.jlems.core.type.BuildException;
import org.lemsml.jlems.core.type.Component;
import org.lemsml.jlems.core.type.ComponentType;
import org.lemsml.jlems.core.type.Lems;
import org.lemsml.jlems.core.xml.XMLException;
import org.lemsml.jlems.io.logging.DefaultLogger;
import org.lemsml.jlems.io.out.FileResultWriterFactory;
import org.lemsml.jlems.io.reader.FileInclusionReader;
import org.lemsml.jlems.viz.datadisplay.SwingDataViewerFactory;
 


public class FlatExecutionTest {

	
	static boolean useGui = false;
	
	 
    public static void main(String[] args) throws ContentError, ParseError, ConnectionError, RuntimeError, IOException, ParseException, BuildException, XMLException {
    	
    	DefaultLogger.initialize();
    	useGui = true;
    	FlatExecutionTest cft = new FlatExecutionTest();
    	cft.run();
    	 
    }
    
    
    
    @Test
    public void run() throws ContentError, ConnectionError, ParseError, IOException, RuntimeError, ParseException, BuildException, XMLException {
    	
    	if (useGui) {
    		SwingDataViewerFactory.initialize();
			DefaultLogger.initialize();
			FileResultWriterFactory.initialize();
	    }
	    	
		
		URL url = this.getClass().getResource("/ex-flat.xml");
		File fex = new File(url.getFile());
		 
		runComponentFlattened(fex, "na");
    
    }

 
	 
	
    
    public void runComponentFlattened(File f, String tgtid) throws ContentError,
    		ConnectionError, ParseError, IOException, RuntimeError, ParseException, 
    		BuildException, XMLException {
    	E.info("Loading LEMS file from: " + f.getAbsolutePath());

        FileInclusionReader fir = new FileInclusionReader(f);
        Sim sim = new Sim(fir.read());

        sim.readModel();
    
        Lems lems = sim.getLems();
        Component cpt = lems.getComponent(tgtid);

        ComponentFlattener cf = new ComponentFlattener(lems, cpt);

        ComponentType ct = cf.getFlatType();
        Component cp = cf.getFlatComponent();
        
        // String typeOut = XMLSerializer.serialize(ct);
        // String cptOut = XMLSerializer.serialize(cp);
      
        // E.info("Flat type: \n" + typeOut);
        // E.info("Flat cpt: \n" + cptOut);
        
		lems.addComponentType(ct);
		lems.addComponent(cp);
	
		lems.resolve(ct);
		lems.resolve(cp);
	 
		// cpt.setReplacement(cp);
		
		sim.build();
		
		sim.runTree();
		
		
		// TODO now substitue cp for cpt somehow and run...
    }
}
