// Programming Languages, Dan Grossman
// Section 8: OOP vs. Functional Decomposition 

abstract class Exp {
    abstract Value eval(); // no argument because no environment
    abstract String toStrng(); // renaming b/c toString in Object is public
    abstract boolean hasZero();
}

// this level of the class hierarchy is overkill here but will provide
// a more direct contrast with more complicated examples soon
abstract class Value extends Exp {

}

class Int extends Value {
    public int i;
    Int(int i) {
	this.i = i;
    }
    Value eval() {
	return this;
    }
    String toStrng() {
	return "" + i;
    }
    boolean hasZero() {
	return i==0;
    }
}

class Negate extends Exp {
    public Exp e;
    Negate(Exp e) {
	this.e = e;
    }
    Value eval() {
	// we downcast from Exp to Int, which will raise a run-time error
	// if the subexpression does not evaluate to an Int
	return new Int(- ((Int)(e.eval())).i);
    }
    String toStrng() {
	return "-(" + e.toStrng() + ")";
    }
    boolean hasZero() {
	return e.hasZero();
    }
}

class Add extends Exp {
    Exp e1;
    Exp e2;
    Add(Exp e1, Exp e2) {
	this.e1 = e1;
	this.e2 = e2;
    }
    Value eval() {
	// we downcast from Exp to Int, which will raise a run-time error
	// if either subexpression does not evaluate to an Int
	return new Int(((Int)(e1.eval())).i + ((Int)(e2.eval())).i);
    }
    String toStrng() {
	return "(" + e1.toStrng() + " + " + e2.toStrng() + ")";
    }
    boolean hasZero() {
	return e1.hasZero() || e2.hasZero();
    }
}
