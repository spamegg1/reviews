// Programming Languages, Dan Grossman
// Section 8: Binary Methods with OOP: Double Dispatch

abstract class Exp {
    abstract Value eval(); // no argument because no environment
    abstract String toStrng(); // renaming b/c toString in Object is public
    abstract boolean hasZero();
    abstract Exp noNegConstants();
}

abstract class Value extends Exp {
    abstract Value add_values(Value other); // first dispatch
    abstract Value addInt(Int other); // second dispatch
    abstract Value addString(MyString other); // second dispatch
    abstract Value addRational(Rational other); // second dispatch
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
    Exp noNegConstants() {
	if(i < 0)
	    return new Negate(new Int(-i));
	else
	    return this;
    }
    Value add_values(Value other) {
	return other.addInt(this);
    }
    Value addInt(Int other) {
	return new Int(other.i + i);
    }
    Value addString(MyString other) {
	return new MyString(other.s + i);
    }
    Value addRational(Rational other) {
	return new Rational(other.i+other.j*i,other.j);
    }
}

class MyString extends Value {
    public String s;
    MyString(String s) {
	this.s = s;
    }
    Value eval() {
	return this;
    }
    String toStrng() {
	return s;
    }
    boolean hasZero() {
	return false;
    }
    
    Exp noNegConstants() {
	return this;
    }

    Value add_values(Value other) {
	return other.addString(this);
    }
    Value addInt(Int other) {
	return new MyString("" + other.i + s);
    }
    Value addString(MyString other) {
	return new MyString(other.s + s);
    }
    Value addRational(Rational other) {
	return new MyString("" + other.i + "/" + other.j + s);
    }
}

class Rational extends Value {
    int i;
    int j;
    Rational(int i, int j) {
	this.i = i;
	this.j = j;
    }
    Value eval() {
	return this;
    }
    String toStrng() {
	return "" + i + "/" + j;
    }
    boolean hasZero() {
	return i==0;
    }
    Exp noNegConstants() {
	if(i < 0 && j < 0)
	    return new Rational(-i,-j);
	else if(j < 0)
	    return new Negate(new Rational(i,-j));
	else if(i < 0)
	    return new Negate(new Rational(-i,j));
	else
	    return this;
    }
    Value add_values(Value other) {
	return other.addRational(this);
    }
    Value addInt(Int other) {
	return other.addRational(this);	// reuse computation of commutative operation

    }
    Value addString(MyString other) {
	return new MyString(other.s + i + "/" + j);
    }
    Value addRational(Rational other) {
	int a = i;
	int b = j;
	int c = other.i;
	int d = other.j;
	return new Rational(a*d+b*c,b*d);
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
    Exp noNegConstants() {
	return new Negate(e.noNegConstants());
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
	return e1.eval().add_values(e2.eval());
    }
    String toStrng() {
	return "(" + e1.toStrng() + " + " + e2.toStrng() + ")";
    }
    boolean hasZero() {
	return e1.hasZero() || e2.hasZero();
    }
    Exp noNegConstants() {
	return new Add(e1.noNegConstants(), e2.noNegConstants());
    }
}

class Mult extends Exp {
    Exp e1;
    Exp e2;
    Mult(Exp e1, Exp e2) {
	this.e1 = e1;
	this.e2 = e2;
    }
    Value eval() {
	// we downcast from Exp to Int, which will raise a run-time error
	// if either subexpression does not evaluate to an Int
	return new Int(((Int)(e1.eval())).i * ((Int)(e2.eval())).i);
    }
    String toStrng() {
	return "(" + e1.toStrng() + " * " + e2.toStrng() + ")";
    }
    boolean hasZero() {
	return e1.hasZero() || e2.hasZero();
    }

    Exp noNegConstants() {
	return new Mult(e1.noNegConstants(), e2.noNegConstants());
    }
}   
