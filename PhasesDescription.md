# Electrical Circuit Builder

## What is my project about?

This application will allow the user to build their own electrical circuit. The two available circuits 
the user can choose from is a series or a parallel circuit. The user can also add in **resistors**, **lightbulbs** 
to make the circuit. The user can also add meters such as **voltmeters** and **ampmeters** to measure the voltage, 
current in different parts of the circuit. There will also be a *save* and *load* option to view past circuits, 
and an *edit* function to edit previous circuits.

## Who will use it? 
The people who will use this project will bne anyone with an interest to simulate series or parallel circuits 
with different tools such as metres and lightbulbs. 

## Why did I choose electrical circuits?
This project is of interest ot me because I am currently studying electrical engineering, 
and I wanted to make a project that combines my major with java object oriented programming.

## User Stories

- As a user, I want to be able to add any component (resistor, lightbulb, voltmeter, and ampmeter) to my circuit
- As a user, I want to be able to remove any component from my circuit
- As a user, I want to be able to view the current and voltage going through a resistor
- As a user, I want to be able to view the resistance and status of a resistor and a lightbulb
- As a user, I want to be able to change the resistance of a resistor or lightbulb in the circuit


- As a user I would like to save my circuit
- As a user I would like to load one of my past circuits
- As a user I would like to edit one of my past circuits

## Instructions for Grader

- You can add a resistor, lightbulb, ampmeter or voltmeter by clicking the add button
- You can change the value of resistance of a resistor or lightbulb by pressing the 
change button and typing in which resistance you would want to change it to.
- You can locate a visual component of the amount of circuit parts you have in view/
view chart
- You can save all circuits made by pressing the save button
- You can load in a past circuit by pressing load and typing the respective circuit you want to load in


## Phase 4: Task 2
For task two I decided to use a map interface. I used hashmap, which is located in my GUI class.
I used this so I can choose which panel on my GUI shows up after an action as been inputted.

## Phase 4: Task 3

One problem that I have identified is that my resistor and lightbulb class had very similar class. I refactored this by converting 
my ResistorComponent interface into an abstract class, this way I don't have to duplicate my code, and I improve my cohesion of both my
resistor and lightbulb class. A second problem that I identified was the poor cohesion in the FileReader class.
I noticed that the parseCircuit method in my file reader class was too long and had two methods in order to fully convert a string to a circuit. In order to improve on 
the cohesion of this I placed two methods of parseCircuit into its own class. That way the class that has to do with reading from 
a file doesn't have to deal with conversions from string to a circuit.