// Anuneet Anand (2018022)
// Rhythm Patel (2018083)
// ADA Assignment
// Huffman Coding

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;

class Node implements Comparable<Node>
{
    // Stores information about characters
    private String Letter;
    private int Frequency;
    private Node L;
    private Node R;

    public Node(String letter, int frequency, Node l, Node r)
    {
        Letter = letter;
        Frequency = frequency;
        L = l;
        R = r;
    }

    public String getLetter() { return Letter; }
    public int getFrequency() { return Frequency; }
    public Node getL() { return L; }
    public Node getR() { return R; }

    @Override
    public int compareTo(Node other)
    { return this.getFrequency()- other.getFrequency(); }
}

public class HuffmanCode
{
    static FileReader R;
    static PrintWriter P1;
    static PrintWriter P2;
    static String InputString = "";
    static String EncodedString = "";
    static HashMap<String, Integer> Text = new HashMap<>();                 // Stores Characters & Frequencies
    static PriorityQueue<Node> Data = new PriorityQueue<>();                // Stores Character Nodes
    static HashMap<String, String> Table = new HashMap<>();                 // Stores Characters & Codes
    static Node Start = null;                                               // Root Of Huffman Tree
    static double AccessTime;

    public static void ReadData() throws IOException
    {
        // Reads characters from the file input.txt and record their frequencies.
        try
        {
            R = new FileReader("input.txt");
            int c;
            while ((c = R.read()) != -1)
            {
                InputString += (char) (c);
                String A = String.valueOf((char) (c));
                if (Text.containsKey(A))
                { Text.replace(A, Text.get(A) + 1); }
                else { Text.put(A, 1); }
            }
        }
        finally
        { if (R!=null) {R.close();} }
    }

    public static void AnalyseData()
    {
        // Creates nodes for building Huffman Tree.
        Text.forEach((key, value) -> Data.add(new Node((String) key, (int) value, null, null)));
    }

    public static void BuildTree()
    {
        // Builds the Huffman Tree.
        if(Data.size()==1) {Table.put(Data.poll().getLetter(),"1");}
        while(Data.size()>1)
        {
            Node a = Data.poll();
            Node b = Data.poll();
            Node ab = new Node((a.getLetter()+b.getLetter()), (a.getFrequency()+b.getFrequency()),a,b);
            Start = ab;
            Data.add(ab);
        }
    }

    public static void CreateTable(Node start,String code)
    {
        // Traverses the Huffman Tree to assign prefix free codes to different characters.
        // Convention : Left - 0 , Right - 1
        if(start == null) { return; }
        if ((start.getL() == null)&&(start.getR() == null))
        {
            Table.put(start.getLetter(),code);
            return;
        }
        CreateTable(start.getL(),code+"0");
        CreateTable(start.getR(),code+"1");
    }

    public static void Encode() throws FileNotFoundException
    {
        // Encodes the Input String to Bit String using the Huffman Codes.
        // Mapping stored in mapping.txt & Bit String stored in encoded.txt .
        for(char c : InputString.toCharArray())
        { EncodedString += Table.get(String.valueOf(c)); }
        try
        {
            P1 = new PrintWriter("mapping.txt");
            Table.forEach((key, value) -> P1.println(key + ":" + value));
        }
        finally
        { if(P1!=null) { P1.close(); } }
        try
        {
            P2 = new PrintWriter("encoded.txt");
            P2.print(EncodedString);
        }
        finally { if(P2!=null){P2.close(); }}
    }

    public static void Compute()
    {
        // Computes Average Access Time
        // Formula : Summation [Probability(c) * Depth(c)]
        for( String C : Text.keySet())
        { AccessTime += Text.get(C)*Table.get(C).length(); }
        if(InputString.length()>0){AccessTime /= InputString.length();}
    }
    
    public static void main(String[] args) throws IOException
    {
        ReadData();
        AnalyseData();
        BuildTree();
        CreateTable(Start,"");
        Encode();
        Compute();
        System.out.println("Average Access Time: "+ AccessTime);
        System.out.println("Bits Required For Input: "+InputString.length()*8);
        System.out.println("Bits Required For Huffman Encoding: "+EncodedString.length());
    }
}
