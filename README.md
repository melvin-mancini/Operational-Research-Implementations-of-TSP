# <a name="Title"></a> Operational Research: Implementations of TSP

## <a name="Introduction"></a> Introduction
This simple project shows different implementations of some algorithms for resolving the TSP (Travelling Salesman Problem). Troubleshooting TSP can be done through two techniques: 
* exact methods
* heuristic techniques.
In this project we have implemented two exact methods (**Constraints Generation** and **Branch And Bound**) through **AMPL** (A Mathematical programming Language) and two heuristic techniques (**Nearest Neighbor** e **Repetetive Nearest Neighbor**) with **Java**. How to run the different programs is shown below.
Furthermore, there is a complete [report](./Report.pdf) in italian about the TSP and the various algorthms.

## <a name="AMPL and the exact methods"></a> AMPL and the exact methods

In the folder [AMPL](./AMPL), there are the files for resolving the TSP with two exact techniques: 
* Constraints Generation
* Branch and Bound.
First of all, it is necessary explain the structure of the folders. Inside [Algorithms folder](./AMPL/Algorithms) there are the files ([ConstrainsGeneration.run](./AMPL/Algorithms/ConstraitsGeneration.run) and [BranchAndBound.run](./AMPL/Algorithms/BranchAndBound.run)) that we use for resolving a specific TSP. In [Models folder](./AMPL/Models) there are the various models ([ConstrainsGenerationModel.mod](./AMPL/Models/ConstraitsGenerationModel.mod) and [BranchAndBoundModel.mod](./AMPL/Models/BranchAndBoundModel.mod)) written in AMPL language for describing the problem in mathematical way. Finally, we have the [Data folder](./AMPL/Data) where there is an example of a particular data for TSP ([dataTSP.dat](./AMPL/Data/dataTSP.dat)).

### <a name="How to run ConstraitsGeneration.run "></a> How to run ConstraintsGeneration.run
To run the ConstraintsGeneration.run program you must have AMPL software. To dowload and for a complete documentation you can consult the [web site of AMPL](http://ampl.com/).
Within the file ([ConstrainsGeneration.run](./AMPL/Algorithms/ConstraitsGeneration.run) there is the code for resolving a TSP through the Constraints Generation algorithm. Inside the file we specified the mathematical model ([ConstrainsGenerationModel.mod](./AMPL/Models/ConstraitsGenerationModel.mod) and the data ([dataTSP.dat](./AMPL/Data/dataTSP.dat)). Inside the ([ConstrainsGeneration.run](./AMPL/Algorithms/ConstraitsGeneration.run) it is possible change the location of the model and the data and select other files. For example, if you want change the data set or the model for a problem, you can create another file with .dat or .mod extension and specify the path inside the ([ConstrainsGeneration.run](./AMPL/Algorithms/ConstraitsGeneration.run). 
To run the program you need to move inside the [Algorithms folder](./AMPL/Algorithms) through the AMPL interface and in the AMPL terminal you have to digit **include ConstraintsGeneration.run** . After the execution you can see the results.

### <a name="How to run BranchAndBound.run "></a> How to run BranchAndBound.run
The remarks made about the data and the model for the Constraints Generation method are also valid for Branch And Bound algorithm. The file of the data is the same ([dataTSP.dat](./AMPL/Data/dataTSP.dat)) but the model is different (we use [BranchAndBoundModel.mod](./AMPL/Models/BranchAndBoundModel.mod)). The AMPL command for running the BranchAndBound.run program is always the same but changing the name of the file (**include BranchAndBound.run**). In this case, the program is not automatic as the ConstraintsGeneration.run and to accomplish the solution we might launch the program several times.
There is a main difference between the Constraints Generation method and the Branch and Bound method. In the first case, for each iteration, we add a constraint if there is a sub tour in the partial solution. In this way, when we will run again the program, the constrait added allows to delete the sub tour and another solution will be presented. Through the Branch and Bound method, we don't add a constraints, but in according to the partial solution matrix, we can see if there are some sub tours or not. If there is one or more sub tours we have to modify the cost matrix (the data file) and replace some particular elements with a very large number (10000). After that, you can run the program again for obtain another solution. For example, we have this cost matrix:

data; param n := 6;

| cost matrix   | 1     | 2    | 3    | 4    |5     | 6    |
| ------------- |:-----:|:----:|:----:|:----:|:----:|:----:|
| 1             | 1000  | 33.6 | 14   | 40.9 | 14.5 | 11.5 |
| 2             | 34.7  | 1000 | 21.7 | 13   | 20.2 | 23.4 |
| 3             | 14.8  | 21.5 | 1000 | 29.3 | 2    | 3.9  |
| 4             | 41.7  | 13.1 | 29.4 | 1000 | 27.6 | 30.3 |
| 5             | 15    | 20.2 | 2.0  | 27.5 | 1000 | 3.9  |
| 6             | 12    | 22.8 | 2    | 30.1 | 4    | 1000 |


If we run the file [BranchAndBound.run](./AMPL/Algorithms/BranchAndBound.run)) we obtain these solution:

| solution    | 1 | 2 | 3 | 4 | 5 | 6 |
| ----------- |:-:|:-:|:-:|:-:|:-:|:-:|
| 1           | 0 | 0 | 0 | 0 | 0 | 1 |
| 2           | 0 | 0 | 0 | 1 | 0 | 0 |
| 3           | 0 | 0 | 0 | 0 | 1 | 0 | 
| 4           | 0 | 1 | 0 | 0 | 0 | 0 |
| 5           | 0 | 0 | 1 | 0 | 0 | 0 |
| 6           | 1 | 0 | 0 | 0 | 0 | 0 |

In this case we have 4 sub tours: (1-6-1), (2-4-2), (3-5-3).
From the initial problem, now we have to break up the sub tours and we will obtain 6 different sub problems. We obtain the first sub problem changing the initial cost matrix and replacing the element c<sub>16</sub> with 10000 and the second sub problem changing the elements c<sub>12</sub>, c<sub>13</sub>, c<sub>14</sub>, c<sub>15</sub> with 10000 and unchanged the  c<sub>16</sub> element. Resolving all sub problems, we can obtain more solution without sub tour. The optimal solution is the solution with the minimum value of function.

**NB:** if you obtain a sub tour similar to this: (3-6-4-3), you have to formulate 3 sub problems changing the cost matrix as follows:  
  1. c<sub>36</sub> equal to 1000
  2. c<sub>36</sub> unchanged and c<sub>64</sub> c<sub>31</sub> c<sub>32</sub> c<sub>33</sub> c<sub>34</sub> c<sub>35</sub> equal to 1000
  3. c<sub>36</sub> unchanged and c<sub>43</sub> c<sub>31</sub> c<sub>32</sub> c<sub>33</sub> c<sub>34</sub> c<sub>35</sub> equal to 1000.

At the end, we you have to resolve all sub problems, the optimal solution is the slution with the minimum value of objective function.
The Branch and Bound program is not automatic and each time you have to change the cost matrix until you don't accomplish the final goal.

## <a name="Java anf the heuristic techniques"></a> Java and the heuristic techniques
Inside the [Java folder](./Java) there are two files [Main.java](./Java/Main.java) and [TSP.java](./Java/TSP.java) where ther is the code that implemented two heuristic techniques for TSP:
* Nearest Neighbor
* Repetetive Nearest Neighbor.
These two methods are very similiar but in the first case you have to choose the initial node where you want start the algorithm. After your choice, then the program gives the solution. The operation of Repetetive Nearest Neighbor algorithm is the same of Nearest Neighbor, but instead to give back one solution (with the specific inital node), this technique returns as many solutions as many nodes. The method considers for each execution a different node as starting node. In according to the starting node, the program gives a different solution.
To run the program you have to use a command line interface (Terminal for Mac OSX or cmd for Windows). After that you opened the console application, you must follow the steps:
1. **browes the folder** and go in the **[Java folder](./Java)**
2. digit **javac Main.java**
3. digit **java Main**.
At this point the program will be in execution and you can follow the istructions for resolving a TSP with one of heuristic techniques.

