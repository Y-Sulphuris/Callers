import com.ydo4ki.callers.Callers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Sulphuris
 * @since 12.10.2024 18:33
 */
public class Main {
	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = Cl2.class.getDeclaredMethod("call", new Class[0]);
		method.setAccessible(true);
		method.invoke(null, new Object[0]);
		Cl2.call();
		System.out.println(System.getProperty("com.ydo4ki.callers.impl"));
	}
}


class Cl2 {
	static void call() {
		System.out.println("call:");
		System.out.println(Callers.getCallerClass(1));
		System.out.println(Callers.getCallerClass());
	}
}