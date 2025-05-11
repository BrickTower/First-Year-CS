"""
******************************
CS 1026 - Assignment 4 – Air Travel
Code by: Ethan Gregory
Student ID: egregor4
File created: November 29, 2024
******************************
This file Is the main part of the Assignment. It defines all the necessary functions for the program, by loading the data into
a List and Dictionary for Airports & Flights, respectively. It can also pull Airports by code, show all flights per city or country,
find flights between two locations (with support for one layover). It finally can pull the shortest flight from an airport, and
find a return flight.
"""

# You must create the Flight.py and Airport.py files.
from Flight import *
from Airport import *

# Define your collections for all_airports and all_flights here.
all_airports = []
all_flights = {}  # Initialize it as a dictionary


def load_data(airport_file, flight_file):
    try:  # Open Airport File
        airport_data = open(airport_file)

        line = airport_data.readline()
        while line != "":
            line_data = line.split("-")

            for i in range(0, len(line_data)):
                line_data[i] = line_data[i].strip()  # Remove whitespace

            all_airports.append(Airport(line_data[0], line_data[2], line_data[1]))
            line = airport_data.readline()

        airport_data.close()  # Close Airport File
        flight_data = open(flight_file)  # Open Flight File

        line = flight_data.readline()
        while line != "": # Same for flight
            line_data = line.split("-") # Split by -

            for i in range(0, len(line_data)): # Strip data
                line_data[i] = line_data[i].strip()

            # Prep Flight Number
            flight_no = str(line_data[0] + "-" + line_data[1])

            # Add to Dictionary
            origin_air = get_airport_by_code(line_data[2])
            dest_air = get_airport_by_code(line_data[3])
            flight_obj = Flight(flight_no, origin_air, dest_air, line_data[4])

            if line_data[2] not in all_flights: # Add Flight List to Dictionary if it doesn't exist, append to it if not.
                all_flights[line_data[2]] = [flight_obj]
            else:
                all_flights[line_data[2]].append(flight_obj)

            line = flight_data.readline() # Next line

        flight_data.close() # Close Flight File
        return True # Return True if fully successful
    except IOError:
        print("Could not open files!")
    except Exception as exception:
        print("Error: " + str(exception))
        return False


def get_airport_by_code(code):
    for airport in all_airports: # Loop through airports list
        if airport.get_code() == code: # If match, return
            return airport

    # If Airport not found, raise error
    raise ValueError("No airport with the given code: {}".format(code))


def find_all_city_flights(city):
    city_list = []

    for flight in all_flights:
        for info in all_flights[flight]:
            dest_city = info.get_destination().get_city().lower()  # Get city text by calling the Airport, and then its city name.
            origin_city = info.get_origin().get_city().lower()

            if origin_city == city.lower() or dest_city == city.lower():
                city_list.append(info)

    return city_list


def find_all_country_flights(country):
    #Very similar code to find_all_city_flights, just adjusted for calling countries instead of cities
    country_list = []

    for flight in all_flights:
        for info in all_flights[flight]:
            dest_city = info.get_destination().get_country().lower()  # Get city text by calling the Airport, and then its city name.
            origin_city = info.get_origin().get_country().lower()

            if origin_city == country.lower() or dest_city == country.lower():
                country_list.append(info)

    return country_list


def find_flight_between(orig_airport, dest_airport):
    all_layovers = set()  # Initialize set as a local variable across function

    for flight in all_flights: # Loop through all flights and their airports
        for info in all_flights[flight]:
            if info.get_origin() == orig_airport and info.get_destination() == dest_airport: # If direct, return flight
                origin_code = info.get_origin().get_code()
                dest_code = info.get_destination().get_code()
                return "Direct Flight: {} to {}".format(origin_code, dest_code)
            elif info.get_origin() == orig_airport and not info.get_destination() == dest_airport:
                # If not, find nearest airport
                current_airport = info.get_destination() # Get flight's destination
                if current_airport.get_code() in all_flights: # If possible flight exists in database, then
                    for hop in all_flights[current_airport.get_code()]: # Loop through their list
                        if hop.get_destination() == dest_airport and hop.get_origin() == info.get_destination():
                            all_layovers.add(current_airport.get_code()) # Add multi-hop to list if possible

    if len(all_layovers) <= 0: # If no layovers exist or direct is impossible, raise error
        raise ValueError(
            f"There are no direct or single-hop connecting flights from {orig_airport.get_code()} to {dest_airport.get_code()}")
    else:
        return all_layovers




def shortest_flight_from(orig_airport):
    first_flight = all_flights[orig_airport.get_code()][0] # Set initial flight object so that I can compare

    current_dur = first_flight.get_duration() # Init. Var
    shortest_flight = None

    for flight in all_flights[orig_airport.get_code()]: # Loop through all flights from orig_airport
        if flight.get_duration() <= current_dur: # If duration is lower than before,
            current_dur = flight.get_duration() # Set duration
            shortest_flight = flight # Set flight

    return shortest_flight # Return Flight


def find_return_flight(first_flight):
    return_dest = first_flight.get_origin() # Set origin and destination to their opposites for return flight
    return_origin = first_flight.get_destination()

    for flight in all_flights[return_origin.get_code()]: # Loop through flights at the return flight's origin point
        if flight.get_destination() == return_dest: # If match, return
            return flight

    # Raise error if no flights found
    raise ValueError(f"There is no flight from {return_origin.get_code()} to {return_dest.get_code()}")

if __name__ == "__main__":
    data = load_data("airports.txt", "flights.txt")
