package com.naveen.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.naveen.structure.Node;
import com.naveen.structure.Tree;

public class RemoveDuplicate {
	
	private static void buildLevelOrder(Node<String> node)
	{
		Queue<Node<String>> queue = new LinkedList<Node<String>>();
		queue.add(node);
		//printNode(queue.peek().getData());
		while(!queue.isEmpty())
		{
			Node<String> q = queue.peek();
			for(int i = 0 ; i < q.getChildren().size() ; i++)
			{
				queue.add(q.getChildAt(i));
			}
			System.out.println(queue.poll().getData());
		}
		
	}
	
			

	private static void preOrder(Node<String> node)
	{
		if(node.getChildren().size()==0){
			System.out.println(node.getData());
			return;}
		else
		{
			System.out.println(node.getData());
			for(int i = 0 ; i < node.getChildren().size() ; i++)
			{
				preOrder(node.getChildAt(i));
			}	
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		removeDuplicate("/Users/naveenmarikar/Desktop/Jav/logs.txt" , "/Users/naveenmarikar/Desktop/Jav/output.txt");
	}
	
	public static boolean isMethodIn(String line)
	{
		return line.startsWith("MI~");
	}
	
	public static boolean isMethodOut(String line)
	{
		return line.startsWith("MO~");
	}
	
	public static void removeDuplicate(String source , String target) throws IOException
	{
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try
		{
		reader = new BufferedReader(new FileReader(source));
		writer = new BufferedWriter(new FileWriter(target));
		Stack<Node<String>> stack = new Stack<Node<String>>();
		
		String line = null;
		
		Node<String> root = new Node<String>("START");
		int depth = 0 ;
		root.setDepth(depth);
		int prevdepth = 0;
		while((line = reader.readLine()) != null)
		{
			if(isMethodIn(line))
			{
				depth++;
				stack.push(new Node<String>(line));
				stack.peek().setDepth(depth);
				
			}
			else if(isMethodOut(line))
			{
				depth--;
				if(stack.size() == 1)
				{
					Node<String> close = new Node<String>("MO~" + stack.peek().getData().substring(3));
					Node<String> last = stack.peek();
					if(root.getChildren().contains(last))
					{
						stack.pop();
					}
					else
					{
						root.addChild(last);
						root.addChild(close);
						stack.pop();
					}
				}
				else
				{
					Node<String> last = stack.pop();
					if(!stack.peek().getChildren().contains(last))
					{
						stack.peek().addChild(last);
						stack.peek().addChild(new Node<String>("MO~"+ last.getData().substring(3)));
					}		
				}
			}
			
		}
		Tree<String> tree = new Tree<String>(root);

		ArrayList<Node<String>> list = tree.getPreOrderTraversal();
		for(Node<String> x : list)
		{
			writer.write(x.getData());
			writer.newLine();
		}
		
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			reader.close();
			writer.close();
		}
				
	}

}


