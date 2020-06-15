// Anuneet Anand (2018022)
// Rhythm Patel (2018083)
// ADA Assignment
// Shannon-Fano Coding

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Node implements Comparable<Node>
{
    // Stores information about characters
    private String Letter;
    private Double Probability;
    private String Code;

    public Node(String letter, Double probability)
    {
        Letter = letter;
        Probability = probability;
        Code = "";
    }

    @Override
    public int compareTo(Node other)
    {
        return this.Probability.compareTo(other.Probability);
    }

    public String getLetter() { return Letter; }
    public Double getProbability() { return Probability; }
    public String getCode() { return Code; }
    public void setCode(String code) { Code = code; }
}

public class SFCode
{
    static FileReader R;
    static PrintWriter P1;
    static PrintWriter P2;
    static String InputString = "";
    static String EncodedString = "";
    static HashMap<String,Double> Text = new HashMap<>();            //Stores Characters & Probabilities
    static HashMap<String,String> Table = new HashMap<>();           //Stores Characters & Codes
    static ArrayList<Node> Data = new ArrayList<>();                 //To Implement Shannon-Fano Algorithm
    static double AccessTime;

    public static void ReadData() throws IOException
    {
        // Reads characters from file input.txt and record their probabilities.
        try
        {
            R = new FileReader("input.txt");
            int c;
            while ((c = R.read()) != -1)
            {
                InputString += (char) (c);
                String A = String.valueOf((char) (c));
                if (Text.containsKey(A))
                    { Text.replace(A, Text.get(A) + 1.0); }
                else
                    { Text.put(A, 1.0); }
            }
        }
        finally { if (R != null) { R.close(); } }

        for(String C : Text.keySet())
        { Text.replace(C,(Text.get(C)/InputString.length())); }

        Text.forEach((key, value) -> Data.add( new Node((String) key, (double) value) ));
        Collections.sort(Data);
        Collections.reverse(Data);
    }

    public static void SF(int L,int R)
    {
        // Implements Shannon-Fano Algorithm
        // Convention: Left - 0 , Right - 1
        if(R-L==1) {return;}
        double S = 0;
        double T = S;
        double Divider = 0;
        for(int i=L;i<R;i++) { Divider += Data.get(i).getProbability(); }
        Divider /= 2;
        int M = 0;

        for(int i=L;i<R;i++)
        {
            T = S;
            S += Data.get(i).getProbability();
            if (S  >= Divider)
            {
                if (Divider-T<S-Divider) {M = i ;}
                else { M = i+1;}
                break;
            }
        }

        for(int i=L;i<M;i++)
        { Data.get(i).setCode(Data.get(i).getCode() + "0"); }
        for(int i=M;i<R;i++)
        { Data.get(i).setCode(Data.get(i).getCode() + "1"); }

        SF(L,M);
        SF(M,R);
    }

    public static void CreateTable()
    {
        // Store generated codes of characters
        for( Node N : Data)
        { Table.put(N.getLetter(),N.getCode()); }
    }

    public static void Encode() throws FileNotFoundException
    {
        // Encodes the Input String to Bit String using the Shannon Fano Codes.
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
    }

    public static void main(String[] args) throws IOException
    {
        ReadData();
        SF(0, Data.size());
        CreateTable();
        Encode();
        Compute();
        System.out.println("Average Access Time: "+ AccessTime);
        System.out.println("Bits Required For Input: "+InputString.length()*8);
        System.out.println("Bits Required For Shannon Fano Encoding: "+EncodedString.length());
    }
}
