package order.payment;

import java.text.DecimalFormat;

public class CommaUtil {
	
	public static String toNumFormat(int num) {
		  DecimalFormat df = new DecimalFormat("#,###");
		  return df.format(num);
		 }

}
