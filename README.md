# Huffman & Shannon-Fano Coding

- Generator.py : To generate random test cases
- input.txt : Input string (without linebreaks) 
- encoded.txt : Encoded Bit string
- mapping.txt : Characters & Codes
- decoded.txt : Decoded String

### Observation
Huffman code offers less average access time and require less bits than Shannon-Fano code in most cases. For the sample input,

## Huffman
- Average Access Time: 6.6086
- Bits Required For Input: 80000
- Bits Required For Huffman Encoding: 66086
- Decoding Time: 0.033574219s

## ShannonFano
- Average Access Time: 6.6317
- Bits Required For Input: 80000
- Bits Required For Shannon Fano Encoding: 66317
- Decoding Time: 0.032731487s
