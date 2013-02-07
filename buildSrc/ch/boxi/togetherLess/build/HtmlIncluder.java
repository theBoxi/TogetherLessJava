package ch.boxi.togetherLess.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
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
	private static Set<File> convertedFiles = new HashSet<>();
	private static Properties properties = new Properties();
	private static Set<String> fileEndingsToConvert = new HashSet<>();
	
	static{
		fileEndingsToConvert.add("html");
		fileEndingsToConvert.add("htm");
		fileEndingsToConvert.add("js");
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Options options = new Options();
		options.addOption("f", true, "file to Convert");
		options.addOption("d", true, "directory to convert all files in it");
		options.addOption("p", true, "propertyFile");
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
		
		String fileName = "";
		if(params.hasOption("p")){
			fileName = params.getOptionValue("p");
		}else{
			fileName = "config/togetherLess.properties";
		}
		File file = new File(fileName);
		if(!file.exists() || !file.canRead()){
			System.err.println("file " + fileName + " doesn't exists or is not readable");
			System.err.println("current Dir is " + new File("./").getAbsolutePath());
			formatter.printHelp("HtmlIncluder", options );
			System.exit(-1);
		}
		properties.load(new FileReader(fileName));
		checkFileOrDir(root);
	}
	
	private static void checkFileOrDir(File file){
		if(file.isDirectory()){
			for(File subFile: file.listFiles()){
				checkFileOrDir(subFile);
			}
		} else{
			if(isFileToConvert(file)){
				convertFile(file);
			}
		}
	}
	
	static boolean isFileToConvert(File file){
		String fileName = file.toString();
		String fileNameEnd = fileName.substring(fileName.lastIndexOf(".")+1);
		if(fileName.endsWith("jquery-1.7.2.min.js")){
			return false;
		}
		return fileEndingsToConvert.contains(fileNameEnd);
	}
	
	private static void convertFile(File file){
		if(convertedFiles.contains(file)){
			log("already converted file: " + file + " -> skipping");
			return;
		}
		log("convertFile: " + file);
		BufferedReader in = null;
		PrintWriter out = null;
		
		// <tgl:include file="./header.html"/>
		Pattern includePattern  = Pattern.compile("<tgl:include\\s+file=\"([0-9a-zA-Z\\./-_]+)\"\\s*/>");
		PropertyReplaycer propertyReplaycer = new PropertyReplaycer(properties, verbose);
		
		try {
			File tmpFile = File.createTempFile(file.getName(), ".tmp");
			in = new BufferedReader(new FileReader(file));
			out = new PrintWriter(tmpFile);
			while(in.ready()){
				String line = in.readLine();
				
				Matcher includeMatcher = includePattern.matcher(line);
				if(includeMatcher.find()){
					String relativFileToInclude = includeMatcher.group(1);
					log("found file to include: " + relativFileToInclude);
					Path path = file.toPath();
					File fileToInclude = new File(path.subpath(0, path.getNameCount()-1).toString() + File.separator + relativFileToInclude);
					convertFile(fileToInclude);
					String includedLine = readFileInLine(fileToInclude);
					includedLine = escapeReplacementString(includedLine);
					line = includeMatcher.replaceAll(includedLine);
				}
				line = propertyReplaycer.replayceAllProperties(line);
				out.println(line);
			}
			in.close();
			out.close();
			Files.move(tmpFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			convertedFiles.add(file);
		} catch (FileNotFoundException e) {
			System.err.println("failed to convert File: " + file + " because of: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("failed to convert File: " + file + " because of: " + e.getMessage());
		} catch (IllegalArgumentException e) {
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
	
	private static String escapeReplacementString(String replacementString){
		BufferedReader in = null;
		PrintWriter out = null;
		StringWriter stringWriter = new StringWriter();
		try {
			in = new BufferedReader(new StringReader(replacementString));
			out = new PrintWriter(stringWriter);
			while(in.ready()){
				String line = in.readLine();
				if(line != null){
					line = line.replace("\\", "\\\\");
					line = line.replace("$", "\\$");
					out.println(line);
				} else{
					break;
				}
			}
			replacementString = stringWriter.getBuffer().toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					System.err.println("failed to escape replacement String: " + e.getMessage());
				}
			}
			if(out != null){
				out.close();
			}
			try {
				stringWriter.close();
			} catch (IOException e) {
				System.err.println("failed to escape replacement String: " + e.getMessage());
			}
		}
		return replacementString;
	}
	
	private static String readFileInLine(File file){
		BufferedReader in = null;
		StringBuffer buffer = new StringBuffer();
		try{
			in = new BufferedReader(new FileReader(file));
			while(in.ready()){
				buffer.append(in.readLine());
				buffer.append("\n");
			}
		} catch(IOException e){
			log("failed to read file " + file + " because of " + e.getMessage());
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				log("failed to close file " + file + " because of " + e.getMessage());
			}
		}
		return buffer.toString();
	}
	
	private static void log(String msg){
		if(verbose){
			System.out.println(msg);
		}
	}
}
