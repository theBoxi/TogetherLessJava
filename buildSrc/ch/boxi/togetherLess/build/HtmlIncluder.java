package ch.boxi.togetherLess.build;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class HtmlIncluder {
	private static boolean verbose = false;
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("f", true, "file to Convert");
		options.addOption("d", true, "directory to convert all files in it");
		options.addOption("v", false, "varbose");
		
		HelpFormatter formatter = new HelpFormatter();
		CommandLineParser parser = new PosixParser();
		CommandLine params = null;
		try {
			params = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
			formatter.printHelp("HtmlIncluder", options );
			System.exit(-1);
			return;
		}
		
		File root = null;
		if(params.hasOption("v")){
			verbose = true;
		}
		if(params.hasOption("f")){
			root = new File(params.getOptionValue("f"));
		} else if(params.hasOption("d")){
			root = new File(params.getOptionValue("d"));
		} else{
			formatter.printHelp("HtmlIncluder", options );
			System.exit(-1);
			return;
		}
		checkFileOrDir(root);
	}
	
	private static void checkFileOrDir(File file){
		if(file.isDirectory()){
			for(File subFile: file.listFiles()){
				checkFileOrDir(subFile);
			}
		} else{
			convertFile(file);
		}
	}
	
	private static void convertFile(File file){
		if(verbose){
			System.out.println("convertFile: " + file);
		}
	}

}
