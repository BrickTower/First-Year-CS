"""
******************************
CS 1026A - Assignment 1 - Shapes and Sizes
Created by: Ethan Gregory
Student ID: 251433485
File Created: 2024-09-19
******************************
This program prompts the user for a 2D shape. If the shape is valid,
(either circle, rectangle, triangle, hexagon, or trapezoid) then
prompt for the corresponding sides of the shape, or radius for the circle.
The program then outputs the shape's perimeter and area.
"""
import math
from math import * # Import Math Libraries

PI = pi # Constant for Pi

# Get User Input
valid_shape = input("Enter a 2D shape: ")
user_input = valid_shape.lower() # Make lowercase

# Check if Shape is valid
if user_input == "rectangle" or user_input == "circle" or user_input == "triangle" or user_input == "trapezoid" or user_input == "hexagon":
    if user_input == "rectangle":  # Rectangle Code
        length = float(input("\nEnter the length of the rectangle: "))  # Get Width & Height
        width = float(input("\nEnter the width of the rectangle: "))
        if width <= 0 or length <= 0:  # Guard against 0 or negative numbers
            print("Invalid value entered") # If error, print error & quit
            exit(0)
        rect_peri = (2 * width) + (2 * length)
        rect_area = width * length

        print("Shape: {}\nPerimeter: {:.2f}\nArea: {:.2f}".format(valid_shape, rect_peri, rect_area))
    if user_input == "circle":  # Code for Circle
        rad = float(input("\nEnter the radius of the circle: "))  # Get Radius
        if rad <= 0:  # Guard
            print("Invalid value entered") # Let user know, then close program
            exit(0)
        circ_peri = 2 * PI * rad
        circ_area = pi * (rad ** 2)  # Calc. results

        # Output
        print("Shape: {}\nPerimeter: {:.2f}\nArea: {:.2f}".format(valid_shape, circ_peri, circ_area))
    if user_input == "triangle":  # Code for Triangle
        base = float(input("\nEnter the base length of the triangle: "))
        side_2 = float(input("\nEnter the 2nd side length of the triangle: ")) # Get each side
        side_3 = float(input("\nEnter the 3rd side length of the triangle: "))
        height = float(input("\nEnter height of the triangle: "))

        if base <= 0 or height <= 0 or side_2 <= 0 or side_3 <= 0: # Guard against invalid numbers
            print("Invalid value entered")
            exit(0) # Quit if true

        tri_peri = base + side_2 + side_3 # Calc.
        tri_area = (base * height) / 2

        # Output
        print("Shape: {}\nPerimeter: {:.2f}\nArea: {:.2f}".format(valid_shape, tri_peri, tri_area))
    if user_input == "hexagon": # Code for hexagon
        hexagon_side = float(input("\nEnter the side length of the hexagon: ")) #Input

        if hexagon_side <= 0: # Guard
            print("Invalid value entered")
            exit(0) # Print error & quit.

        hexa_peri = hexagon_side * 6 # Calculations
        hexa_area = ((3 * sqrt(3) / 2) * (hexagon_side ** 2))

        # Output
        print("Shape: {}\nPerimeter: {:.2f}\nArea: {:.2f}".format(valid_shape, hexa_peri, hexa_area))
    if user_input == "trapezoid":
        trap_top = float(input("\nEnter the top side length of the trapezoid: "))
        trap_bottom = float(input("\nEnter the bottom side length of the trapezoid: "))
        trap_left = float(input("\nEnter the left side length of the trapezoid: "))
        trap_right = float(input("\nEnter the right side length of the trapezoid: "))
        trap_height = float(input("\nEnter the height of the trapezoid: "))

        if trap_top <= 0 or trap_bottom <= 0 or trap_left <= 0 or trap_height <= 0 or trap_right <= 0:
            print("Invalid value entered")
            exit(0) # Guard

        trap_peri = trap_top + trap_bottom + trap_left + trap_right
        trap_area = ((trap_top + trap_bottom) / 2) * trap_height # Calc.

        print("Shape: {}\nPerimeter: {:.2f}\nArea: {:.2f}".format(valid_shape, trap_peri, trap_area)) # Output result
else:
    print("Invalid shape entered")
    exit(0) # If shape not valid, quit