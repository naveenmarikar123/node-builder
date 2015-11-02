package com.naveen.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NodeBuilder {
	public static boolean isMethodIn(String line)
	{
		return line.startsWith("MI~");
	}
	
	public static boolean isMethodOut(String line)
	{
		return line.startsWith("MO~");
	}
	
	private static void printMI(String line , BufferedWriter writer) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("\"").append("name").append("\"");
		builder.append(":");
		builder.append("\"").append(line).append("\"");
		builder.append(",");
		builder.append("\"").append("children").append("\"");
		builder.append(":");
		builder.append("[");
		writer.write(builder.toString());
		writer.newLine();
	}
	private static void printMO(String line, BufferedWriter writer) throws IOException
	{
		
		StringBuilder builder = new StringBuilder();
		builder.append("]");
		builder.append("}");
		writer.write(builder.toString());
		writer.newLine();
	}
	
	
	public static void main(String[] args) throws IOException
	{
		createTreeForD3JS("/Users/naveenmarikar/Desktop/Jav/output.txt", "/Users/naveenmarikar/Desktop/Jav/sample.json");
	}
	
	public static void createTreeForD3JS(String source,String target) throws IOException
	{
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try
		{
		reader = new BufferedReader(new FileReader(source));
		writer = new BufferedWriter(new FileWriter(target));
		
		String line = null;
		int prevdepth = 0;
		int depth = 0;
		while( (line = reader.readLine()) != null)
		{
			if(isMethodIn(line))
			{
				depth++;
				if(prevdepth == depth)
				{
					writer.write(",");
				}
				printMI(line,writer);
			}
			else if(isMethodOut(line))
			{
				prevdepth = depth;
				depth--;
				printMO(line,writer);
			}
		}
		}
		catch(Exception e)
		{
			
		}
		finally{
			reader.close();
			writer.close();
		}
		
	}

}
