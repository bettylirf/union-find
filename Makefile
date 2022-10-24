# file: Makefile
#
#	You must NOT change this Makefile. 
#		It includes another file "make-include", which you MAY change.
#		I provide a sample "make-include" to show 
#			how I customize my configuration, namely,
#				(Cygwin installed on a Windows 10 laptop).
#			You should customize it for own environment
#				(Mac, Linux, Windows).
#		If you are in Windows, then I suggest you install the Cygwin
#		to give you a Unix/Linux like operating system inside Windows.
#		You can freely move between Windows and Cygwin, and share
#		each other's files. Talk to our Tutor Metarya for other OS.
# 
#	HINTS:
#		-- This file is best viewed with an editor
#			that understands Makefile syntax and provides syntax colors
#		-- Note the following "standard" variables:
#
#				p: program to run/compile
#				ss: seed for random generator (ss=0 is special)
#				nn: int value used by many programs
#				mm: int value if we need a second int 
#
#############################################
#  CS 301, Basic Algorithms, Fall 2021
#	Prof. Chee Yap
#	TAs: Bingran Shen and Zihan Feng
#############################################

# ================================================
# ENVIRONMENT CUSTOMIZATION:
# ================================================
# Put your customizations in make-include file.
# 	To ensure that the default target is NOT over-ridden
#	by your make-include, we put the default here:
# FOR INSTRUCTORS: the make-include should change myName from "yap"
#	to "sol" when producing solutions.

default: compileAll

-include make-include

# program 
# ================================================
# Hello should be always available for small Java experiments
p=Hello
p=Hello1 		# think of Hello1 as a variant of Hello
p=UnionFind
p=Graph
p=myKruskal
p=myGraphWt
p=myUnionFind

# standard command line arguments:
# ================================================
# the first 3 arguments for command lines are three integers:
ss=111
nn=10
mm=0
# Other arguments are optional:
a3=OUTPUT/ofile.txt
# We assemble them into a single argument:
# args=$(ss) $(nn) $(mm) $(a3) 
args=$(ss) $(nn) $(mm) 

# ================================================
# TARGETS
# ================================================
h help:
	-@echo "HELP:"
	-echo "    >make                  -- compile everything" 
	-@echo "    >make c               -- compile \$$(p)"
	-@echo "    >make r               -- run \$$(p)" 
	-@echo "    >make cr              -- compile and run \$$(p)" 
	-@echo "    >make t1 nn=12 ss=0   -- test1" 
	-@echo "    >make t2 mm=2 nn=321  -- test2 (etc)"

# default is to compile all *java programs
ca compileAll:
	test -d bin || mkdir bin
	$(javac) $(cflags) -d bin *.java 

# for doing only ONE program (great in debugging):
cr compileRun: c r

c compile javac compileOne:
	test -d bin || mkdir bin
	$(javac) $(cflags) -d bin $(p).java

r run java: 
	$(java) $(rflags) -classpath bin $(p) $(args)

r0 run0 java0: 
	$(java) $(rflags) -classpath bin $(p) 

# running a variant of $(p):
r1 run1 java1: 
	$(java) $(rflags) -classpath bin $(p)1 $(args) 

s showargs:
	@printf "ss= $(ss), nn=$(nn), mm=$(mm)\n"
	@printf "args= $(args)\n"

# ================================================
# SPECIFIC TARGETS
# ================================================
hi hello:
	@echo "Hello program is always available for testing!"
	$(java) -classpath bin Hello $(ss) $(nn)

hi1 hello1:
	@echo "Hello1 program is always available for testing!"
	$(java) -classpath bin Hello1 $(ss) $(nn)

t test:
	@echo "test: \$$(p) no command line args"
	$(java) $(rflags) -classpath bin $(p) 

t1 test1:
	@echo "test1: \$$(p) with \$$(args)"
	$(java) $(rflags) -classpath bin $(p) $(args)

t9 test9 debug TESTOUTPUT9:
	@echo "TESTOUTPUT9 : (debugging)"
	$(java) $(rflags) -classpath bin Tree23 1 $(_nn) 9

tg testGraph:
	@echo "testGraph: p=Graph with \$$(args)"
	$(java) $(rflags) -classpath bin Graph $(args)

tg1 testGraph1:
	@echo "testGraph1: p=Graph with ss=111, nn=15, mm=1"
	$(java) $(rflags) -classpath bin Graph 111 15 1

########################## GraphWt
tw tgw testGraphWt:
	@echo "testGraphWt: p=GraphWt with \$$(args)"
	$(java) $(rflags) -classpath bin GraphWt $(args)

tmw tmgw testMyGraphWt:
	@echo "testMyGraphWt: p=myGraphWt with \$$(args)"
	$(java) $(rflags) -classpath bin myGraphWt $(args)

te testEdge:
	@echo "testEdge: p=Edge with \$$(args)"
	$(java) $(rflags) -classpath bin Edge $(args)

########################## UnionFind
tu tuf testUnionFind:
	@echo "testUnionFind: p=UnionFind with \$$(args)"
	$(java) $(rflags) -classpath bin UnionFind $(args)

tmu tmuf testMyUnionFind:
	@echo "testMyUnionFind: p=myUnionFind with \$$(args)"
	$(java) $(rflags) -classpath bin myUnionFind $(args)

########################## Kruskal
tk testKruskal:
	@echo "testKruskal: p=Kruskal with \$$(args)"
	$(java) $(rflags) -classpath bin Kruskal $(args)

tmk testMyKruskal:
	@echo "testMyKruskal: p=myKruskal with \$$(args)"
	$(java) $(rflags) -classpath bin myKruskal $(args)

########################## Kruskal
tout testOutput:
	@echo "producing output files for OUT subfolder"
	$(java) $(rflags) -classpath bin Graph \
		111 10 1 > OUT/testGraph-mm1.txt
	$(java) $(rflags) -classpath bin Graph\
		111 10 2 > OUT/testGraph-mm2.txt
	$(java) $(rflags) -classpath bin Graph\
		111 10 3 > OUT/testGraph-mm3.txt
	$(java) $(rflags) -classpath bin GraphWt \
		111 10 1 > OUT/testGraphWt-mm1.txt
	$(java) $(rflags) -classpath bin GraphWt \
		111 10 2 > OUT/testGraphWt-mm2.txt
	$(java) $(rflags) -classpath bin Edge \
		111 10 1 > OUT/testEdge-mm1.txt
	$(java) $(rflags) -classpath bin Edge \
		111 10 2 > OUT/testEdge-mm2.txt
	$(java) $(rflags) -classpath bin UnionFind \
		111 10 1 > OUT/testUnionFind-mm1.txt
	$(java) $(rflags) -classpath bin UnionFind \
		111 10 2 > OUT/testUnionFind-mm2.txt
	$(java) $(rflags) -classpath bin UnionFind \
		111 10 3 > OUT/testUnionFind-mm3.txt
	$(java) $(rflags) -classpath bin Kruskal \
		111 10 1 > OUT/testKruskal-mm1.txt
	$(java) $(rflags) -classpath bin Kruskal \
		111 10 2 > OUT/testKruskal-mm2.txt

tmout testMyOutput:
	$(java) $(rflags) -classpath bin myGraphWt \
		111 10 1 > OUT/testMyGraphWt-mm1.txt
	$(java) $(rflags) -classpath bin myGraphWt \
		111 10 2 > OUT/testMyGraphWt-mm2.txt
	$(java) $(rflags) -classpath bin myUnionFind \
		111 10 1 > OUT/testMyUnionFind-mm1.txt
	$(java) $(rflags) -classpath bin myUnionFind \
		111 10 2 > OUT/testMyUnionFind-mm2.txt
	$(java) $(rflags) -classpath bin myUnionFind \
		111 10 3 > OUT/testMyUnionFind-mm3.txt
	$(java) $(rflags) -classpath bin myKruskal \
		111 10 1 > OUT/testMyKruskal-mm1.txt
	$(java) $(rflags) -classpath bin myKruskal \
		111 10 2 > OUT/testMyKruskal-mm2.txt

# ================================================
# HOUSEKEEPING
# ================================================
e edit g gvim:
	$(gvim) $(p).java &

m makefile:
	$(gvim) Makefile &

clean:
	-rm -f bin/* .*~ *.class *~ src/*~  src/.*

v vclean: clean
	-rm -r bin

# Create zip file "hwXXX-YYY.zip" (e.g., "hw0-yap.zip") one level up:
zip:	vclean
	-test -f ../$(thisFolder).zip && \
			rm ../$(thisFolder).zip
	cd ..; zip $(thisFolder).zip \
	 $(thisFolder)/README \
	 $(thisFolder)/hw6-yap.txt \
	 $(thisFolder)/Makefile \
	 $(thisFolder)/make-include \
	 $(thisFolder)/Util.java \
	 $(thisFolder)/UnionFind.java \
	 $(thisFolder)/myUnionFind.java \
	 $(thisFolder)/UnionFindInterface.java \
	 $(thisFolder)/Graph.java \
	 $(thisFolder)/GraphWt.java \
	 $(thisFolder)/myGraphWt.java \
	 $(thisFolder)/Edge.java \
	 $(thisFolder)/Kruskal.java \
	 $(thisFolder)/myKruskal.java \
	 $(thisFolder)/OUT/*.txt \
	 $(thisFolder)/OUT-REFERENCE/*.txt \

# ================================================
# END
# ================================================
