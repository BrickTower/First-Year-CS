"""
*******************************************
CS 1026 - Assignment 2 - Password Strength
Code by: Ethan Gregory
Student ID: egregor4
File created: 2024-10-09
*******************************************
This file defines the functions count_groups() and password_strength().
count_groups(pwd) takes a String and analyzes how many attributes (lowercase/uppercase
letters, numbers, symbols) are present, counting them. password_strength(pwd) uses this
function, alongside the length of the string itself, to calculate and return a total strength from 0 to 5.
"""

def count_groups(pwd): # Define Count Groups
    # Create variables for groups
    upper_present = False
    lower_present = False
    number_present = False
    symbol_present = False

    count = 0
    # Create constants for groups
    SYMBOLS = "!@#$%^&*/?-+=,.|~" # Does not have a corresponding indicator

    for letter in pwd: # Loop through String
        if letter.islower(): # If lower, count it once.
            lower_present = True
        elif letter.isupper():
            upper_present = True
        elif letter.isnumeric():
            number_present = True
        else:
            for symbol in SYMBOLS:
                if letter == symbol and symbol_present == False:
                    symbol_present = True

    # Add groups together
    if upper_present: # If condition is true, then:
        count += 1 # Increase count by 1
    if lower_present:
        count += 1
    if number_present:
        count += 1
    if symbol_present:
        count += 1

    return count # Return counted value

def password_strength(pwd):
    # Define variables & constants
    PWD_LENGTH = len(pwd)

    length_score = 0
    group_score = 0
    total_score = 0

    # First, calculate score from length. We also do this to check if invalid characters are used.
    for letter in pwd:
        # Immediately return a score of 0 if whitespace is present
        if letter == " " or letter == "\t" or letter == "\n":
            return total_score  # Immediately end the function (It is already at 0)

    group_count = count_groups(pwd) # Call our function above to get the total amount of groups

    # Otherwise, calculate length score
    if PWD_LENGTH > 12:
        length_score = 3
    elif 9 <= PWD_LENGTH <= 12:
        length_score = 2
    elif 5 <= PWD_LENGTH <= 8:
        length_score = 1
    elif PWD_LENGTH < 5:
        length_score = 0
    # Keep length_score at 0 if it's less than 5.

    # Calculate group score
    # We'll keep group_score at 0 if it's only got 0 to 1 groups, or if the password is less than 4 characters

    if 2 <= group_count <= 3 and PWD_LENGTH > 5:
        group_score = 1
    elif group_count > 3 and PWD_LENGTH > 5: # If 4, then give max score
        group_score = 2

    # Calculate total_score
    total_score = group_score + length_score

    # Return result
    return total_score