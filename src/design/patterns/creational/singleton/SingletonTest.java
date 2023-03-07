package design.patterns.creational.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonTest {

	public static void main(String[] args) throws Exception, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Employee emp1 = Employee.getEmpInstance();
		Employee emp2 = Employee.getEmpInstance();

		System.out.println(emp1.hashCode());// 366712642
		System.out.println(emp2.hashCode());// 366712642

		// CASE1-VIOLATION THRU REFLECTION(VIA CONSTRUCTOR ACCESS)
//		Constructor<Employee> constructor = Employee.class.getDeclaredConstructor();
//		constructor.setAccessible(true);
//		Employee emp3 = constructor.newInstance();
		
		Constructor<Employee>[] constructors = (Constructor<Employee>[]) Employee.class.getDeclaredConstructors();
		System.out.println(constructors.length);// 1
		constructors[0].setAccessible(true);
		// Employee emp3 = constructors[0].newInstance();
		/*
		 * InstantiationException, IllegalAccessException,
		 * IllegalArgumentException, InvocationTargetException
		 */
		// System.out.println(emp3.hashCode());// 1829164700
		// A new object is created if exception is not thrown in constructor

		// CASE2 - VIOLATING THRU CLONNING
/*		Employee emp4 = (Employee) emp2.clone();
		System.out.println(emp4.hashCode());
*/
		// CASE3 - VIOLATING THRU SERIALIZATION
		
		   ObjectOutput out = new ObjectOutputStream(new FileOutputStream("D:/JavaSE8/DesignPatterns/src/EmployeeObj.text"));
	        out.writeObject(emp2);
	        out.close();
		
	        ObjectInput in = new ObjectInputStream(new FileInputStream("D:/JavaSE8/DesignPatterns/src/EmployeeObj.text"));
	        Employee emp5 = (Employee) in.readObject();
	        in.close();
	        
	        System.out.println(emp2.hashCode());
	        System.out.println(emp5.hashCode());



	}

}
