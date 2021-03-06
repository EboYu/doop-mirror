package org.clyze.deepdoop.system;

import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.clyze.deepdoop.actions.*;
import org.clyze.deepdoop.datalog.DatalogLexer;
import org.clyze.deepdoop.datalog.DatalogListenerImpl;
import org.clyze.deepdoop.datalog.DatalogParser;
import org.clyze.deepdoop.datalog.Program;
import org.clyze.deepdoop.datalog.component.Component;

public class Compiler {

	public static List<Result> compile(String outDir, String filename) {
		List<Result> results = null;
		try {
			DatalogParser parser = new DatalogParser(
					new CommonTokenStream(
						new DatalogLexer(
							new ANTLRFileStream(filename))));
			DatalogListenerImpl listener = new DatalogListenerImpl();
			ParseTreeWalker.DEFAULT.walk(listener, parser.program());

			Program p = listener.getProgram();

			PostOrderVisitor<IVisitable> v = new PostOrderVisitor<>(new FlatteningActor(p.comps));
			Program flatP = (Program) p.accept(v);

			LBCodeGenVisitingActor codeGenActor = new LBCodeGenVisitingActor(outDir);
			codeGenActor.visit(flatP);
			results = codeGenActor.getResults();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
}
