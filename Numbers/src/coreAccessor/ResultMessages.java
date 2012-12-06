package coreAccessor;

import java.math.BigInteger;
import java.util.ListResourceBundle;

public class ResultMessages extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return contents;
	}
	
	static final Object[][] contents = {
		{"resultHeader","Results for %1$s"},
		{"integerPartLabel","Integer Part in Base %1$s: %2$s"},
		{"fractionRepLabel","Fraction Rep in Base %1$s: %2$s"},
		{"decimalRepLabel","Decimal Rep in Base %1$s: %2$s"},
	};
	
	

}
