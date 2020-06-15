// Anuneet Anand (2018022)
// Rhythm Patel (2018083)
// ADA Assignment
// Huffman Decoding

import java.io.*;
import java.util.HashMap;

public class HuffmanDecode
{
    static FileReader R1;
    static BufferedReader R2;
    static PrintWriter P;
    static String InputString = "";
    static String DecodedString = "";
    static HashMap<String,String> Reference ;                   // Stores Coding Map

    public static void ReadCode() throws IOException
    {
        // Reads encoded string from encoded.txt .
        try
        {
            R1 = new FileReader("encoded.txt");
            int c;
            while ((c = R1.read()) != -1)
            { InputString += (char) (c); }
        }
        finally { if(R1!=null) { R1.close();} }
    }

    public static void ReadMapping() throws IOException
    {
        // Reads Coding Map from mapping.txt .
        Reference = new HashMap<>();
        try
        {
            R2 = new BufferedReader(new FileReader("mapping.txt"));
            String s;
            while ((s = R2.readLine()) != null)
            {
                String A = s.substring(0, 1);
                String code = s.substring(2);
                Reference.put(code, A);
            }
        }
        finally { if(R2!=null) {R2.close();} }
    }

    public static void Decode() throws FileNotFoundException
    {
        // Decodes the encoded string using Coding Map.
        String S = "";
        long A = System.nanoTime();
        for(int i=0;i<InputString.length();i++)
        {
            S += InputString.charAt(i);
            if (Reference.containsKey(S))
            {
                DecodedString += Reference.get(S);
                S = "";
            }
        }
        long B = System.nanoTime();
        System.out.println("Decoding Time: "+(B-A)/1000000000.0 +"s");
        try
        {
            P = new PrintWriter("decoded.txt");
            P.print(DecodedString);
        }
        finally
        { if(P!=null) { P.close(); } }
    }

    public static void main(String[] args) throws IOException
    {
        ReadCode();
        ReadMapping();
        Decode();
    }
}
