package org.lemsml.jlems.expression;

import java.util.HashMap;

import org.lemsml.jlems.eval.AbstractDVal;
import org.lemsml.jlems.eval.Times;
import org.lemsml.jlems.sim.ContentError;

public class TimesNode extends AbstractFloatResultNode {

	
	public TimesNode() {
		super("*");
	}

     
	
	public TimesNode copy() {
		return new TimesNode();
	}
	
	public int getPrecedence() {
		return 3;
	}

	public double op(double x, double y) {
		return x * y;
	}
	
	public AbstractDVal makeEvaluable(HashMap<String, Double> fixedHM) throws ContentError {
		checkLeftRight();
		return new Times(leftEvaluable.makeEvaluable(fixedHM), rightEvaluable.makeEvaluable(fixedHM));
	}
	
	public Dimensional dimop(Dimensional dl, Dimensional dr) {
		Dimensional ret = dl.getTimes(dr);
		return ret;
	}



	@Override
	public void doVisit(ExpressionVisitor ev) throws ContentError {
			checkLeftRight();
			ev.visitTimesNode(leftEvaluable, rightEvaluable);
		}
		
 
	
}
