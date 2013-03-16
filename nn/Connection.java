package nn;

public class Connection {
	private Connectable leftMember;
	private Connectable rightMember;
	private double wage;
	
	public Connection(Connectable leftMember, Connectable rightMember) {
		this.setLeftMember(leftMember);
		this.setRightMember(rightMember);
	}

	public void setWage(double wage) {
		this.wage = wage;
	}

	public double getWage() {
		return wage;
	}

	public void setRightMember(Connectable rightMember) {
		this.rightMember = rightMember;
	}

	public Connectable getRightMember() {
		return rightMember;
	}

	public void setLeftMember(Connectable leftMember) {
		this.leftMember = leftMember;
	}

	public Connectable getLeftMember() {
		return leftMember;
	}
}
