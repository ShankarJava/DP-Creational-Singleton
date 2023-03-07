package design.patterns.creational.singleton;

import java.io.Serializable;

//This class is created to test Singleton design principle
public class Employee implements Cloneable, Serializable {
	// 1. Private constructor

	private Employee() throws Exception {
		// Fix for the below 2 statements in main tesst class
		// constructors[0].setAccessible(true);
		// Employee emp3 = constructors[0].newInstance();
		// we can throw a runtime exception in private constructor if singleton
		// class is already initialized.
		if (EMPLOYEE_INSTANCE != null)
			throw new RuntimeException("DONT BREAK SINGLETON, OBJECT CREATION IS NOT ALLOWED - THRU REFLECTION(CONSTRUCTOR)");

	}

	// 2. Private static var
	private static Employee EMPLOYEE_INSTANCE;

	// 3. Public static factory method(access point of this class's object)
	// Double checked locking
	public static Employee getEmpInstance() throws Exception {

		if (EMPLOYEE_INSTANCE == null) {
			synchronized (Employee.class) {
				if (EMPLOYEE_INSTANCE == null) {
					EMPLOYEE_INSTANCE = new Employee();
				}
			}
		}
		return EMPLOYEE_INSTANCE;
	}

	// This way of overriding method will allow us to violate singleton pattern
	// Ref: Employee emp4 = (Employee) emp2.clone();

	// @Override
	// protected Object clone() throws CloneNotSupportedException{
	// return super.clone();
	// }

	// FIXING VIOLATION OF CLONING METHOD
	@Override
	protected Object clone() throws CloneNotSupportedException {

		throw new CloneNotSupportedException();
	}

	// FIXING DESERIALIZATION VIOLATION
	// we have to implement readResolve() method in Singleton class.
	// We can either throw runtime exception from readResolve() method or either
	// return
	// instance of Singleton class.
	private Object readResolve() throws Exception {
		System.out.println("rr");
		return EMPLOYEE_INSTANCE;
	}
	// BillPugh 
	// private static class EmployeeHelper {
	// private static final Employee EMPLOYEE_INSTANCE = new Employee();
	//
	// }
	//
	// public static Employee getEmpInstance() throws Exception {
	// return EmployeeHelper.EMPLOYEE_INSTANCE;
	// }

}
