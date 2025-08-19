#!/bin/python3

import os
import sys

def removeDuplicates(arrParam):
    if not arrParam:
        return arrParam
    i = 0
    for j in range(1, len(arrParam)):
        if arrParam[j] != arrParam[i]:
            i += 1
            arrParam[i] = arrParam[j]
    return arrParam[:i + 1]

def search(arrParam, targetParam):
    low, high = 0, len(arrParam) - 1
    while low <= high:
        mid = low + (high - low) // 2
        if arrParam[mid] == targetParam:
            return True
        elif arrParam[mid] < targetParam:
            low = mid + 1
        else:
            high = mid - 1
    return False

if _name_ == '_main_':
    fptr = open(os.environ['OUTPUT_PATH'], 'w')

    n = int(input().strip())
    arr = list(map(int, input().rstrip().split()))
    target = int(input().strip())

    dedupArr = removeDuplicates(arr)
    fptr.write(' '.join(map(str, dedupArr)))
    fptr.write('\n')

    exists = search(dedupArr, target)
    fptr.write(str(int(exists)) + '\n')

    fptr.close()