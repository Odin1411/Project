# -*- coding: utf-8 -*-
"""
Created on Thu May 19 13:21:11 2022

@author: olivi
"""
import matplotlib.pyplot as plt

def readFile(f):
    x = []
    with open(f) as h:
        for line in h: 
            if int(line) < 70: #so the graph will be a bit clearer
                x.append(int(line))
            
    plt.hist(x)
    plt.show()   

#readFile("histogramtest.txt")      
            
            
            