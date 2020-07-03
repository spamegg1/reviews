# -*- coding: utf-8 -*-
"""
Created on Sun Jun  5 13:44:11 2016

@author: ericgrimson
"""

from mortgage import *
from mortgageVariants import *

def findMaxPoints(amt, years, fixedRate, ptsRate, minPts, maxPts):
    totMonths = years*12
    fixed1 = Fixed(amt, fixedRate, totMonths)
    for m in range(totMonths):
        fixed1.makePayment()
    baseAmount = fixed1.getTotalPaid()
    print('base amount', baseAmount)
    
    # want to find out how many points you can tolerate
    
    span = (maxPts - minPts)*10
    
    for incr in range(span):
        pts = minPts + incr*.1
        fixed2 = FixedWithPts(amt, ptsRate, totMonths, pts)
        for m in range(totMonths):
            fixed2.makePayment()
        ptsAmount = fixed2.getTotalPaid()
        if ptsAmount - baseAmount > 0:
            print('maximum points affordable', str(pts))
            return pts
            
def findMaxPointsBisection(amt, years, fixedRate, ptsRate, minPts, maxPts):
    totMonths = years*12
    fixed1 = Fixed(amt, fixedRate, totMonths)
    for m in range(totMonths):
        fixed1.makePayment()
    baseAmount = fixed1.getTotalPaid()
    print('base amount', baseAmount)
    
    # want to find out how many points you can tolerate
    
    eps = 1000
    
    high = maxPts
    low = minPts
    
    closeEnuf = False
    
    while not closeEnuf:
        pts = (high - low)/2
        print('current points', pts)
        input('next')
        fixed2 = FixedWithPts(amt, ptsRate, totMonths, pts)
        for m in range(totMonths):
            fixed2.makePayment()
        ptsAmount = fixed2.getTotalPaid()
        print('pts amount', ptsAmount) 
        if abs(ptsAmount - baseAmount) < eps:
            closeEnuf = True
        elif ptsAmount < baseAmount:
            high = pts
        else:
            low = pts
    
    print('maximum points affordable', str(pts))
    return pts

findMaxPoints(200000, 30, 0.07, 0.06875, 0, 10) 
findMaxPointsBisection(200000, 30, 0.07, 0.6875, 0, 10)       