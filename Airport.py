"""
******************************
CS 1026 - Assignment 4 – Air Travel
Code by: Ethan Gregory
Student ID: egregor4
File created: November 29, 2024
******************************
This file defines the Airport class used in Assign4.py. It defines its initialization, equality check,
String output, and getters & setters.
"""


class Airport:

    def __init__(self, code, city, country):  # Init. Code
        self._code = code
        self._city = city  # Assign and Init. Vars
        self._country = country

    def __str__(self):  # String form
        return "{} ({}, {})".format(self._code, self._city, self._country)

    def __eq__(self, other):  # Define equal compare
        if isinstance(other, Airport):  # Check if it's an Airport
            if other.get_code() == self._code:  # If so, compare their code
                return True
            else:  # If codes do not align, return False.
                return False
        else:  # If not an Airport, return false
            return False

    def get_code(self):  # Getters
        return self._code

    def get_city(self):
        return self._city

    def get_country(self):
        return self._country

    def set_city(self, city):  # Setters
        self._city = city

    def set_country(self, country):
        self._country = country
