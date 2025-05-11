"""
*******************************************
CS 1026 - Assignment 2 - Password Strength
Code by: Ethan Gregory
Student ID: egregor4
File created: 2024-10-17
*******************************************
This file defines the function process_password(). When called with a parameter, min_strength, it prompts the user for
a password. The user may either enter their own, or have the program generate the password for them. The program then
tests the strength of the password, using the password_strength() function defined in password.py. The program will loop
until the password provided matches or exceeds the min_strength parameter.
"""

# Import my custom libraries
from password import *
from generate import *

def process_password(min_strength):
    pwd_strength = 0 # Create variable that tracks the strength of the user's password

    while pwd_strength < min_strength: # While the user's password is less strong than min_strength, run the program
        user_pwd = input("Type in a new password or type X for a randomly generated password: ")  # Get user input

        if user_pwd.lower() == "x":  # If user enters any form of 'X', generate a pwd with a length of 15
            user_pwd = generate_password(15)
            print("Your password: {}".format(user_pwd)) # Custom response for generated password
        else: # If not, have the user's password shown specifically as user's input
            print("You entered: {}".format(user_pwd))  # Show the password



        pwd_strength = password_strength(user_pwd) # Update the pwd_strength variable with the proper response.

        # Show this to the user
        print("Your password has a strength of {}".format(pwd_strength))

        # Output message depending on if the password is stronger than min_strength
        if pwd_strength >= min_strength: # If pwd_strength is larger, then end the program with success
            print("Your password is strong enough! Thank you.")
        else: # If not, let user know, and let the program loop
            print("Your password is not strong enough. Please create a new password that is stronger.\n")