package FrameworkUnitTests;

import java.nio.file.Paths;
import java.text.ParseException;

import Utilities.ReportFactory_DB;

public class GenerateComparisonReport 
{
	@SuppressWarnings("deprecation")
    public static void main( String[] args ) throws ParseException
    {
    		// TODO Auto-generated method stub
 		ReportFactory_DB.switchOn();
 	 	//ReportFactory_DB.initMySql("sql6.freemysqlhosting.net/sql6135035","sql6135035","ubg1YMgphQ");
			
		 ReportFactory_DB.initSqlite(Paths.get("").toAbsolutePath().toString()+"\\ExecutionReports\\DB");
		
 	 	 ReportFactory_DB.getComparisonReport("DAMCO");
 	 		 	
 	 	ReportFactory_DB.closeDBConnection();
 	 	}
 	 	
    	}
 


