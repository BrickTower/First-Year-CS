"""
******************************
CS 1026 - Assignment 4 – Air Travel
Code by: Ethan Gregory
Student ID: egregor4
File created: November 29, 2024
******************************
This file defines the Flight class used in Assign4.py. It defines its initialization, equality check, addition
String output, and getters & setters.
"""

from Airport import *  # Import Airport Class


class Flight:

    def __init__(self, flight_no, origin, dest, dur):  # Initialize a Flight
        if not isinstance(origin, Airport) or not isinstance(dest, Airport):  # Guard for Origin and Dest to be Airports
            raise TypeError("The origin and destination must be Airport objects")  # Raise error if not
        else:
            self._flight_no = flight_no
            self._origin = origin
            self._dest = dest  # Assign Var.
            self._dur = float(dur)  # Convert dur var into a float

    def __eq__(self, other):  # Equal
        if isinstance(other, Flight):  # Check if other is a flight
            if other._dest == self._dest and other.get_origin() == self._origin:  # Make sure dest and origin are same
                return True
            else:  # If not, false
                return False
        else:  # If not Flight, false
            return False

    def is_domestic(self):  # Check if flight is domestic
        if self._origin.get_country() == self._dest.get_country():
            return True
        else:
            return False

    def __str__(self):
        domestic = ""

        if self.is_domestic():  # Check if flight is domestic or international, update string to match
            domestic = "domestic"
        else:
            domestic = "international"

        # Return string
        return f"{self._origin.get_city()} to {self._dest.get_city()} ({round(self._dur)}h) [{domestic}]"

    def __add__(self, conn_flight):  # Add a connecting flight to the existing one
        if not isinstance(conn_flight, Flight):  # Guards
            raise TypeError("The connecting_flight must be a Flight object")

        if conn_flight._origin != self._dest:
            raise ValueError("These flights cannot be combined")

        # Return new Flight if they can connect

        return Flight(self.get_flight_no(), self.get_origin(), conn_flight.get_destination(),
                      (self.get_duration() + conn_flight.get_duration()))

    def get_duration(self):  # Getters
        return self._dur

    def get_flight_no(self):
        return self._flight_no

    def get_origin(self):
        return self._origin

    def get_destination(self):
        return self._dest

    def set_origin(self, origin):  # Setters
        self._origin = origin

    def set_destination(self, dest):
        self._dest = dest
