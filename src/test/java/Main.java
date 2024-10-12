import com.ydo4ki.callers.Callers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Sulphuris
 * @since 12.10.2024 18:33
 */
public class Main {
	public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = Cl2.class.getDeclaredMethod("call");
		method.setAccessible(true);
		method.invoke(null);
		Cl2.call();
	}
}


class Cl2 {
	static void call() {
		System.out.println("call:");
		System.out.println(Callers.getCallerClass(1));
		System.out.println(Callers.getCallerClass());
	}
}