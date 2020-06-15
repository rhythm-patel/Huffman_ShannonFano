from random import randint

S = ""
for i in range(10000):
	a = randint(32,126)
	S = S + chr(a)

with open("input.txt","w") as A:
	A.write(S)