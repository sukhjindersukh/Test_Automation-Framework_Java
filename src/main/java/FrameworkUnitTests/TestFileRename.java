package FrameworkUnitTests;

import Utilities.ExcelDataUtil;

public class TestFileRename {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Object[][] browsers= ExcelDataUtil.getBrowsersList();
		for(Object[] s: browsers){
			 
			System.out.println((String)  s[0]);
			
		}
		
		
	}

}
