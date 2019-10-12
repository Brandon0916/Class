 # seng 474 a1 question4
 # Ronald Liu	V00838627 
 # Zimeng Ming V00844078

import pandas as pd
import numpy
import math


def main():

    #dataset = pd.read_csv('traindata.txt', header = None, sep='\t' )
    #dataset.head()
    
    f1 = open("traindata.txt", "r").read()
    lines1 = f1.splitlines()
    #print(lines1)
    
    extract_V = []
    
    for line in lines1: 
        words = line.split()
        #print(words)

        for word in words:
            if word not in extract_V:
                extract_V.append(word)


    extract_V_count = len(extract_V)
    print(extract_V_count)


    f2 = open("trainlabels.txt", "r").read()
    lines2 = f2.splitlines()
    #print(lines2)
    
    N_all = len(lines2)
    N_c0 = 0
    N_c1 = 0
    
    for line2 in lines2: 
        if line2 == "1":
            N_c1 = N_c1 + 1
        if line2 == "0":
            N_c0 = N_c0 + 1    

    #print(N_all)
    #print(N_c0)
    #print(N_c1)


    
    prior = {}
    prior["0"] = (N_c0 / N_all)
    prior["1"] = (N_c1 / N_all)

    print(prior)








    text1 = ""
    text0 = ""
    for row in range(N_all):
        if lines2[row] == "1\n" or lines2[row] == "1":
            line1 = lines1[row]
            #line = line1[:-1]
            text1 = text1 + line1 + " "
        if lines2[row] == "0\n" or lines2[row] == "0":
            line1 = lines1[row]
            #line = line1[:-1]
            text0 = text0 + line1 + " "           
    
    #print(text1)


    t1_words = text1.split()
    t0_words = text0.split()
    
    T_c1t = {}
    T_c0t = {}
    T_c1_sum = len(t1_words)
    T_c0_sum = len(t0_words)
    
    condprob = {}
    
    
    testing_sum1 = 0
    testing_sum0 = 0 
    
    for t in extract_V:
        T_c1t[t] = t1_words.count(t)
        T_c0t[t] = t0_words.count(t)
    
    #print(T_c1t)
    #print(T_c0t)
    print(T_c1_sum)
    print(T_c0_sum)

    for t in extract_V: 
        condprob[(t,"1")] = ( T_c1t[t] + 1 ) / ( T_c1_sum + extract_V_count )
        condprob[(t,"0")] = ( T_c0t[t] + 1 ) / ( T_c0_sum + extract_V_count )
    
    #print(condprob)
    
    
    
    
    # predicting the label and count accuracy
    
    f3 = open("traindata.txt", "r").read()
    lines3 = f3.splitlines()
    
    output_labels = []
    
    for line3 in lines3:
            
        words = line3.split()
        
        filter_words = []
        
        for word in words:
            if word in extract_V:
                filter_words.append(word)

        score = {}
            
        for c in ["0", "1"]:
        
            score[c] = math.log2(prior[c])
                
            for t in filter_words:

                score[c] = score[c] + math.log2( condprob[(t,c)] )

        output = numpy.argmax( [score["0"], score["1"]] )
        output_labels.append(output)
    
    #print(output_labels)
    
    
    
    
    f4 = open("trainlabels.txt", "r").read()
    lines4 = f4.splitlines()
    
    count = 0
    
    for row in range(len(lines4)):
    
        if ( int(lines4[row]) == output_labels[row] ):
            count = count + 1
    
    print ( "Accuracy: ", ( count/len(lines4) ) )
    

    with open("results.txt", "w+") as output_file:
        output_file.write( "Accuracy of traindata:  " + str( count/len(lines4) ) + "\n" )
        
        
        
        
        
    
    #print( "testing: ***********", extract_V.count("nurturing") )
    
    f5 = open("testdata.txt", "r").read()
    lines5 = f5.splitlines()
    
    output_labels = []
    
    for line5 in lines5:
            
        words = line5.split()
        
        filter_words = []
        
        for word in words:
            if word in extract_V:
                filter_words.append(word)

        score = {}
            
        for c in ["0", "1"]:
        
            score[c] = math.log2(prior[c])
                
            for t in filter_words:

                score[c] = score[c] + math.log2( condprob[(t,c)] )

        output = numpy.argmax( [score["0"], score["1"]] )
        output_labels.append(output)
    
    #print(output_labels)
    
    
    
    
    f6 = open("testlabels.txt", "r").read()
    lines6 = f6.splitlines()
    
    count = 0
    
    for row in range(len(lines6)):
    
        if ( int(lines6[row]) == output_labels[row] ):
            count = count + 1
    
    print ( "Accuracy: ", ( count/len(lines6) ) )
    

    with open("results.txt", "a") as output_file:
        output_file.write( "Accuracy of testdata:  "+ str( count/len(lines6) ) + "\n" )

if __name__ == "__main__":
	main()

	