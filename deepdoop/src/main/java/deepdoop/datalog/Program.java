package deepdoop.datalog;

import deepdoop.actions.IVisitable;
import deepdoop.actions.IVisitor;
import deepdoop.datalog.component.*;
import deepdoop.datalog.element.atom.IAtom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class Program implements IVisitable {

	public Component                    globalComp;
	public final Map<String, Component> comps;
	public final Map<String, String>    inits;
	public final Set<Propagation>       props;

	public Program(Component globalComp, Map<String, Component> comps, Map<String, String> inits, Set<Propagation> props) {
		this.globalComp = globalComp;
		this.comps      = comps;
		this.inits      = inits;
		this.props      = props;
	}
	public Program() {
		this(new Component(), new HashMap<>(), new HashMap<>(), new HashSet<>());
	}

	public void addComponent(Component comp) {
		comps.put(comp.name, comp);
	}
	public void addInit(String id, String comp) {
		if (inits.get(id) != null)
			throw new DeepDoopException("Id `" + id + "` already used to initialize a component");

		inits.put(id, comp);
	}
	public void addPropagation(String fromId, Set<IAtom> preds, String toId) {
		props.add(new Propagation(fromId, preds, toId));
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add(globalComp.toString());
		for (Component c : comps.values()) joiner.add(c.toString());
		if (inits != null) for (Map.Entry<String, String> entry : inits.entrySet()) joiner.add(entry.toString());
		if (props != null) for (Propagation prop : props) joiner.add(prop.toString());
		return joiner.toString();
	}


	@Override
	public <T> T accept(IVisitor<T> v) {
		return v.visit(this);
	}


	public static Program from(Component globalComp, Map<String, Component> comps, Map<String, String> inits, Set<Propagation> props) {
		return new Program(
				globalComp == null ? null : new Component(globalComp),
				comps      == null ? null : new HashMap<>(comps),
				inits      == null ? null : new HashMap<>(inits),
				props      == null ? null : new HashSet<>(props)
				);
	}
}
