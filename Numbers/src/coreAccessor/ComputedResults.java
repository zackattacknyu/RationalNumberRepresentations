package coreAccessor;

import java.util.ListResourceBundle;

public class ComputedResults extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return contents;
	}
	
	static final Object[][] contents = {
		{"userInputPart","%1$s in base %2$s is %3$s in base %4$s"}
	};

}
