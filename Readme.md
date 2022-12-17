# Seating Arrangement

## Airplane Seating Algorithm
    Write a program that helps seat audiences in a flight based on the following input and rules.

## Rules for seating
1. Always seat passengers starting from the front row to back, starting from the left to the right
2. Fill aisle seats first followed by window seats followed by center seats (any order in center seats)

## Input to the program will be
1. A 2D array that represents the columns and rows - Ex. [[3,4], [4,5], [2,3], [3,4]]
2. Number of passengers waiting in the queue.

## Assumptions:
    1. Single column are aisle seats.

## Test Cases
1.  Correct Case
    ```
    [[1,1]]]
    2
    ```
2.  Correct Case
    ```
    [[5,6]]
    4
    ```
3.  Invalid Array
    ```
    [[x2,54]]
    23
    ```
4.  Invalid Array
    ```
    [[1,2,3],]
    23
    ```
5.  Invalid passengers
    ```
    [[1,4],[3,4],[5,3]]
    3d4
    ```
6. More than seating capacity
    ```
    [[4,5],[23,3],[2,4],[12,4]]
    34980
    ```
7. Single Row
    ```
    [[1,54]]
    54
    ```
8. Single Col
    ```
    [[54,1]]
    54
    ```
9. Negative Size
    ```
    [[-12,9],[5,-7]]    
    45
    ```
10. Valid
    ```
    [[1,2],[3,4], 
    23
    ```
## Environment Used
    1. OS: Manjaro Linux 
    2. IDE: Intellij Idea CE 2022.2.4 
    3. SDK: openjdk 18

## Setup
    1. Open folder in your favourite IDE or editor
    2. Make you environment variables are setup.
    3. Input is read from input.txt file and written to output.txt
    3. Run commands:
        1. javac SeatingArrangement.java
        2. java helper
