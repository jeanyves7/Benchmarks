from time import perf_counter
import time
from itertools import groupby
from heapq import *
import random
import os
import subprocess
import platform


def letSleep(n):
    print("sleeping for: " + str(n)+" seconds")
    time.sleep(n)

#Method that empties Benchmark File
def resetFile(name):
    with open(name,"w"):
        pass

def WriteFile(name,liste):
    with open(name, "a") as TextFile:
        for r in liste:
            TextFile.write(r[0] + " ")
            TextFile.write(r[1] + " ")
            TextFile.write(r[2])
            TextFile.write(" \n")

#Bubble Sort benchmark

def bubbleSortBenchmark():
    print("starting bubble Sort")
    benchmarkList = []
    x = 9999
    while (x >= 0):
        benchmarkList.append(x)
        x = x - 1
    timer_start=perf_counter()
    i=0
    while (i<len(benchmarkList)-1):
        j=0
        while (j<len(benchmarkList)-1-i):
            if(benchmarkList[j]>benchmarkList[j+1]):
                benchmarkList[j],benchmarkList[j+1]=benchmarkList[j+1],benchmarkList[j]
            j=j+1
        i=i+1
    timer_stop=perf_counter()
    print("done Bubble Sort")
    return ("BubbleSortList","bbls",str(round((timer_stop - timer_start),2)))

    #Stores Benchmark result in file




#List Appending Benchmark

def appendListBenchmark():
    print("starting AppendList")
    timer_start = perf_counter()
    alphabet = "abcdefghijklmnopqrstuvwxyz0123456789"
    i=0
    while(i<9999):
        liste = []
        liste2=[]
        mot=""
        for j in range(800):
            mot+=random.choice(alphabet)
        for j in mot:
            if j.isnumeric():
                j=int(j)
                if j%2==0:
                    liste.append(j)
                else:
                    liste2.append(j)
            else:
                liste2.append(j)
        liste3=liste2
        i+=1
    timer_stop = perf_counter()

    # Stores Benchmark result in file
    print("done Liste Appending")
    return ("ListAppending", "LsAp",str(round((timer_stop - timer_start),2)))





# Read/Write benchmark

def writeTest():
    print("Starting the Write Test")
    timer_start = perf_counter()
    i = 0
    while (i < 5):
        with open("WriteFile.txt", "wb") as wFile:
            wFile.write(os.urandom(1024 * 1024 * 1024))
        i = i + 1
    with open("WriteFile.txt","w") as Wfile:
        Wfile.close()
    os.remove("WriteFile.txt")
    timer_stop = perf_counter()
    print("done Write test")
    return ("WriteTest", "wTest", str(round((timer_stop - timer_start),2)))


#find prime benchmark
def findPrime():
    print("starting FindPrime")
    timer_start = perf_counter()
    i=0
    c=0
    while(i<9999):
        liste=[]
        num1=random.randint(0,99999)
        if(num1%2==0):
            j=0
            while(j<999):
                num2=random.randint(0,99999)
                if(num2%2==0):
                    liste.append(num2)
                j+=1
            c+=1
            liste3=liste  
        i+=1
    timer_stop = perf_counter()
    print("done prime")
    print("/////////////////")
    print("hold on we are halfway through")
    return ("FindPrime", "Fpme",str(round((timer_stop - timer_start),2)))


#find voyelle benchmark 
def findVoyelle():
    print("starting findVoyelle")
    timer_start = perf_counter()
    voyelle="aeiouy"
    alphabet="abcdefghijklmnopqrstuvwxyz"
    i=0
    while(i<9999):
        liste = []
        mot=""
        for j in range(800):
            mot+=random.choice(alphabet)
        for j in voyelle:
            if j in mot:
                liste.append(mot)
        liste3=liste
        i+=1
    timer_stop = perf_counter()
    print("done find voyelle")
    return ("FindVoyelle", "Fvol",str(round((timer_stop - timer_start),2)))


#Ceaser cypher benchmark 
def caesarCypher():
    print("starting Caesar Cypher")
    timer_start = perf_counter()
    L2I = dict(zip("ABCDEFGHIJKLMNOPQRSTUVWXYZ", range(26)))
    I2L = dict(zip(range(26), "ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
    for counter in range(0,9999):
        key = random.randint(0,26)
        choices="ABCDEF GHIJKLM NOPQRSTUVWXYZ "
        plaintext=""
        for i in range (850):
            plaintext += random.choice(choices)

        # encipher
        ciphertext = ""
        for c in plaintext.upper():
            if c.isalpha():
                ciphertext += I2L[(L2I[c] + key) % 26]
            else:
                ciphertext += c

        # decipher
        plaintext2 = ""
        for c in ciphertext.upper():
            if c.isalpha():
                plaintext2 += I2L[(L2I[c] - key) % 26]
            else:
                plaintext2 += c
    timer_stop = perf_counter()
    print("Caesar Cypher done")
    return ("CaesarCypher", "CaCp", str(round((timer_stop - timer_start),2)))



#ICMP Request Benchmark

def pingICMP():
    print("starting ICMP Request")
    timer_start=perf_counter()
    try:
        output = subprocess.check_output(("ping google.com -n 50"), shell=True, universal_newlines=True)
    except:
        print("Could not test ping: No internet connection")
    timer_stop=perf_counter()
    print("ICMP Request Done")
    return ("ICMPrequest","icmp",str(round((timer_stop - timer_start),2)))



#WORK presented by ahmad el cheikh and jean yves Youssef

#Main method
if __name__=="__main__":

    Name = input("please enter your name: ")
    path = Name + ".txt"
    resetFile(Name)
    print("this will take some time " + Name + " but don't worry nothing will happen so let the process finish itself")
    print("//////////////////////////////")
    B = bubbleSortBenchmark()
    letSleep(10)
    A = appendListBenchmark()
    letSleep(10)
    W = writeTest()
    letSleep(10)
    FP = findPrime()
    letSleep(10)
    FV = findVoyelle()
    letSleep(10)
    C = caesarCypher()
    letSleep(10)
    P = pingICMP()
    results = [B, A, FP, FV, C, P]
    WriteFile(path,results)
    print("Kindly send us your file .txt")
