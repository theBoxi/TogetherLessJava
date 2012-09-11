package ch.boxi.togetherLess.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		log("convertFile: " + file);
		BufferedReader in = null;
		PrintWriter out = null;
		
		// <tgl:include file="./header.html"/>
		Pattern pattern = Pattern.compile("<tgl:include\\s+file=\"([0-9a-zA-Z\\./-_]+)\"\\s*/>");
		
		try {
			File tmpFile = File.createTempFile(file.getName(), ".tmp");
			in = new BufferedReader(new FileReader(file));
			out = new PrintWriter(tmpFile);
			while(in.ready()){
				String line = in.readLine();
				
				Matcher matcher = pattern.matcher(line);
				if(matcher.find()){
					String relativFileToInclude = matcher.group(1);
					log("found file to include: " + relativFileToInclude);
					Path path = file.toPath();
					File fileToInclude = new File(path.subpath(0, path.getNameCount()-1).toString() + File.separator + relativFileToInclude);
							
				}
				
				out.println(line);
			}
			in.close();
			out.close();
			Files.move(tmpFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (FileNotFoundException e) {
			System.err.println("failed to convert File: " + file + " because of: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("failed to convert File: " + file + " because of: " + e.getMessage());
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					System.err.println("failed to close File: " + file + " because of: " + e.getMessage());
				}
			}
			if(out != null){
				out.close();
			}
		}
	}
	
	private static void log(String msg){
		if(verbose){
			System.out.println(msg);
		}
	}
}
