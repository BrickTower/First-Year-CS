"""
*******************************************
CS 1026 - Assignment 2 - Password Strength
Code by: Ethan Gregory
Student ID: egregor4
File created: 2024-10-16
*******************************************
This file defines the function generate_password(). When generate_password() is called, it takes a parameter from it
called length, and randomly generates a string of valid characters that would create a valid password.
"""

import random # Pull in the built-in random Python library

def generate_password(length):
    ALL_CHARS = "" # To make the program more clear, I'll concat the ALL_CHARS var to add each of the necessary numbers

    ALL_CHARS += "abcdefghijklmnopqrstuvwxyz" # Lowercase
    ALL_CHARS += "abcdefghijklmnopqrstuvwxyz".upper() # Uppercase
    ALL_CHARS += "0123456789" # Numbers
    ALL_CHARS += "!@#$%^&*/?-+=,.|~" # Symbols

    random_pwd = "" # Create variable to return the password

    # Create a for loop to add a random character to the string
    for i in range(0, int(length)): # Loop through the desired inputs
        random_pwd += random.choice(ALL_CHARS) # Add to random_pwd

    return random_pwd # Return random_pwd


