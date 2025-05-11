"""
Ethan Gregory
egregor4@uwo.ca
251433485
Creation date: 11-08-2024
Main code of the Assignment. Defines ask_user_for_input, and returns the most common emotion in a dataset,
along with writing a file that counts every instance of every emotion. This dataset can be filtered.
Exceptions are checked and handled within main code.
"""

import os.path
from emotions import *

VALID_COUNTRIES = ['bangladesh', 'brazil', 'canada', 'china', 'egypt',
                   'france', 'germany', 'india', 'iran', 'japan', 'mexico',
                   'nigeria', 'pakistan', 'russia', 'south korea', 'turkey',
                   'united kingdom', 'united states']


def ask_user_for_input():
    tuple_data = ()  # Init. Tuple

    keyword_file = input("Input keyword file (ending in .tsv): ")  # Get input
    if not keyword_file.endswith(".tsv"):  # Check for exceptions
        raise ValueError("Keyword file does not end in .tsv!")  # Custom error
    if not os.path.exists(keyword_file):
        raise IOError("{} does not exist!".format(keyword_file.lower()))
    tuple_data += (keyword_file.lower(),)  # Add to tuple

    comment_file = input("Input comment file (ending in .csv): ")
    if not comment_file.endswith(".csv"):
        raise ValueError("Comments file does not end in .csv!")
    if not os.path.exists(comment_file):
        raise IOError("{} does not exist!".format(comment_file.lower()))
    tuple_data += (comment_file.lower(),)

    country = input("Input a country to analyze (or \"all\" for all countries): ")
    if VALID_COUNTRIES.count(country.lower()) == 0 and country.lower() != "all":
        raise ValueError("{} is not a valid country to filter by!".format(country.lower()))
    tuple_data += (country.lower(),)

    report_file = input("Input the name of the report file (ending in .txt): ")
    if not report_file.endswith(".txt"):
        raise ValueError("Report file does not end in .txt!")
    if os.path.exists(report_file):
        raise IOError("{} exists, the report file can not already exist!".format(report_file.lower()))
    tuple_data += (report_file.lower(),)

    return tuple_data  # Return tuple


def main():
    user_data = ()  # Init. an empty tuple

    while len(user_data) <= 0:  # Loop until code is successful
        try:
            user_data = ask_user_for_input()
        except Exception as error:  # Output error if necessary
            print("Error: {}\n".format(str(error)))

    # Get a list of all filtered comments
    try:
        comments_list = make_comments_list(user_data[2], user_data[1])  # Make comment list via tuple
        print("Most common emotion is: {}".format( # Return the most common emotion
            make_report(comments_list, make_keyword_dict(user_data[0]), user_data[3])))  # Output most common emotion
    except Exception as error:  # Throw exception if failed
        print("Error: {}\n".format(str(error)))


if __name__ == "__main__":
    main()
