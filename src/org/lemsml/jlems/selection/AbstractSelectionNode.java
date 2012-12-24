package org.lemsml.jlems.selection;

import java.util.ArrayList;
import java.util.HashMap;

import org.lemsml.jlems.expression.Node;
import org.lemsml.jlems.run.ConnectionError;
import org.lemsml.jlems.run.RuntimeError;
import org.lemsml.jlems.run.StateInstance;
import org.lemsml.jlems.run.StateRunnable;
import org.lemsml.jlems.sim.ContentError;



public abstract class AbstractSelectionNode extends Node {

	public abstract String getEvaluationProcessDescription();

	public abstract ArrayList<StateRunnable> getMatches(StateRunnable baseSI) throws ContentError, ConnectionError, RuntimeError;

	public abstract void replaceSymbols(HashMap<String, String> map);
	 

}